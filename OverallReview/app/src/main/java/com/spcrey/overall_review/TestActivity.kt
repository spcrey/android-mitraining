package com.spcrey.overall_review

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.spcrey.overall_review.tools.HfAnrWatchDog
import com.spcrey.overall_review.tools.ServerApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TestActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "TestActivity"
    }

    private val anrWatchDog = HfAnrWatchDog(5)

    private val mHandle = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        anrWatchDog.start()

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
//        val edit = sharedPreferences.edit()
//        edit.putString("key", "value")
//        edit.apply()

        val value = sharedPreferences.getString("key", "")
        Log.d(TAG, "save key: $value")


        anrWatchDog.setAnrListener{ error ->
            println("Custom ANR handling: ${error.message}")
        }

        val btnTestNetwork = findViewById<Button>(R.id.btn_test_network)
        btnTestNetwork.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val commonData = ServerApiManager.apiService.queryGame("55").await()
                        Log.d(TAG, "request successful")
                        Log.d(TAG, commonData.toString())
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@TestActivity, "request successful", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "request failed: ${e.message}")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        anrWatchDog.stop()
        mHandle.removeCallbacksAndMessages(null)
    }
}
