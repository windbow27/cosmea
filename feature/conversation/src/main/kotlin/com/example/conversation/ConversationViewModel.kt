package com.example.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.service.ChannelService
import com.example.model.ChannelData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConversationViewModel(private val channelService: ChannelService, private val channelId: String) : ViewModel() {
    private val _channelData = MutableStateFlow(ChannelData("","", "", mutableListOf(), mutableListOf()))
    val channelData: StateFlow<ChannelData> get() = _channelData

    init {
        fetchChannelData(channelId)
    }

    fun fetchChannelData(channelId: String) {
        viewModelScope.launch {
            val data = channelService.getChannelById(channelId)
            if (data != null) {
                _channelData.value = data
            }
        }
    }
}

class ConversationViewModelFactory(private val channelService: ChannelService, private val channelId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom( ConversationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return  ConversationViewModel(channelService, channelId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}