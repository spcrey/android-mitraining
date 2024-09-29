package com.xiaomi.weibo_hanpeng.tools

import android.content.Context
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CachedData {
    var userInfo: ServerApiManager.UserInfo? = null
    val homePageRecords: MutableList<ServerApiManager.WeiboInfo> = mutableListOf()

}