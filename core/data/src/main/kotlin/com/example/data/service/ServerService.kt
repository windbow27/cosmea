package com.example.data.service

import android.util.Log
import com.example.data.repo.ServerRepository
import com.example.model.CategoryData
import com.example.model.ServerData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ServerService(private val firestore: FirebaseFirestore): ServerRepository {
    override suspend fun addServerData(serverData: ServerData) {
        firestore.collection("servers").
        document(serverData.id.toString()).set(serverData)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "Created server successfully: $serverData")
            }
            .addOnFailureListener {exception ->
                Log.e("FIRESTORE ERROR", "Error adding server data to Firestore: $exception")
            }.await()
    }

    override suspend fun getServerDataById(serverId: String): String? {
        var server: String? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    server = document.data.toString()
                    Log.d("FIRESTORE", "Get server with ID: $serverId successfully")
                    Log.d("FIRESTORE", "Server: $server")
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

//    override suspend fun addCategory(serverId: String, categoryData: CategoryData) {
//        val server = getServerDataById(serverId)
//        if (server != null) {
//            firestore.collection("servers").document(serverId).get()
//                .addOnSuccessListener {document ->
//                    document.data?.put("categories", categoryData)
//                    Log.d("FIRESTORE", "Added category successfully: ${document.data.toString()}")
//                }
//                .addOnFailureListener { exception ->
//                    Log.e("FIRESTORE ERROR", "Error getting categories: $exception")
//                }.await()
//        }
//    }

    override suspend fun getAllCategories(serverId: String): String? {
        var categories: String? = null
        firestore.collection("servers").document(serverId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    categories = documentSnapshot.get("categories").toString()
                    Log.d("FIRESTORE", "Get all categories successfully: $categories")
                } else {
                    Log.e("FIRESTORE ERROR", "Not found server with ID: $serverId")
                }
            }
            .addOnFailureListener {exception ->
                Log.e("FIRESTORE ERROR", "Error getting categories: $exception")
            }.await()
        return null
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
}