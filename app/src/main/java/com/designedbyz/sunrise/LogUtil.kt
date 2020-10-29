package com.designedbyz.sunrise

import android.util.Log
import javax.inject.Inject

class LogUtil @Inject constructor() {
    fun e(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun d(tag: String, message: String, throwable: Throwable) {
        Log.d(tag, message, throwable)
    }

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
    fun i(tag: String, message: String, throwable: Throwable) {
        Log.i(tag, message, throwable)
    }

    fun v(tag: String, message: String, throwable: Throwable) {
        Log.v(tag, message, throwable)
    }

    fun w(tag: String, message: String, throwable: Throwable) {
        Log.w(tag, message, throwable)
    }
    fun w(tag: String, message: String) {
        Log.w(tag, message)
    }
}
