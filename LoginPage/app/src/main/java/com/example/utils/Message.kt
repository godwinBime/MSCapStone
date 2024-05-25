package com.example.utils

import android.content.Context
import android.widget.Toast

//fun getMessage(toastMessage: String): String{
//    return toastMessage
//}

fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_LONG
) = Toast.makeText(this, message, duration).show()