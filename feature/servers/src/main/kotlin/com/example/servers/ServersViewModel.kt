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
        viewModelScope.launch {
            _servers.value = serverService.getAllServerData()
            _servers.value.forEach { server ->
                _channels.value += (server.id to getAllChannelData(server.id, server.channels))
            }
        }
    }

    private suspend fun getAllChannelData(serverId: String?, channelIds: List<String>?): List<ChannelData?> {
        return channelIds?.map { channelId ->
            serverId?.let { channelService.getChannelById(it, channelId) }
        } ?: listOf()
    }

    fun selectServer(serverId: String) {
        viewModelScope.launch {
            val server = serverService.getServerDataById(serverId)
            _channels.value += (serverId to getAllChannelData(serverId, server?.channels))
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

class ChannelViewModel(private val channelService: ChannelService) : ViewModel() {
    private val _channelData = MutableStateFlow<ChannelData?>(null)
    val channelData: StateFlow<ChannelData?> get() = _channelData

    fun fetchChannelData(serverId: String, channelId: String) {
        viewModelScope.launch {
            _channelData.value = channelService.getChannelById(serverId, channelId)
        }
    }
}

class ChannelViewModelFactory(private val channelService: ChannelService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChannelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChannelViewModel(channelService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}