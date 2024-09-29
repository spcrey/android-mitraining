package com.example.homework_day_11

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button = findViewById<Button>(R.id.toJavaActivity);
        button.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
        }
        button = findViewById<Button>(R.id.toKotlinActivity);
        button.setOnClickListener {
            val intent = Intent(this, KotlinActivity::class.java)
            startActivity(intent)
        }
        button = findViewById<Button>(R.id.toHomework);
        button.setOnClickListener {
            val intent = Intent(this, HomeworkActivity::class.java)
            startActivity(intent)
        }
    }
}
