package com.spcrey.overall_review.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BindService : Service() {
    override fun onBind(intent: Intent): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        val service: BindService
            get() = this@BindService
    }

    val testText
        get() = "Text from Bind Service"
}