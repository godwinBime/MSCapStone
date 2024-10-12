package com.example.loginpage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.data.viewmodel.HomeViewModel
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.navigation.ScreenMain

class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.simpleName

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
                    ScreenMain(homeViewModel = HomeViewModel())
                }
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "Inside on destroy")
//    }

}
