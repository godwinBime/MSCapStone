package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
    private var timerJob: Job? = null
    private val TAG = VerifyEmailViewModel::class.simpleName
    var timeLeft = mutableStateOf(10L)
    var isRunning = mutableStateOf(false)
    var isFinished = mutableStateOf(false)

    fun startTimer(timerDuration: Long = 10){
        if (isRunning.value) return
        isRunning.value = true
        isFinished.value = false
        timerJob = viewModelScope.launch {
            while (isRunning.value && timeLeft.value > 0){
                delay(timerDuration)
                timeLeft.value--
            }
//            Log.d(TAG, "isFinished: ${isFinished.value}")
//            Log.d(TAG, "isRunning: ${isRunning.value}")
            isFinished.value = true
            isRunning.value = false //  when timer reaches zero, set finished state
        }
    }
}