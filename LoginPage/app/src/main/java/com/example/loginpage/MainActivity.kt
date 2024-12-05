package com.example.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.data.viewmodel.TimerViewModel
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.navigation.ScreenMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
//    private val timerViewModel: TimerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
    }
/*
    override fun onStart() {
        super.onStart()
        val context = this
        lifecycleScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
        }
    }

    override fun onRestart() {
        super.onRestart()
        val context = this
        lifecycleScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
        }
    }

    override fun onResume() {
        super.onResume()
        val context = this
        lifecycleScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val context = this
        lifecycleScope.launch(Dispatchers.IO) {
            timerViewModel.resetTimeRecordingFlag(context = context)
        }
    }
    */
}