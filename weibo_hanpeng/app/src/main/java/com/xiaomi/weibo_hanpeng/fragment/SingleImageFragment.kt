package com.xiaomi.weibo_hanpeng.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.xiaomi.weibo_hanpeng.R

class SingleImageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val image = requireArguments().getString("image")
        val currentPage = requireArguments().getInt("currentPage")
        val imageView = view.findViewById<ImageView>(R.id.image)
        val bgUp = view.findViewById<View>(R.id.bg_up)
        val bgDown = view.findViewById<View>(R.id.bg_down)
        bgUp.setOnClickListener {
            activity?.finish()
        }
        bgDown.setOnClickListener {
            activity?.finish()
        }
        imageView.setOnClickListener {
            activity?.finish()
        }
        context?.let {
            Glide.with(it)
                .load(image)
                .into(imageView)
        }
    }
}