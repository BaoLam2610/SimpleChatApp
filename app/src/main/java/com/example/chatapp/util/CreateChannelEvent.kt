package com.example.chatapp.util

sealed class CreateChannelEvent {
    data class Error(val error: String) : CreateChannelEvent()
    object Success : CreateChannelEvent()
}