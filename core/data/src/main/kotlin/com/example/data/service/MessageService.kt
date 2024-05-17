package com.example.data.service

import android.util.Log
import com.example.data.repo.MessageRepository
import com.example.model.MessageData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MessageService(private val realTimeDB: FirebaseDatabase): MessageRepository {
    override suspend fun addMessageData(channelId: String, messageData: MessageData) {
        realTimeDB.getReference("messages/$channelId").child(messageData.id).setValue(messageData)
            .addOnSuccessListener {
                Log.d("REALTIME DATABASE", "Added message successfully: ${messageData}")

            }
            .addOnFailureListener { exception ->
                Log.e("REALTIME DATABASE ERROR", "Error adding message: $exception")
            }.await()
        addMessageIntoChannelList(channelId, messageData.id)
        return
    }

    override suspend fun addMessageIntoChannelList(channelId: String, messageId: String) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("channels").document(channelId).get()
            .addOnSuccessListener {documentSnapshot ->
                val messages: MutableList<String> = documentSnapshot.get("messages") as? MutableList<String> ?: mutableListOf()
                messages.add(messageId)
                firestore.collection("channels").document(channelId).update("messages", messages)
                Log.d("FIRESTORE", "Added message into channel successfully: ${messages}")
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error adding message: $exception")
            }.await()
        return
    }

    override suspend fun getMessageData(channelId: String): List<MessageData> {
        val messages = mutableListOf<MessageData>()
        val reference = realTimeDB.getReference("messages/$channelId")
        val dataChangeEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages.clear() // Clear existing messages before adding new ones
                dataSnapshot.children.forEach { message ->
                    val id = message.child("id").toString()
                    val author = message.child("author").toString()
                    val receiver = message.child("receiver").toString()
                    val content = message.child("content").toString()
                    val timestamp = message.child("timestamp").toString()
                    val image = message.child("image").toString()
                    val file = message.child("file").toString()
                    messages.add(MessageData(author, receiver, content, timestamp, image, file, id = id))
                    print(messages)
                }
//                messages.addAll(
//                    dataSnapshot.children.mapNotNull {
//                        it.getValue(MessageData::class.java)
//                    }
//                )
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors (optional)
            }
        }
        reference.addValueEventListener(dataChangeEventListener)
//        withContext(Dispatchers.IO) { }
//        reference.removeEventListener(dataChangeEventListener)
        print(messages)
        return messages // Filter by ID and return the message or null
    }


    //
//    override suspend fun getReceiver(messageId: String) {
//        TODO("Not yet implemented")
//    }
//
    override suspend fun deleteMessageData(messageId: String) {
        realTimeDB.getReference("messages/$messageId").removeValue()
            .addOnSuccessListener {
                Log.d("REALTIME DATABASE", "Deleted message successfully")
            }
            .addOnFailureListener {exception ->
                Log.e("REALTIME DATABASE ERROR", "Error deleting message: $exception")
            }.await()
    }

}