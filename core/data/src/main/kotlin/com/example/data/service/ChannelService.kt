package com.example.data.service

import android.util.Log
import com.example.data.repo.ChannelRepository
import com.example.model.ChannelData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChannelService(private val firestore: FirebaseFirestore): ChannelRepository {
    override suspend fun addChannel(serverId: String, channelData: ChannelData, currentUserId: String) {
        val serverService = ServerService(firestore)
        // val adminId = serverService.getAdminId(serverId)
        // Log.d("FIRESTORE", "Admin ID: $adminId")
        // if (adminId == currentUserId) {
        firestore.collection("servers").document(serverId)
            .collection("channels").document(channelData.id).set(channelData)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "Added channel successfully: ${channelData}")

            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding channels: $exception")
            }.await()
        addChannelIntoServerList(serverId, channelData.id)
        addMember(serverId, channelData.id ,channelData.adminId)
        return
        // }
        // Log.e("FIRESTORE ERROR", "Only admin can create new channel")
    }

    override suspend fun addChannelIntoServerList(serverId: String, channelId: String) {
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId).get()
                .addOnSuccessListener {documentSnapshot ->
                    val channels: MutableList<String> = (documentSnapshot.get("channels") as MutableList<String>)!!
                    channels.add(channelId)
                    firestore.collection("servers").document(serverId).update("channels", channels)
                    Log.d("FIRESTORE", "Added channel into server successfully: ${channels}")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error adding channels: $exception")
                }.await()
            return
        }
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
    }

    override suspend fun updateChannelData(serverId: String,
                                            channelId: String,
                                            channelData: ChannelData,
                                            currentUserId: String) {
        val channelService = ChannelService(firestore)
        val serverService = ServerService(firestore)
        val channel = channelService.getChannelById(serverId, channelId)
        val adminId = serverService.getAdminId(serverId)
        if (channel != null) {
            if (adminId == currentUserId) {
                firestore.collection("servers").document(serverId).
                collection("channels").document(channelId).set(channelData)
                    .addOnSuccessListener {
                        Log.d("FIRESTORE", "Updated server successfully: $channelData")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FIRESTORE ERROR", "Error updating server data to Firestore: $exception")
                    }.await()
                return
            }
            Log.e("FIRESTORE ERROR", "Only admin can update channel")
            return
        }
        Log.d("FIRESTORE ERROR", "Channel not found with ID: $channelId")
    }

    override suspend fun addMember(serverId: String, channelId: String, userId: String) {
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId)
                .collection("channels").document(channelId).get()
                .addOnSuccessListener {documentSnapshot ->
                    var members: MutableList<String> = documentSnapshot.get("members") as MutableList<String>
                    members.add(userId)
                    firestore.collection("servers").document(serverId)
                        .collection("channels").document(channelId).update("members", members)
                    Log.d("FIRESTORE", "Added user successfully: ${members}")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error adding user: $exception")
                }.await()
        }
    }

    override suspend fun deleteChannel(serverId: String, channelId: String, currentUserId: String) {
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        val adminId = serverService.getAdminId(serverId)
        if (server != null) {
            if (adminId == currentUserId) {
                firestore.collection("servers").document(serverId)
                    .collection("channels").document(channelId).delete()
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
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
    }

    override suspend fun getChannelById(serverId: String, channelId: String): ChannelData? {
        var channel: ChannelData? = null
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId)
                .collection("channels").document(channelId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val id: String = document.data?.get("id").toString()
                        val adminId: String = document.data?.get("adminId").toString()
                        val name: String = document.data?.get("name").toString()
                        val members: MutableList<String> = document.data?.get("members") as MutableList<String>? ?: mutableListOf()
                        val messages: MutableList<String> = document.data?.get("messages") as MutableList<String>? ?: mutableListOf()
                        Log.d("FIRESTORE", "Get channel with ID: $serverId successfully")
                        channel = ChannelData(name, adminId, serverId, members, messages, id = id)
                        Log.d("FIRESTORE", "Channel: $server")
                    } else {
                        Log.d("FIRESTORE ERROR", "Server not found with ID: $serverId")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error getting server: ", exception)
                }.await()
            return channel
        }
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
        return null
    }
}