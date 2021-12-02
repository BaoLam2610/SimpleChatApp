package com.example.chatapp.util

sealed class LoginEvent {
    object ErrorInputTooShort : LoginEvent()
    data class ErrorLogIn(val error: String) : LoginEvent()
    object Success : LoginEvent()
}