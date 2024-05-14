package com.example.data.service

import android.util.Log
import com.example.data.repo.CategoryRepository
import com.example.model.CategoryData
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryService(private val firestore: FirebaseFirestore): CategoryRepository {
    override suspend fun addCategory(serverId: String, categoryData: CategoryData, currentUserId: String) {
        val serverService = ServerService(firestore)
        val adminId = serverService.getAdminId(serverId)
        Log.d("FIRESTORE", "Admin ID: $adminId")
        if (adminId == currentUserId) {
            firestore.collection("servers").document(serverId)
                .collection("categories").document(categoryData.id).set(categoryData)
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Added category successfully: ${categoryData}")

                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error adding categories: $exception")
                }.await()
            addCategoryIntoServerList(serverId, categoryData.id)
            return
        }
        Log.e("FIRESTORE ERROR", "Only admin can create new category")
    }

    override suspend fun addCategoryIntoServerList(serverId: String, categoryId: String) {
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId).get()
                .addOnSuccessListener {documentSnapshot ->
                    val categories: MutableList<String> = (documentSnapshot.get("categories") as MutableList<String>)!!
                    categories.add(categoryId)
                    firestore.collection("servers").document(serverId).update("categories", categories)
                    Log.d("FIRESTORE", "Added category into server successfully: ${categories}")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error adding categories: $exception")
                }.await()
            return
        }
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
    }

    override suspend fun updateCategoryData(serverId: String,
                                            categoryId: String,
                                            categoryData: CategoryData,
                                            currentUserId: String) {
        val categoryService = CategoryService(firestore)
        val serverService = ServerService(firestore)
        val category = categoryService.getCategoryById(serverId, categoryId)
        val adminId = serverService.getAdminId(serverId)
        if (category != null) {
            if (adminId == currentUserId) {
                firestore.collection("servers").document(serverId).
                collection("categories").document(categoryId).set(categoryData)
                    .addOnSuccessListener {
                        Log.d("FIRESTORE", "Updated server successfully: $categoryData")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FIRESTORE ERROR", "Error updating server data to Firestore: $exception")
                    }.await()
                return
            }
            Log.e("FIRESTORE ERROR", "Only admin can update category")
            return
        }
        Log.d("FIRESTORE ERROR", "Category not found with ID: $categoryId")
    }

    override suspend fun deleteCategory(serverId: String, categoryId: String, currentUserId: String) {
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        val adminId = serverService.getAdminId(serverId)
        if (server != null) {
            if (adminId == currentUserId) {
                firestore.collection("servers").document(serverId)
                    .collection("categories").document(categoryId).delete()
                    .addOnSuccessListener {
                        Log.d("FIRESTORE", "Delete category successfully")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FIRESTORE ERROR", "Error deleting category: ", exception)
                    }.await()
                return
            }
            Log.e("FIRESTORE ERROR", "Only admin can delete category")
            return
        }
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
    }

    override suspend fun getCategoryById(serverId: String, categoryId: String): CategoryData? {
        var category: CategoryData? = null
        val serverService = ServerService(firestore)
        val server = serverService.getServerDataById(serverId)
        if (server != null) {
            firestore.collection("servers").document(serverId)
                .collection("categories").document(categoryId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val id: String = document.data?.get("id").toString()
                        val name: String = document.data?.get("name").toString()
                        val members: MutableList<UserData> = document.data?.get("members") as MutableList<UserData>
                        val chanels: MutableList<String> = document.data?.get("chanels") as MutableList<String>
                        Log.d("FIRESTORE", "Get category with ID: $serverId successfully")
                        category = CategoryData(name, members, chanels, id = id)
                        Log.d("FIRESTORE", "Category: $server")
                    } else {
                        Log.d("FIRESTORE ERROR", "Server not found with ID: $serverId")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error getting server: ", exception)
                }.await()
            return category
        }
        Log.e("FIRESTORE ERROR", "Server with ID: $serverId not exist")
        return null
    }
}