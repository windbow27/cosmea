package com.example.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Channels(channels: List<String>, listener: ChannelListener) {
    channels.forEach { channel ->
        Text(
            text = channel,
            modifier = Modifier
                .padding(start = 32.dp, top = 8.dp)
                .clickable {
                    listener.onChannelSelected(channel)
                }
        )
    }
}

interface ChannelListener {
    fun onChannelSelected(channel: String)
}