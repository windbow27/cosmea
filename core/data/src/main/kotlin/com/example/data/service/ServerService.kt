package com.example.data.service

import android.util.Log
import com.example.data.repo.ServerRepository
import com.example.model.ChannelData
import com.example.model.ServerData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ServerService(private val firestore: FirebaseFirestore): ServerRepository {
    override suspend fun addServerData(serverData: ServerData) {
        firestore.collection("servers").
        document(serverData.id).set(serverData)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "Created server successfully: $serverData")
            }
            .addOnFailureListener {exception ->
                Log.e("FIRESTORE ERROR", "Error adding server data to Firestore: $exception")
            }.await()
        addMember(serverData.id, serverData.adminId)
    }

    override suspend fun getAdminId(serverId: String): String? {
        var adminId: String? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener {documentSnapshot ->
                adminId = documentSnapshot.data?.get("adminId").toString()
                Log.d("FIRESTORE", "Get admin ID successfully: $adminId")
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Failed to get admin ID")
            }.await()
        return adminId
    }

    override suspend fun getServerDataById(serverId: String): ServerData? {
        var server: ServerData? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val id: String = document.data?.get("id").toString()
                    val adminId: String = document.data?.get("adminId").toString()
                    val avatar: String = document.data?.get("avatar").toString()
                    val channels: MutableList<String> = (document.data?.get("channels") as MutableList<String>?)!!
                    val members: MutableList<String> = (document.data?.get("members") as MutableList<String>?)!!
                    val name: String = document.data?.get("name").toString()
                    Log.d("FIRESTORE", "Get server with ID: $serverId successfully")
                    server =  ServerData(adminId, name, avatar, members, channels, id = id)
                } else {
                    Log.d("FIRESTORE ERROR", "Server not found with ID: $serverId")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error getting server: ", exception)
            }.await()
        return server
    }

    override suspend fun updateServerData(serverId: String, serverData: ServerData) {
        val server = getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId).set(serverData)
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Updated server successfully: $serverData")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error updating server data to Firestore: $exception")
                }.await()
        }
        else {
            Log.d("FIRESTORE ERROR", "Server not found with ID: $serverId")
        }
    }

    override suspend fun deleteServerDataById(serverId: String) {
        val server = getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId).delete()
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Deleted server with ID: $serverId successfully")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error deleting server data: $exception")
                }.await()
        }
        else {
            Log.d("FIRESTORE ERROR", "Server not found with ID: $serverId")
        }
    }

    override suspend fun addMember(serverId: String, userId: String) {
        val server = getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId).get()
                .addOnSuccessListener {documentSnapshot ->
                    var users: MutableList<String> = documentSnapshot.get("members") as MutableList<String>
                    users.add(userId)
                    firestore.collection("servers").document(serverId).update("members", users)
                    Log.d("FIRESTORE", "Added user successfully: ${users}")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error adding user: $exception")
                }.await()
        }
    }

    override suspend fun getAllChannels(serverId: String): List<ChannelData>? {
        var channels: List<ChannelData>? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    channels = documentSnapshot.get("channels") as List<ChannelData>?
                    Log.d("FIRESTORE", "Get all channels successfully: $channels")
                } else {
                    Log.e("FIRESTORE ERROR", "Not found server with ID: $serverId")
                }
            }
            .addOnFailureListener {exception ->
                Log.e("FIRESTORE ERROR", "Error getting channels: $exception")
            }.await()
        return channels
    }

    override suspend fun getAllMembers(serverId: String): String? {
        var members: String? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    members = documentSnapshot.get("members").toString()
                    Log.d("FIRESTORE", "Get all members successfully: $members")
                } else {
                    Log.e("FIRESTORE ERROR", "Not found server with ID: $serverId")
                }
            }
            .addOnFailureListener {exception ->
                Log.e("FIRESTORE ERROR", "Error getting members: $exception")
            }.await()
        return null
    }

    override fun getAllServerData(): CollectionReference {
        return firestore.collection("servers")
    }
}