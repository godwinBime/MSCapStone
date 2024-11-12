package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.ProfileViewModel
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.navigation.ScreenMain

class MainActivity : ComponentActivity() {
//    private val TAG = ProfileViewModel::class.simpleName
//    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Inside onCreate")
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
                Log.d(TAG, "Inside onStart")
        profileViewModel.sessionJustStarted.value = true
    }

    override fun onResume() {
        super.onResume()
                Log.d(TAG, "Inside onResume")
        profileViewModel.sessionJustResumed.value = true
    }

    override fun onRestart() {
        super.onRestart()
                Log.d(TAG, "Inside onRestart")
        profileViewModel.sessionJustReStarted.value = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Inside on destroy")
    }
*/
}
