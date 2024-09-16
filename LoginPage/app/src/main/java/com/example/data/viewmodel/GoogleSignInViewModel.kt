package com.example.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.google.GoogleSignInState
import com.example.data.repository.AuthenticationRepository
import com.example.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val repository: AuthenticationRepository): ViewModel() {
        private val _googleState = mutableStateOf(GoogleSignInState())
        val googleState: State<GoogleSignInState> = _googleState

        fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
            repository.googleSignIn(credential).collect{result ->
                when(result){
                    is Resource.Success -> {
                        _googleState.value = GoogleSignInState(success = result.data)
                    }
                    is Resource.Loading -> {
                        _googleState.value = GoogleSignInState(loading = true)
                    }
                    is Resource.Error -> {
                        _googleState.value = GoogleSignInState(error = result.message!!)
                    }
                }
        }
    }
}