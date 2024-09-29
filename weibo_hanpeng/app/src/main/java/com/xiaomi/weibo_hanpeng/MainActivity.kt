package com.xiaomi.weibo_hanpeng

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.xiaomi.weibo_hanpeng.tools.ServerApiManager
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val isAgreeTermCondition = sharedPreferences.getBoolean("isAgreeItem", false)
        val btnAgree = findViewById<Button>(R.id.btn_agree)
        val btnDisagree = findViewById<TextView>(R.id.btn_disagree)
        val textTermCondition = findViewById<TextView>(R.id.text_term_condition)
        val textString = "欢迎使用iH微博，我们将严格遵守相关法律和隐私政策保护您的个人隐私，请您阅读并同意《用户协议》与《隐私政策》"
        val linkSpannableStringSetter = LinkSpannableStringSetter(textTermCondition, textString, this)

        linkSpannableStringSetter.set(LinkSpannableText(42, 47, "查看用户协议"))
        linkSpannableStringSetter.set(LinkSpannableText(48, 54, "查看隐私政策"))
        textTermCondition.text = linkSpannableStringSetter.text

        btnAgree.setOnClickListener {
            val edit = sharedPreferences.edit()
            edit.putBoolean("isAgreeItem", true)
            edit.apply()
            val intent = Intent(this, PageActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnDisagree.setOnClickListener {
            Log.d(TAG, "btnDisagree.setOnClickListener")
            exitApp()
        }

        Log.d(TAG, "Is agree term and condition: $isAgreeTermCondition")
        if (isAgreeTermCondition) {
            val intent = Intent(this, PageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun exitApp() {
        finishAffinity();
        android.os.Process.killProcess(android.os.Process.myPid());
        exitProcess(0);
    }

    private class LinkSpannableText(val posStart: Int, val posEnd: Int, val toastString: String)
    private class LinkSpannableStringSetter(textView: TextView, content: String, val context: Context) {
        private val spannableString = SpannableString(content)
        init {
            textView.movementMethod = LinkMovementMethod.getInstance()
        }

        val text: SpannableString
            get() = spannableString
        private fun setClickableSpan(linkSpannableString: LinkSpannableText) {
            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(context, linkSpannableString.toastString, Toast.LENGTH_SHORT).show()
                }
            }, linkSpannableString.posStart, linkSpannableString.posEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        private fun setForegroundColorSpan(linkSpannableText: LinkSpannableText) {
            spannableString.setSpan(
                ForegroundColorSpan(context.resources.getColor(R.color.blue)),
                linkSpannableText.posStart, linkSpannableText.posEnd,
                Spannable.SPAN_COMPOSING
            )
        }
        private fun setBackgroundColorSpan(linkSpannableText: LinkSpannableText) {
            spannableString.setSpan(
                BackgroundColorSpan(context.resources.getColor(R.color.white)),
                linkSpannableText.posStart, linkSpannableText.posEnd,
                Spannable.SPAN_COMPOSING
            )
        }
        fun set(linkSpannableText: LinkSpannableText) {
            setClickableSpan(linkSpannableText)
            setForegroundColorSpan(linkSpannableText)
            setBackgroundColorSpan(linkSpannableText)
        }
    }
}