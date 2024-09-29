package com.xiaomi.weibo_hanpeng.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.widget.Toast

class DynamicBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "DynamicBroadcastReceiver"
    }

    interface NetworkListener {
        fun Connected()
        fun Disconnected()
    }

    private var networkListener: NetworkListener? = null
    fun setNetworkListener(networkListener: NetworkListener) {
        this.networkListener = networkListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isConnected = isNetworkConnected(connectivityManager)

            if (isConnected) {
                this.networkListener?.Connected()
            } else {
                this.networkListener?.Disconnected()
            }
        }
    }

    private fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            @Suppress("DEPRECATION")
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}