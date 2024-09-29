package com.xiaomi.weibo_hanpeng

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xiaomi.weibo_hanpeng.fragment.SingleImageFragment
import java.io.File
import java.io.IOException
import java.util.UUID

class ImageDisplayActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ImageDisplayActivity1212"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val currentPage = intent.getIntExtra("currentPage", 0)
        val textIndex = findViewById<TextView>(R.id.text_index)
        val images = intent.getStringArrayListExtra("images")
        val avatar = intent.getStringExtra("avatar")
        val username = intent.getStringExtra("username")
        val avatarView = findViewById<ImageView>(R.id.img_avatar)
        val usernameView = findViewById<TextView>(R.id.text_username)
        usernameView.text = username

        Glide.with(this).load(avatar).transform(CircleCrop()).into(avatarView)


        val text = "${currentPage+1}/${images!!.size}"
        textIndex.text = text
        val btnDownLoad = findViewById<TextView>(R.id.btn_download)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val imagesPagerAdapter = ImagesPagerAdapter(supportFragmentManager, images!!.toList())

        viewPager.adapter = imagesPagerAdapter
        viewPager.currentItem = currentPage

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val text = "${position+1}/${images.size}"
                textIndex.text = text
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        btnDownLoad.setOnClickListener {
            Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show()
            val flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (flag == PackageManager.PERMISSION_GRANTED) {
                Glide.with(this)
                    .asFile()
                    .load(images[viewPager.currentItem]).into(object : CustomTarget<File>() {
                        override fun onResourceReady(
                            resource: File,
                            transition: Transition<in File>?
                        ) {
                            val externalStorageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            val targetDirectory = File(externalStorageDirectory, "image")
                            Log.d(TAG, targetDirectory.toString())
                            if (!targetDirectory.exists()) {
                                targetDirectory.mkdirs()
                            }
                            try {
                                val fileName = UUID.randomUUID()
                                resource.copyTo(File(targetDirectory, "$fileName.jpg"), overwrite = true)
                                Toast.makeText(
                                    this@ImageDisplayActivity,
                                    "下载已完成:$fileName",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }catch (e: IOException) {
                                e.printStackTrace()
                                Toast.makeText(this@ImageDisplayActivity, "下载失败", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })

            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 200
                )
            }
        }
    }

    class ImagesPagerAdapter(fm: FragmentManager, val images: List<String>) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment: Fragment = SingleImageFragment()
            val args = Bundle()
            args.putString("image", images[position])
            args.putInt("currentPage", position)
            fragment.arguments = args
            return fragment
        }
    }
}