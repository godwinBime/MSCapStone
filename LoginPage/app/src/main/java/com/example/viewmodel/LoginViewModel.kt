package com.example.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel(){
//    private var _username: String by mutableStateOf("")
//    val readUsername: String
//        get() = _username

    private val _username = mutableStateOf("")
    val username: MutableState<String> = _username

    fun updateUsername(username: String){
        _username.value = username
    }
}