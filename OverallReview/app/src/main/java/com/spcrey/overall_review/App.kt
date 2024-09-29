package com.spcrey.overall_review

import android.app.Application
import android.util.Log

class App : Application() {
    companion object {
        private const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }
}