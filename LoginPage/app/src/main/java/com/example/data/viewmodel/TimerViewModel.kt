package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TimerViewModel: ViewModel() {
    private var timerJob: Job? = null
    private val TAG = VerifyEmailViewModel::class.simpleName
    var timeLeft = mutableStateOf(60L)
    var isRunning = mutableStateOf(false)
    private var isFinished = mutableStateOf(false)
    private var isTimerReset = mutableStateOf(false)

    private var mfaTimerJob: Job? = null
    private var mfaTimeLeft = mutableStateOf(60L)
    var mfaIsRunning = mutableStateOf(false)
    var mfaIsFinished = mutableStateOf(false)
    private var isMfaTimerReset = mutableStateOf(false)

    /**
     * Timer to resend the otp code when the user clicks the resend otp button.
     */
    fun startTimer(timerDuration: Long = 10){
        if (isRunning.value) return
        isRunning.value = true
        isFinished.value = false
        timerJob = CoroutineScope(Dispatchers.Main).launch {
//        timerJob = viewModelScope.launch {
            while (isRunning.value && timeLeft.value > 0){
                /*if (isTimerReset.value){
                    isTimerReset.value = false
                    break
                }*/
                delay(timerDuration)
                Log.d(TAG, "Timer running inside startTimer()...${timeLeft.value} seconds left")
                timeLeft.value--
            }
            isFinished.value = true
            isRunning.value = false //  when timer reaches zero, set finished state
        }
    }

    fun timeLeft(): Long {
        return timeLeft.value
    }

    fun resetTimer(){
        isFinished.value = false
        isRunning.value = false
        timeLeft.value = 60L
        Log.d(TAG, "Timer reset inside resetTimer()...")
        timerJob?.cancel()
    }

    fun isTimerFinished(): Boolean{
        return isFinished.value
    }

    fun isTimerRunning(): Boolean{
        return isRunning.value
    }

    /**
     * ==========================================================
     * ==========================================================
     */

    /**
     * Timer to invalidate otp code when the user delays to enter the otp code
     */
    fun mfaStartTimer(timerDuration: Long = 10){
        if (mfaIsRunning.value) return
        mfaIsRunning.value = true
        mfaIsFinished.value = false
        mfaTimerJob = CoroutineScope(Dispatchers.Main).launch {
//        mfaTimerJob = viewModelScope.launch {
            while (mfaIsRunning.value && mfaTimeLeft.value > 0){
                /*if (isMfaTimerReset.value){
                    Log.d(TAG, "\n\n\nIs mfa timer reset...${isMfaTimerReset.value}\n\n\n")
                    isMfaTimerReset.value = false
                    break
                }*/
                Log.d(TAG, "Timer running inside mfaStartTimer()...${mfaTimeLeft.value} seconds left")
                delay(timerDuration)
                mfaTimeLeft.value--
            }
            mfaIsFinished.value = true
            mfaIsRunning.value = false //  when timer reaches zero, set finished state
        }
    }

    fun mfaTimeLeft(): Long {
        return mfaTimeLeft.value
    }

    fun isMfaCounterFinished(): Boolean{
        Log.d(TAG, "mfaIsFinished = ${mfaIsFinished.value}")
        return mfaIsFinished.value
    }

    fun isMfaTimerRunning(): Boolean{
        Log.d(TAG, "Timer running inside isMfaTimerRunning(): ${mfaIsRunning.value}...${mfaTimeLeft.value} seconds left")
        return mfaIsRunning.value
    }

    fun mfaResetTimer(){
        Log.d(TAG, "Timer reset inside mfaResetTimer()...")
        mfaIsFinished.value = false
        mfaIsRunning.value = false
        mfaTimeLeft.value = 60L
        mfaTimerJob?.cancel()
    }
}