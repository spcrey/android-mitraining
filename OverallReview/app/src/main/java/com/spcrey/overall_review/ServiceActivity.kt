package com.spcrey.overall_review

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spcrey.overall_review.service.BindService

class ServiceActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ServiceActivity"
    }

    private var mBound = false

    private var mBindService: BindService? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            val localBinder = service as BindService.LocalBinder
            mBindService = localBinder.service
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            mBindService = null
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val btnToBindService = findViewById<Button>(R.id.btn_bind_service)
        btnToBindService.setOnClickListener {
            Log.d(TAG, "btnToBindService.setOnClickListener")
            if (!mBound) {
                val intent = Intent(this, BindService::class.java)
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
            }

        }
        val btnToUnbindService = findViewById<Button>(R.id.btn_unbind_service)
        btnToUnbindService.setOnClickListener {
            Log.d(TAG, "mBound: $mBound")
            Log.d(TAG, "btnToUnbindService.setOnClickListener")
            if (mBound) {
                unbindService(mConnection)
                mBindService = null
                mBound = false
            }
        }
        val btnGetBindText = findViewById<Button>(R.id.btn_get_bind_text)
        btnGetBindText.setOnClickListener {
            Log.d(TAG, "btnGetBindText.setOnClickListener")
            val bindText = mBindService?.testText?:"No Text"
            Toast.makeText(this, bindText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBound) {
            unbindService(mConnection)
        }
    }
}