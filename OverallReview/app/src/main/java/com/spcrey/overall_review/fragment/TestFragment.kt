package com.spcrey.overall_review.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.spcrey.overall_review.R
import com.spcrey.overall_review.ServiceActivity

class TestFragment : Fragment() {
    companion object {
        private const val TAG = "TestFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnTest = view.findViewById<Button>(R.id.btn_test)
        btnTest.setOnClickListener {
            Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show()
        }
        val btnToServiceActivity = view.findViewById<Button>(R.id.btn_to_service_activity)
        btnToServiceActivity.setOnClickListener {
            val intent = Intent(context, ServiceActivity::class.java)
            startActivity(intent)
        }
    }
}