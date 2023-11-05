package com.example.myapplication

import android.util.Log

fun Any.logd(message: String? = "No message provided!", cause: Throwable? = null) {
    Log.d(this.javaClass.simpleName, message, cause)
}