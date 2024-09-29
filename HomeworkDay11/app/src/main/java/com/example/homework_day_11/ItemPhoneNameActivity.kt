package com.example.homework_day_11

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_day_11.fragment.HomeFragment
import org.greenrobot.eventbus.EventBus

class ItemPhoneNameActivity : AppCompatActivity() {
    private val layoutPosition by lazy {
        intent.getIntExtra("layoutPosition", -1)
    }

    private var isLove = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_phone_name)

        val text = intent.getStringExtra("text")
        val textView = findViewById<TextView>(R.id.item_name)
        textView.text = text
        isLove = intent.getBooleanExtra("isLove", false)
        val button = findViewById<TextView>(R.id.toChangeLoveStatus)
        val color = if (isLove) {
            resources.getColor(R.color.coral)
        } else {
            resources.getColor(R.color.gray)
        }
        button.setBackgroundColor(color)
        button.setOnClickListener {
            isLove = !isLove;
            val color1 = if (isLove) {
                resources.getColor(R.color.coral)
            } else {
                resources.getColor(R.color.gray)
            }
            button.setBackgroundColor(color1)
            EventBus.getDefault().post(HomeFragment.LoveStatusEvent(layoutPosition, isLove))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}