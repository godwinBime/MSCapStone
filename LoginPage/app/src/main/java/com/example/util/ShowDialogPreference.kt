package com.example.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Checks if the AlertDialog has been displayed before
 * using the SharedPreferences flag. Returns true if
 * it's the first launch...
 */
fun isFirstLaunch(context: Context): Boolean{
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FirstLaunchPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFirstLaunch", true)
}

/**
 * Sets the flag to false in SharedPreferences after the dialog
 * has been displayed for the first time
 */
fun setFirstLaunchFlag(context: Context, isFirstLaunch: Boolean){
    val  sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FirstLaunchPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isFirstLaunch", isFirstLaunch)
    editor.apply()
}