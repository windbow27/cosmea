package com.example.data.service

import android.util.Log
import com.example.data.repo.MessageRepository
import com.example.model.MessageData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun getMessageData(channelId: String): Flow<List<MessageData>> = callbackFlow {
        val reference = realTimeDB.getReference("messages/$channelId")
        val dataChangeEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messages = mutableListOf<MessageData>()
                dataSnapshot.children.forEach { message ->
                    val id = message.child("id").getValue(String::class.java)
                    val author = message.child("author").getValue(String::class.java)
                    val receiver = message.child("receiver").getValue(String::class.java)
                    val content = message.child("content").getValue(String::class.java)
                    val timestamp = message.child("timestamp").getValue(String::class.java)
                    val image = message.child("image").getValue(String::class.java)
                    val file = message.child("file").getValue(String::class.java)
                    if (id != null && author != null && receiver != null && content != null && timestamp != null) {
                        messages.add(MessageData(author, receiver, content, timestamp, image, file, id))
                    }
                }
                trySend(messages).isSuccess // Send the messages to the Flow
            }
            override fun onCancelled(databaseError: DatabaseError) {
                close(databaseError.toException()) // Close the Flow with an error if there's an error
            }
        }
        reference.addValueEventListener(dataChangeEventListener)
        awaitClose { reference.removeEventListener(dataChangeEventListener) } // Remove the listener when the Flow is closed
    }

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