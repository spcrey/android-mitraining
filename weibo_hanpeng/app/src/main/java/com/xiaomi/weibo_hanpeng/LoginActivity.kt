package com.xiaomi.weibo_hanpeng

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xiaomi.weibo_hanpeng.fragment.MineFragment
import com.xiaomi.weibo_hanpeng.fragment.RecommendFragment
import com.xiaomi.weibo_hanpeng.tools.CachedData
import com.xiaomi.weibo_hanpeng.tools.ServerApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val editTextInputPhone = findViewById<EditText>(R.id.edit_text_input_phone)
        val editTextInputCode = findViewById<EditText>(R.id.edit_text_input_code)
        val btnGetCode = findViewById<TextView>(R.id.btn_get_code)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnBack = findViewById<TextView>(R.id.btn_back)
        var phone = ""
        var code = ""
        var isAllowLogin = false
        var isAllowGetCode = true

        btnBack.setOnClickListener {
            finish()
        }

        editTextInputPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    phone = s.toString()
                    when (s.length) {
                        12 -> {
                            val newSequence = s.subSequence(0, s.length - 1)
                            editTextInputPhone.setText(newSequence)
                            editTextInputPhone.setSelection(newSequence.length)
                        }
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) { }
        })

        editTextInputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    code = s.toString()
                    when (s.length) {
                        in 0..5 -> {
                            isAllowLogin = false
                            btnLogin.setBackgroundResource(R.drawable.bg_gray_r16dp)
                        }
                        6 -> {
                            isAllowLogin = true
                            btnLogin.setBackgroundResource(R.drawable.btn_bg_blue_r16dp)
                        }
                        else -> {
                            val newSequence = s.subSequence(0, s.length - 1)
                            editTextInputCode.setText(newSequence)
                            editTextInputCode.setSelection(newSequence.length)
                        }
                    }
                } else {
                    btnLogin.setBackgroundResource(R.drawable.bg_gray_r16dp)
                }
            }
            override fun afterTextChanged(s: Editable?) { }
        })

        btnGetCode.setOnClickListener {
            if (isAllowGetCode) {
                Log.d(TAG, "tel=$phone")
                if (phone.length < 11) {
                    Toast.makeText(
                        this@LoginActivity,
                        "请输⼊完整⼿机号",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                val phoneRequest = ServerApiManager.PhoneRequest(editTextInputPhone.text.toString())
                                val sendCodeData = ServerApiManager.apiService.sendCode(phoneRequest).await()
                                Log.d(TAG, "request successful")
                                Log.d(TAG, sendCodeData.toString())
                            } catch (e: Exception) {
                                Log.d(TAG, "request failed: ${e.message}")
                            }
                            for (i in 60 downTo 0) {
                                withContext(Dispatchers.Main) {
                                    btnGetCode.setTextColor(resources.getColor(R.color.deep_gray))
                                    btnGetCode.text = "$i s 后可重新获取"
                                }
                                delay(1000)
                            }
                            withContext(Dispatchers.Main) {
                                btnGetCode.setTextColor(resources.getColor(R.color.blue))
                                btnGetCode.text = "获取验证码"
                            }
                            isAllowGetCode = true
                        }
                    }
                    isAllowGetCode = false
                }
            }
        }
        btnLogin.setOnClickListener {
            if (isAllowLogin && phone.length == 11 && code.length == 6) {
                btnLogin.text = "登录中"
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            val phoneCodeRequest = ServerApiManager.PhoneCodeRequest(phone, code)
                            val loginData = ServerApiManager.apiService.login(phoneCodeRequest).await()
                            if(loginData.code==200) {
                                Log.d(TAG, "login successful")
                                Log.d(TAG, loginData.toString())
                                val edit = sharedPreferences.edit()
                                val token = loginData.data
                                edit.putString("token", token)
                                edit.putString("tokenBackup", token)
                                edit.apply()

                                try {
                                    val userInfoData = ServerApiManager.apiService.info("Bearer $token").await()
                                    CachedData.userInfo = userInfoData.data
                                    try {
                                        val homePageData = ServerApiManager.apiService.homePage("Bearer $token", current = 1).await()
                                        CachedData.homePageRecords.clear()
                                        CachedData.homePageRecords.addAll(homePageData.data.records)
                                        EventBus.getDefault().post(PageActivity.LoginEvent())
                                        EventBus.getDefault().post(MineFragment.LoginEvent())
                                        EventBus.getDefault().post(RecommendFragment.LoginEvent())
                                        withContext(Dispatchers.Main) {
                                            finish()
                                        }
                                    } catch (e: Exception) {
                                        Log.d(TAG, "request failed: ${e.message}")
                                    }
                                } catch (e: Exception) {
                                    Log.d(TAG, "request failed: ${e.message}")
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    btnLogin.text = "登录"
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "验证码错误",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "request failed: ${e.message}")
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "格式错误",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}