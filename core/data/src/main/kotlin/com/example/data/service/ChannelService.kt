package com.example.data.service

import android.util.Log
import com.example.data.repo.ChannelRepository
import com.example.model.ChannelData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChannelService(private val firestore: FirebaseFirestore): ChannelRepository {
    override suspend fun addChannel(channelData: ChannelData, currentUserId: String) {
        firestore.collection("channels").document(channelData.id).set(channelData)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "Added channel successfully: ${channelData}")

            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding channels: $exception")
            }.await()
        addChannelIntoServerList(channelData.serverId, channelData.id)
        addMember(channelData.id ,channelData.adminId)
        return
    }

    override suspend fun addDirectMessage(channelData: ChannelData, currentUserId: String, targetUserId: String) {
        firestore.collection("channels").document(channelData.id).set(channelData)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "Added direct message successfully: ${channelData}")
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding direct message: $exception")
            }.await()
        return
    }

    override suspend fun getDirectMessageId(currentUserId: String, targetUserId: String): String {
        try {
            val channels = firestore.collection("channels")
                .whereArrayContains("members", currentUserId)
                .get().await().documents

            val channel = channels.firstOrNull {
                (it.get("members") as? List<String>)?.contains(targetUserId) == true &&
                        (it.get("serverId") as? String).isNullOrEmpty()
            }

            if (channel != null) {
                Log.d("FIRESTORE", "Get direct message ID successfully: ${channel.id}")
                return channel.id
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE ERROR", "Error getting direct message ID: ", e)
        }
        return ""
    }

    override suspend fun addChannelIntoServerList(serverId: String, channelId: String) {
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener {documentSnapshot ->
                val channels = documentSnapshot.get("channels") as? MutableList<String> ?: mutableListOf()
                if (!channels.contains(channelId)) channels.add(channelId)
                firestore.collection("servers").document(serverId).update("channels", channels)
                Log.d("FIRESTORE", "Added channel into server successfully: ${channels}")
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding channels: $exception")
            }.await()
        return
    }

    override suspend fun updateChannelData(channelId: String, channelData: ChannelData, currentUserId: String) {
        val channelService = ChannelService(firestore)
        val channel = channelService.getChannelById(channelId)
        val adminId = channel?.adminId
        if (channel != null) {
            if (adminId == currentUserId) {
                firestore.collection("channels").document(channelId).set(channelData)
                    .addOnSuccessListener {
                        Log.d("FIRESTORE", "Updated server successfully: $channelData")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FIRESTORE ERROR", "Error updating channel data to Firestore: $exception")
                    }.await()
                return
            }
            Log.e("FIRESTORE ERROR", "Only admin can update channel")
            return
        }
    }

    override suspend fun addMember(channelId: String, userId: String) {
        firestore.collection("channels").document(channelId).get()
            .addOnSuccessListener {documentSnapshot ->
                var members: MutableList<String> = documentSnapshot.get("members") as MutableList<String>
                if (!members.contains(userId)) members.add(userId)
                firestore.collection("channels").document(channelId).update("members", members)
                Log.d("FIRESTORE", "Added user successfully: ${members}")
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding user: $exception")
            }.await()
    }

    override suspend fun deleteChannel(channelId: String, currentUserId: String) {
        val channelService = ChannelService(firestore)
        val channel = channelService.getChannelById(channelId)
        val adminId = channel?.adminId
        if (adminId != null) {
            if (adminId == currentUserId) {
                firestore.collection("channels").document(channelId).delete()
                    .addOnSuccessListener {
                        Log.d("FIRESTORE", "Delete channel successfully")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FIRESTORE ERROR", "Error deleting channel: ", exception)
                    }.await()
                return
            }
            Log.e("FIRESTORE ERROR", "Only admin can delete channel")
            return
        }
    }

    override suspend fun getChannelById(channelId: String): ChannelData? {
        try {
            val document = firestore.collection("channels").document(channelId).get().await()
            if (document != null) {
                val id: String = document.data?.get("id").toString()
                val adminId: String = document.data?.get("adminId").toString()
                val serverId: String = document.data?.get("serverId").toString()
                val name: String = document.data?.get("name").toString()
                val members: MutableList<String> = document.data?.get("members") as MutableList<String>? ?: mutableListOf()
                val messages: MutableList<String> = document.data?.get("messages") as MutableList<String>? ?: mutableListOf()
                Log.d("FIRESTORE", "Get channel with ID: $serverId successfully")
                return ChannelData(name, adminId, serverId, members, messages, id = id)
            } else {
                Log.d("FIRESTORE ERROR", "Channel not found with ID: $channelId")
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE ERROR", "Error getting channel: ", e)
        }
        return null
    }

    override suspend fun getLastMessage(channelId: String): String {
        try {
            val messages = firestore.collection("channels").document(channelId).get().await().data?.get("messages") as? MutableList<String>
            if (!messages.isNullOrEmpty()) {
                Log.d("FIRESTORE", "Get last message successfully: ${messages.last()}")
                return messages.last()
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE ERROR", "Error getting last message: ", e)
        }
        return ""
    }

    override fun getChannelDocument(channelId: String): DocumentReference {
        return firestore.collection("channels").document(channelId)
    }
}