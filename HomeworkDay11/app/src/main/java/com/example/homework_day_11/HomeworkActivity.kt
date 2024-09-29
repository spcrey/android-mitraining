package com.example.homework_day_11

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_day_11.fragment.HomeFragment
import com.example.homework_day_11.fragment.MineFragment

class HomeworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(
                R.id.pageFragmentContainerView,
                HomeFragment::class.java,
                null,
                "BUTTON_FRAGMENT_TAG"
            )
            .commit()

        val buttonToHome = findViewById<Button>(R.id.toPageFragmentHome)
        val buttonToMine = findViewById<Button>(R.id.toPageFragmentMine)
        buttonToHome.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    R.id.pageFragmentContainerView,
                    HomeFragment::class.java,
                    null,
                    "BUTTON_FRAGMENT_TAG"
                )
                .commit()
            buttonToHome.setBackgroundColor(resources.getColor(R.color.coral))
            buttonToMine.setBackgroundColor(resources.getColor(R.color.gray))
        }
        buttonToMine.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    R.id.pageFragmentContainerView,
                    MineFragment::class.java,
                    null,
                    "BUTTON_FRAGMENT_TAG"
                )
                .commit()
            buttonToHome.setBackgroundColor(resources.getColor(R.color.gray))
            buttonToMine.setBackgroundColor(resources.getColor(R.color.coral))
        }
    }
}