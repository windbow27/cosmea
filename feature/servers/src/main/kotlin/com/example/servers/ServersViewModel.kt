package com.example.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.service.ChannelService
import com.example.data.service.ServerService
import com.example.model.ChannelData
import com.example.model.ServerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServersViewModel(private val serverService: ServerService, private val channelService: ChannelService) : ViewModel() {
    private val _servers = MutableStateFlow<List<ServerData>>(emptyList())
    val servers: StateFlow<List<ServerData>> = _servers

    private val _channels = MutableStateFlow<Map<String, List<ChannelData?>>>(emptyMap())
    val channels: StateFlow<Map<String, List<ChannelData?>>> = _channels

    init {
        fetchServersAndChannels()
    }

    private fun fetchServersAndChannels() {
        serverService.getAllServerData().addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                // Handle the error
                return@addSnapshotListener
            }

            querySnapshot?.let {
                val fetchedServers = it.documents.mapNotNull { document ->
                    document.toObject(ServerData::class.java)
                }
                _servers.value = fetchedServers

                fetchedServers.forEach { server ->
                    server.channels.forEach { channelId ->
                        channelService.getChannelDocument(channelId).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                            firebaseFirestoreException?.let {
                                // Handle the error
                                return@addSnapshotListener
                            }

                            documentSnapshot?.let { document ->
                                val channel = document.toObject(ChannelData::class.java)
                                val fetchedChannels = _channels.value.toMutableMap()
                                fetchedChannels[server.id]?.let { channels ->
                                    val updatedChannels = channels.toMutableList()
                                    if (!updatedChannels.contains(channel)) {
                                        updatedChannels.add(channel)
                                        fetchedChannels[server.id] = updatedChannels
                                    }
                                } ?: run {
                                    fetchedChannels[server.id] = mutableListOf(channel)
                                }
                                _channels.value = fetchedChannels
                            }
                        }
                    }
                }
            }
        }
    }
}

class ServersViewModelFactory(private val serverService: ServerService, private val channelService: ChannelService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ServersViewModel(serverService, channelService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}