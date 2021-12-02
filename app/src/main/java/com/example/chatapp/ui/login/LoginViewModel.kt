package com.example.chatapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.util.Constants.MIN_USERNAME_LENGTH
import com.example.chatapp.util.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private fun isValidUsername(username: String) =
        username.length >= MIN_USERNAME_LENGTH

    fun connectUser(username: String) {
        val trimUsername = username.trim()
        viewModelScope.launch {
            if (isValidUsername(trimUsername)) {
                val result = client.connectGuestUser(
                    userId = trimUsername,
                    username = trimUsername
                ).await()
                if (result.isError) {
                    _loginEvent.emit(LoginEvent.ErrorLogIn(result.error().message ?: "Unknown error"))
                    return@launch
                }
                _loginEvent.emit(LoginEvent.Success)
            } else {
                _loginEvent.emit(LoginEvent.ErrorInputTooShort)
            }
        }
    }
}