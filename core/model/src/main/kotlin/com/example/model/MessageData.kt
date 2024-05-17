package com.example.model

import java.time.Instant

class MessageData(
    var author: String,
    var receiver: String,
    var content: String,
    var timestamp: String = Instant.now().toEpochMilli().toString(),
    var image: String? = null,
    var file: String? = null,
    var id: String = generateId((author + receiver + content + timestamp)),
)