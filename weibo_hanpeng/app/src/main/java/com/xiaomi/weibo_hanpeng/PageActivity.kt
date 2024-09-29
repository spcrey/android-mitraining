package com.xiaomi.weibo_hanpeng

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaomi.weibo_hanpeng.fragment.MineFragment
import com.xiaomi.weibo_hanpeng.fragment.RecommendFragment
import com.xiaomi.weibo_hanpeng.tools.CachedData
import com.xiaomi.weibo_hanpeng.tools.ServerApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

class PageActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PageActivity"
    }

    private val btnLoginInOut by lazy { findViewById<TextView>(R.id.btn_login_in_out) }
    private val btnLoginInOutManager by lazy { BtnLoginInOutManager(btnLoginInOut) }
    private val fragmentCommitManager by lazy { FragmentCommitManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)
        EventBus.getDefault().register(this)
        Log.d(TAG, "onCreate")

        val icRecommend = findViewById<ImageView>(R.id.ic_recommend)
        val textRecommend = findViewById<TextView>(R.id.text_recommend)
        val icMine = findViewById<ImageView>(R.id.ic_mine)
        val textMine = findViewById<TextView>(R.id.text_mine)
        val textTitle = findViewById<TextView>(R.id.text_title)
        val btnToFragmentRecommend = findViewById<TextView>(R.id.to_fragment_recommend)
        val btnToFragmentMine = findViewById<TextView>(R.id.to_fragment_mine)
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val fragmentTitleManager = FragmentTitleManager(this, textTitle, icRecommend, textRecommend, icMine, textMine)
        fragmentCommitManager.update()

        ServerApiManager.setEvent403Listener222(object : ServerApiManager.Event403Listener {
            override fun on403() {
                runOnUiThread {
                    Toast.makeText(this@PageActivity, "登录已过期", Toast.LENGTH_SHORT).show()
                    val editor = sharedPreferences?.edit()
                    if (editor != null) {
                        editor.remove("token")
                        editor.apply()
                    }
                    btnLoginInOutManager.status = BtnLoginInOutManager.Status.CAN_IN
                    CachedData.userInfo = null
                    EventBus.getDefault().post(MineFragment.LoginEvent())
                    val intent = Intent(this@PageActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        })


        token?.let { nonNullToken ->
            lifecycleScope.launch {
                try {
                    val commonData = withContext(Dispatchers.IO) {
                        ServerApiManager.apiService.info("Bearer $nonNullToken").await()
                    }
                    when (commonData.code) {
                        200 -> {
                            CachedData.userInfo = commonData.data
                        }
                        403 -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@PageActivity, "登陆已过期", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "request failed: ${e.message}")
                }
            }
        }

        btnLoginInOut.setOnClickListener {
            if (btnLoginInOutManager.status == BtnLoginInOutManager.Status.CAN_OUT) {
                val editor = sharedPreferences?.edit()
                if (editor != null) {
                    editor.remove("token")
                    editor.apply()
                }
                btnLoginInOutManager.status = BtnLoginInOutManager.Status.CAN_IN
                CachedData.userInfo = null
                EventBus.getDefault().post(MineFragment.LoginEvent())
            }
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        btnToFragmentRecommend.setOnClickListener {
            fragmentTitleManager.status = FragmentTitleManager.Status.RECOMMEND
            btnLoginInOutManager.status = BtnLoginInOutManager.Status.HIDE
            fragmentCommitManager.status = FragmentCommitManager.Status.RECOMMEND
        }

        btnToFragmentMine.setOnClickListener {
            fragmentTitleManager.status = FragmentTitleManager.Status.MINE
            if (CachedData.userInfo == null) {
                btnLoginInOutManager.status = BtnLoginInOutManager.Status.CAN_IN
            } else {
                btnLoginInOutManager.status = BtnLoginInOutManager.Status.CAN_OUT
            }
            fragmentCommitManager.status = FragmentCommitManager.Status.MINE
        }
    }

    class LoginEvent

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event:  LoginEvent) {
        if(btnLoginInOutManager.status==BtnLoginInOutManager.Status.CAN_IN)
            btnLoginInOutManager.status = BtnLoginInOutManager.Status.CAN_OUT
    }

    class FragmentTitleManager(
        private val context: Context, private val textTitle: TextView, private val icRecommend: ImageView,
        private val textRecommend: TextView, private val icMine: ImageView, private val textMine: TextView) {
        enum class Status { RECOMMEND, MINE }
        var status: Status = Status.RECOMMEND
            set(value) {
                when (value) {
                    Status.RECOMMEND -> {
                        textTitle.text = "iH推荐"
                        icRecommend.setImageResource(R.drawable.ic_recommend_onclick)
                        textRecommend.setTextColor(context.resources.getColor(R.color.blue));
                        textRecommend.alpha = 1f
                        icMine.setImageResource(R.drawable.ic_mine_unclick)
                        textMine.setTextColor(context.resources.getColor(R.color.black));
                        textMine.alpha = 0.4f
                    }
                    Status.MINE -> {
                        textTitle.text = "我的"
                        icRecommend.setImageResource(R.drawable.ic_recommend_unclick)
                        textRecommend.setTextColor(context.resources.getColor(R.color.black));
                        textRecommend.alpha = 0.4f
                        icMine.setImageResource(R.drawable.ic_mine_onclick)
                        textMine.setTextColor(context.resources.getColor(R.color.blue));
                        textMine.alpha = 1.0f
                    }
                }
                field = value
            }
    }

    class BtnLoginInOutManager(private val btnLoginInOut: TextView) {
        enum class Status { CAN_IN, CAN_OUT, HIDE }
        var status: Status = Status.HIDE
            set(value) {
                when(value) {
                    Status.CAN_IN -> {
                        btnLoginInOut.alpha = 1.0f
                        btnLoginInOut.text = "登录"
                    }
                    Status.CAN_OUT -> {
                        btnLoginInOut.alpha = 1.0f
                        btnLoginInOut.text = "退出登录"
                    }
                    Status.HIDE -> {
                        btnLoginInOut.alpha = 0.0f
                    }
                }
                field = value
            }
    }

    class FragmentCommitManager(activity: PageActivity) {

        private val activityRef: WeakReference<PageActivity>
        companion object {
            private const val TAG = "TAG_FRAGMENT"
        }
        init {
            activityRef = WeakReference(activity)
            activity.supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(
                    R.id.fragment_container_view,
                    RecommendFragment::class.java,
                    null,
                    TAG
                ).commit()
        }
        enum class Status { RECOMMEND, MINE }

        fun update() {
            status = status
        }

        var status = Status.RECOMMEND
            set(value) {
                val activity = activityRef.get()
                if (activity != null) {
                    val fragmentClass = when (value) {
                        Status.RECOMMEND -> RecommendFragment::class.java
                        Status.MINE -> MineFragment::class.java
                    }
                    activity.supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(
                            R.id.fragment_container_view,
                            fragmentClass,
                            null,
                            TAG
                        )
                        .commit()
                }
                field = value
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}