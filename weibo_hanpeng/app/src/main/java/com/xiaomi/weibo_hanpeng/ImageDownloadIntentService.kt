package com.xiaomi.weibo_hanpeng

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.util.UUID

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "com.xiaomi.weibo_hanpeng.action.FOO"
private const val ACTION_BAZ = "com.xiaomi.weibo_hanpeng.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "com.xiaomi.weibo_hanpeng.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.xiaomi.weibo_hanpeng.extra.PARAM2"
/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class ImageDownloadIntentService : IntentService("ImageDownloadIntentService") {

    override fun onHandleIntent(intent: Intent?) {

    }

}