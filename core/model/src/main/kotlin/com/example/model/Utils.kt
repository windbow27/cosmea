package com.example.model

import java.security.MessageDigest

fun generateId(username: String, additionalInfo: String? = null): String {
    val idBase = username + (additionalInfo ?: "")

    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(idBase.toByteArray())

    val hashedString = hashedBytes.joinToString("") { "%02x".format(it) }
    return hashedString.substring(0, 9)
}

const val EXPIRED_TIME = 259200000

val SERVER_AVATAR = listOf(
    "https://firebasestorage.googleapis.com/v0/b/cosmea-69930.appspot.com/o/images%2F0498af5b-761c-4e3c-b091-08dc44c45b22.jpg?alt=media&token=696d05db-7169-4011-8cfc-7b67c4fbed4b",
    "https://firebasestorage.googleapis.com/v0/b/cosmea-69930.appspot.com/o/images%2F1e7dc961-531c-46f2-95ab-d74fccdb1a26.jpg?alt=media&token=a6499e42-9e18-461d-983d-378b9e05c686",
    "https://firebasestorage.googleapis.com/v0/b/cosmea-69930.appspot.com/o/images%2F71f1ec98-5d4c-4b88-9aeb-2cce232cb5a8.jpg?alt=media&token=4e5172de-bdfe-45f9-86e5-62f6060a7bee",
    "https://firebasestorage.googleapis.com/v0/b/cosmea-69930.appspot.com/o/images%2F9924c64d-35c3-4368-ac59-93ce108ee6ad.jpg?alt=media&token=cdcbf3f8-f6a4-45d0-9540-92ee3793a54e",
)

