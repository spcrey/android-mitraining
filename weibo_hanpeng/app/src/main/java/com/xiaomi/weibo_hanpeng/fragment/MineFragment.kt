package com.xiaomi.weibo_hanpeng.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xiaomi.weibo_hanpeng.LoginActivity
import com.xiaomi.weibo_hanpeng.PageActivity
import com.xiaomi.weibo_hanpeng.R
import com.xiaomi.weibo_hanpeng.tools.CachedData
import com.xiaomi.weibo_hanpeng.tools.ServerApiManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : Fragment() {

    companion object {
        private const val TAG = "MineFragment"
    }

    private var isLogin = false
    private lateinit var view: View

    private val textUsername by lazy {
        view.findViewById<TextView>(R.id.text_username)
    }
    private val textFan by lazy {
        view.findViewById<TextView>(R.id.text_fan)
    }
    private val imgAvatar by lazy {
        view.findViewById<ImageView>(R.id.img_avatar)
    }
    private val textMine by lazy {
        view.findViewById<TextView>(R.id.text_mine)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    private fun updateUi() {

        val userInfo = CachedData.userInfo
        if (userInfo != null) {
            textUsername.text = userInfo.username
            textFan.text = "粉丝 9999"
            textMine.text = "你没有新的动态哦~"
            Glide.with(imgAvatar.context)
                .load(userInfo.avatar)
                .into(imgAvatar)
            imgAvatar.alpha = 1.0f
            isLogin = true
        } else {
            textUsername.text = "请先登录"
            textFan.text = "点击头像去登陆"
            textMine.text = "登陆后查看"
            imgAvatar.setImageResource(R.drawable.circle_shape)
            imgAvatar.alpha = 0.06f
            isLogin = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.view = view
        EventBus.getDefault().register(this)
        updateUi()
        imgAvatar.setOnClickListener {
            if (!isLogin) {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    class LoginEvent

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event:  LoginEvent) {
        updateUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}