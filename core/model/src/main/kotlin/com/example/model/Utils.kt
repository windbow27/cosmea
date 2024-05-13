package com.example.model

import java.security.MessageDigest

fun generateUserId(username: String, additionalInfo: String? = null): String {
    val idBase = username + (additionalInfo ?: "")

    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(idBase.toByteArray())

    val hashedString = hashedBytes.joinToString("") { "%02x".format(it) }
    return hashedString.substring(0, 9)
}


