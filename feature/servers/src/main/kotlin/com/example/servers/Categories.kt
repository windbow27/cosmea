package com.example.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.icon.Icons

@Composable
fun Categories(
    name: String,
    channels: List<String>,
    listener: ChannelListener
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded = !expanded }
        ) {
            Icon(
                imageVector = if (expanded) Icons.ArrowDown else Icons.ArrowUp,
                contentDescription = "Expand/Collapse Icon",
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        if (expanded) {
            Channels(channels, listener)
        }
    }
}