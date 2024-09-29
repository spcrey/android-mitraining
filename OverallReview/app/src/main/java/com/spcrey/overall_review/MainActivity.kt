package com.spcrey.overall_review

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.spcrey.overall_review.fragment.TestFragment

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val btnToTestActivity by lazy {
        findViewById<Button>(R.id.btn_to_test_activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(
                R.id.fragment_container_view,
                TestFragment::class.java,
                null,
                "TAG_FRAGMENT"
            )
            .commit()

        btnToTestActivity.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }
}
