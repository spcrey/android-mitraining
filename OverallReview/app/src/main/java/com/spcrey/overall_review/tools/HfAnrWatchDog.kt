package com.spcrey.overall_review.tools

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.github.anrwatchdog.ANRWatchDog
import kotlin.concurrent.Volatile

class HfAnrWatchDog(
    private val mMaxCount: Int = 5,
    private val mInterval: Long = 1000L
) {
    companion object {
        private const val TAG = "HfAnrWatchDog"
    }

    interface AnrListener {
        fun onAppNotResponding(error: RuntimeException);
    }

    var mAnrListener = object : AnrListener {
        override fun onAppNotResponding(error: RuntimeException) {
            throw error
        }
    }

    fun setAnrListener(listener: AnrListener) {
        mAnrListener = listener
    }

    fun setAnrListener(listener: (RuntimeException) -> Unit) {
        mAnrListener = object : AnrListener {
            override fun onAppNotResponding(error: RuntimeException) {
                listener(error)
            }
        }
    }

    private val mHandle = Handler(Looper.getMainLooper())

    @Volatile
    private var mTick = 1
    private var mRunnableFlag = true
    private var mCurrentCount = 0
    private val mTicker = Runnable {
        mTick = 0
    }
    private val mThread = Thread {
        while (mRunnableFlag) {
            mTick = 1
            mHandle.post(mTicker)
            try {
                Thread.sleep(mInterval)
            } catch (e: InterruptedException) {
                Log.d(TAG, "Thread interrupted")
                return@Thread
            }
            if (mTick == 0) {
                Log.d(TAG, "on responsive")
                mCurrentCount = 0
            } else {
                Log.d(TAG, "not responsive")
                mCurrentCount ++
            }
            if (mMaxCount == mCurrentCount) {
                mAnrListener.onAppNotResponding(RuntimeException("ANR error occurred"))
            }
        }
    }
    fun start() { mThread.start() }
    fun stop() {
        mRunnableFlag = false
        mHandle.removeCallbacksAndMessages(null)
    }
}