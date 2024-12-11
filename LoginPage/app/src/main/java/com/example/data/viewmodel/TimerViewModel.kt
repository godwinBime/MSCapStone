package com.example.data.viewmodel

import android.content.Context
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
    private val TAG = TimerViewModel::class.simpleName
    var timeLeft = mutableStateOf(60L)
    var isRunning = mutableStateOf(false)
    private var isFinished = mutableStateOf(false)
    private var mfaTimerJob: Job? = null
    private var mfaTimeLeft = mutableStateOf(60L)
    var mfaIsRunning = mutableStateOf(false)
    private var mfaIsFinished = mutableStateOf(false)

    fun setAuthComplete(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthComplete", true)
        editor.apply()
    }

    fun isAuthComplete(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isAuthComplete", false)
    }

    fun resetAuthFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthComplete", false)
        editor.apply()
        Log.d(TAG, "inside resetAuthTime()...resetting authcomplete")
    }

    fun saveAuthStartTime(context: Context, startTime: Long){
       val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("startTime", startTime)
        Log.d(TAG, "startTime inside saveAuthStartTime()...$startTime")
        editor.apply()
    }

    fun getAuthStartTime(context: Context): Long{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("startTime", 0L)
    }

    fun saveAuthEndTime(context: Context, endTime: Long){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("endTime", endTime)
        Log.d(TAG, "endTime inside saveAuthEndTime()...$endTime")
        editor.apply()
    }

    fun getAuthEndTime(context: Context): Long{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("endTime", 0L)
    }

    fun setAuthTimeRecorded(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthStartTimeRecorded", true)
        editor.apply()
    }

    fun isAuthTimeRecorded(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isAuthStartTimeRecorded", false)
    }

    fun setUserTypingFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserTyping", true)
        editor.apply()
    }

    fun isUserTyping(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isUserTyping", false)
    }

    fun resetUserTypingFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserTyping", false)
        editor.apply()
        Log.d(TAG, "inside resetUserTypingFlag()...resetting user input flags")
    }

    fun resetAuthStartTime(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthStartTimeRecorded", false)
        editor.apply()
        Log.d(TAG, "inside resetAuthStartTime()...resetting AuthStartTime")
    }

    fun resetTimeRecordingFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuthStartTimeRecorded", false)
        editor.putLong("startTime", 0L)
        editor.putLong("endTime", 0L)
        editor.putBoolean("isUserCreatingAccount", false)
        editor.putBoolean("isUserTyping", false)
        editor.putBoolean("isAuthComplete", false)
        editor.apply()
        Log.d(TAG, "inside resetTimeRecordingFlag()...resetting all flags")
    }

    fun setUserCreatingAccountFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserCreatingAccount", true)
        editor.apply()
    }

    private fun isUserCreatingAccount(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isUserCreatingAccount", false)
    }

    /*fun setUserLoginFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserLogging-In", true)
        editor.apply()
    }

    private fun isUserLoggingIn(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isUserLogging-In", false)
    }*/

    fun calculateAuthDuration(startTime: Long, endTime: Long, context: Context): String{
        val isUserCreatingAccount = isUserCreatingAccount(context = context)
        val duration = endTime - startTime
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        val summary = if (isUserCreatingAccount){
            "Account Creation and Login Duration: \n$minutes minute(s) and $seconds seconds"
        }else{
            "Login Duration: $minutes minute(s) and $seconds seconds"
        }
        return summary
    }

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
                delay(timerDuration)
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
//                Log.d(TAG, "Timer running inside mfaStartTimer()...${mfaTimeLeft.value} seconds left")
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