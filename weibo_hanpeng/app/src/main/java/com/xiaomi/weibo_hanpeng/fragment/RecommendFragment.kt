package com.xiaomi.weibo_hanpeng.fragment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaomi.weibo_hanpeng.ImageDisplayActivity
import com.xiaomi.weibo_hanpeng.LoginActivity
import com.xiaomi.weibo_hanpeng.R
import com.xiaomi.weibo_hanpeng.receiver.DynamicBroadcastReceiver
import com.xiaomi.weibo_hanpeng.tools.CachedData
import com.xiaomi.weibo_hanpeng.tools.ServerApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Collections


class RecommendFragment : Fragment() {
    companion object {
        private const val TAG = "RecommendFragment"
    }

    private val recordAdapter = RecordAdapter(CachedData.homePageRecords)
    private var currentPage = 1
    private var isAllowReload = false
    private val mReceiver by lazy { DynamicBroadcastReceiver() }

    private fun Context.dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
        ).toInt();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    private fun loadData(swipeRefreshLayout: SwipeRefreshLayout, textLoading: TextView,
                         icLoading: ImageView, icLoadFailed: ImageView,
                         textLoadFailed: TextView, btnReload: Button, anim: Animation, token: String?) {
        icLoading.startAnimation(anim)
        swipeRefreshLayout.isEnabled = false
        textLoading.alpha = 1.0f
        icLoading.alpha = 1.0f
        icLoadFailed.alpha = 0.0f
        textLoadFailed.alpha = 0.0f
        btnReload.alpha = 0.0f
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(1000)
                try {
                    currentPage = 1
                    val bearerToken = if(token!=null) "Bearer $token" else null
                    val commonData = ServerApiManager.apiService.homePage(token=bearerToken, current = currentPage).await()
                    if (commonData.code==200) {
                        CachedData.homePageRecords.clear()
                        CachedData.homePageRecords.addAll(commonData.data.records)
                        Collections.shuffle(CachedData.homePageRecords)
                        withContext(Dispatchers.Main) {
                            recordAdapter.notifyDataSetChanged()
                            swipeRefreshLayout.isEnabled = true
                            textLoading.alpha = 0.0f
                            icLoading.alpha = 0.0f
                            icLoading.clearAnimation()
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "request failed: ${e.message}")
                    withContext(Dispatchers.Main) {
                        textLoading.alpha = 0.0f
                        icLoading.alpha = 0.0f
                        icLoading.clearAnimation()
                        icLoadFailed.alpha = 1.0f
                        textLoadFailed.alpha = 1.0f
                        btnReload.alpha = 1.0f
                    }
                    isAllowReload = true
                }
            }
        }
    }


    val sharedPreferences by lazy {context?.getSharedPreferences("user", Context.MODE_PRIVATE)}
    var token: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EventBus.getDefault().register(this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)

        val icLoading = view.findViewById<ImageView>(R.id.ic_loading)
        val textLoading = view.findViewById<TextView>(R.id.text_loading)
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_loading);
        val icLoadFailed = view.findViewById<ImageView>(R.id.ic_load_failed)
        val textLoadFailed = view.findViewById<TextView>(R.id.text_load_failed)
        val btnReload = view.findViewById<Button>(R.id.btn_reload)
        val textNoNetwork = view.findViewById<TextView>(R.id.text_no_network)
        var allowNetworkNotice = false
//        val sharedPreferences = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("token", null)
        Log.d(TAG, token.toString())

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(mReceiver, intentFilter)

        mReceiver.setNetworkListener( object : DynamicBroadcastReceiver.NetworkListener{
            override fun Connected() {
                if (allowNetworkNotice) {
                    Toast.makeText(context, "有网了", Toast.LENGTH_SHORT).show()
                }
                val params = textNoNetwork.layoutParams
                params.height = 0
                textNoNetwork.layoutParams = params
                allowNetworkNotice = true
                recordAdapter.loadMoreModule.loadMoreComplete()
            }

            override fun Disconnected() {
                if (allowNetworkNotice) {
                    Toast.makeText(context, "网无了", Toast.LENGTH_SHORT).show()
                }
                val params = textNoNetwork.layoutParams
                context?.let { nonNullContext -> params.height = nonNullContext.dpToPx(29) }
                textNoNetwork.layoutParams = params
                allowNetworkNotice = true
            }
        })


        btnReload.setOnClickListener {
            if (isAllowReload) {
                loadData(swipeRefreshLayout, textLoading,
                    icLoading, icLoadFailed,
                    textLoadFailed, btnReload, anim, token)
            }
        }

        if (CachedData.homePageRecords.isEmpty()) {
            loadData(swipeRefreshLayout, textLoading, icLoading, icLoadFailed,
                textLoadFailed, btnReload, anim, token)
        }
        else {
            textLoading.alpha = 0.0f
            icLoading.alpha = 0.0f
        }

        recordAdapter.setIcDeleteOnClickListener(object : RecordAdapter.IcDeleteOnClickListener {
            override fun onClick(position: Int) {
                CachedData.homePageRecords.removeAt(position)
                recordAdapter.notifyItemRemoved(position)
            }
        })

        recordAdapter.setImagesOnClickListener(object : RecordAdapter.ImagesOnClickListener {
            override fun onClick(
                images: List<String>,
                currentPage: Int,
                avatar: String,
                username: String
            ) {
                val intent = Intent(context, ImageDisplayActivity::class.java)
                intent.putStringArrayListExtra("images", ArrayList(images))
                intent.putExtra("currentPage", currentPage)
                intent.putExtra("avatar", avatar)
                intent.putExtra("username", username)
                startActivity(intent)
            }

        })

        var canclick = true

        recordAdapter.setIcLikeOnClickListener(object : RecordAdapter.IcLikeOnClickListener{
            override fun onClick(id: Long, position: Int, status: Boolean) {


                Log.d(TAG, status.toString())
                if (token!=null) {
                    val likeFun = if(status) ServerApiManager.apiService::likeUp else ServerApiManager.apiService::likeDown

                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                val likeData = likeFun("Bearer $token", ServerApiManager.IdRequest(id)).await()
                                if (canclick) {
                                    if (status) {
                                        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_to_love)
                                        val icLike = recordAdapter.getViewByPosition(position, R.id.ic_like)
                                        if (icLike != null) {
                                            icLike.startAnimation(animation)
                                            animation.setAnimationListener(object : Animation.AnimationListener {
                                                override fun onAnimationStart(animation: Animation) {
                                                    canclick = false
                                                    println("Animation started")
                                                }

                                                override fun onAnimationEnd(animation: Animation) {
                                                    val data = recordAdapter.getItem(position)
                                                    data.likeFlag = true
                                                    data.likeCount += 1
                                                    recordAdapter.notifyDataSetChanged()
                                                    canclick = true

                                                }

                                                override fun onAnimationRepeat(animation: Animation) {
                                                    // 动画重复时执行的操作（如果动画设置为重复）
                                                    println("Animation repeated")
                                                }
                                            })
                                        }

                                    } else {
                                        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_to_unlove)
                                        val icLike = recordAdapter.getViewByPosition(position, R.id.ic_like)
                                        if (icLike != null) {
                                            icLike.startAnimation(animation)
                                            animation.setAnimationListener(object : Animation.AnimationListener {
                                                override fun onAnimationStart(animation: Animation) {
                                                    canclick = false
                                                    println("Animation started")
                                                }

                                                override fun onAnimationEnd(animation: Animation) {
                                                    val data = recordAdapter.getItem(position)
                                                    data.likeFlag = false
                                                    if (data.likeCount > 0)
                                                        data.likeCount -= 1
                                                    recordAdapter.notifyDataSetChanged()
                                                    canclick = true
                                                }

                                                override fun onAnimationRepeat(animation: Animation) {
                                                    // 动画重复时执行的操作（如果动画设置为重复）
                                                    println("Animation repeated")
                                                }
                                            })
                                        }
                                    }
                                }
                                Log.d(TAG, likeData.toString())
                            } catch (e: Exception) {
                                Log.d(TAG, "request failed: ${e.message}")
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        })

        recyclerView.layoutManager = StaggeredGridLayoutManager(
            1, StaggeredGridLayoutManager.VERTICAL
        )
        recordAdapter.loadMoreModule.isAutoLoadMore = true
        recordAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        recordAdapter.loadMoreModule.setOnLoadMoreListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {

                            val bearerToken = if(token!=null) "Bearer $token" else null
                            val commonData = ServerApiManager.apiService.homePage(token=bearerToken, current = currentPage).await()
                            if (commonData.code==200) {
                                currentPage += 1
                                delay(300)
                                Log.d(TAG, commonData.data.records.toString())
                                if (commonData.data.records.isNotEmpty()) {
                                    CachedData.homePageRecords.addAll(commonData.data.records)
                                    withContext(Dispatchers.Main) {
                                        recordAdapter.notifyDataSetChanged()
                                        swipeRefreshLayout.isRefreshing = false
                                        recordAdapter.loadMoreModule.loadMoreComplete()
                                    }
                                }
                                else {
                                    withContext(Dispatchers.Main) {
                                        recordAdapter.notifyDataSetChanged()
                                        swipeRefreshLayout.isRefreshing = false
                                        recordAdapter.loadMoreModule.loadMoreEnd()
                                        Toast.makeText(context, "无更多内容", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "request failed: ${e.message}")
                            withContext(Dispatchers.Main) {
                                recordAdapter.loadMoreModule.loadMoreEnd()
                                Toast.makeText(context, "暂无网络", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

            }

        }


        recyclerView.adapter = recordAdapter
        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        currentPage = 1
                        val bearerToken = if(token!=null) "Bearer $token" else null
                        val commonData = ServerApiManager.apiService.homePage(token=bearerToken, current = currentPage).await()
                        if (commonData.code==200) {
//                            Log.d(TAG, commonData.data.records.toString())
                            CachedData.homePageRecords.clear()
                            for (record in commonData.data.records) {
                                Log.d(TAG, "id=${record.id}, likeFlag=${record.likeFlag}")
                            }
                            CachedData.homePageRecords.addAll(commonData.data.records)
                            Collections.shuffle(CachedData.homePageRecords)
                            withContext(Dispatchers.Main) {
                                recordAdapter.notifyDataSetChanged()
                                swipeRefreshLayout.isRefreshing = false
                                recordAdapter.loadMoreModule.loadMoreComplete()
                            }
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "request failed: ${e.message}")
                        withContext(Dispatchers.Main) {
                            swipeRefreshLayout.isRefreshing = false
                            Toast.makeText(context, "暂无网络", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    class RecordAdapter(data: MutableList<ServerApiManager.WeiboInfo>?):
        BaseQuickAdapter<ServerApiManager.WeiboInfo, BaseViewHolder>(
            R.layout.item_records, data), LoadMoreModule {
        interface IcDeleteOnClickListener {
            fun onClick(position: Int)
        }
        interface IcLikeOnClickListener {
            fun onClick(id: Long, position: Int, status: Boolean)
        }

        interface ImagesOnClickListener {
            fun onClick(images: List<String>, currentPage: Int, avatar: String, username: String);
        }

        fun setIcDeleteOnClickListener(listener: IcDeleteOnClickListener) {
            icDeleteOnClickListener = listener
        }

        fun setIcLikeOnClickListener(listener: IcLikeOnClickListener) {
            icLikeOnClickListener = listener
        }

        fun setImagesOnClickListener(listener: ImagesOnClickListener) {
            imagesOnClickListener = listener
        }

        fun Context.dpToPx(dp: Int): Int {
            return (dp * resources.displayMetrics.density).toInt()
        }

        private var icDeleteOnClickListener: IcDeleteOnClickListener? = null
        private var icLikeOnClickListener: IcLikeOnClickListener? = null
        private var imagesOnClickListener: ImagesOnClickListener? = null

        override fun convert(holder: BaseViewHolder, item: ServerApiManager.WeiboInfo) {
            val imgAvatar = holder.getView<ImageView>(R.id.img_avatar)
            Glide.with(context).load(item.avatar).transform(CircleCrop()).into(imgAvatar)
            val textUsername = holder.getView<TextView>(R.id.text_username)
            textUsername.text = item.username
            val icDelete = holder.getView<ImageView>(R.id.ic_delete)
            icDelete.setOnClickListener {
                icDeleteOnClickListener?.onClick(holder.layoutPosition)
            }
            val textTitle = holder.getView<TextView>(R.id.text_title)
            textTitle.text = item.title
            val icLike = holder.getView<ImageView>(R.id.ic_like)
            icLike.setImageResource(if (item.likeFlag) R.drawable.ic_like_red else R.drawable.ic_like_black)
            icLike.setOnClickListener{
                icLikeOnClickListener?.onClick(item.id, holder.layoutPosition, !item.likeFlag)
            }

            val icComment = holder.getView<ImageView>(R.id.ic_comment)
            val textComment = holder.getView<TextView>(R.id.text_comment)
            icComment.setOnClickListener {
                Toast.makeText(context, "点击第${holder.layoutPosition}条数据评论按钮", Toast.LENGTH_SHORT).show()
            }
            val textLikeCount = holder.getView<TextView>(R.id.text_like_count)
            textLikeCount.text = if (item.likeCount > 0) item.likeCount.toString() else "点赞"

            val flMediaLayout = holder.getView<FrameLayout>(R.id.fl_media_layout)
            flMediaLayout.removeAllViews()
            if (item.videoUrl != null) {
                val videoView = VideoView(context).apply {
                    id = View.generateViewId()
                }
                val videoUri = Uri.parse(item.videoUrl)
                videoView.setVideoURI(videoUri)
                val layoutParams = ConstraintLayout.LayoutParams(context.dpToPx(320), context.dpToPx(180))
                layoutParams.topMargin = context.dpToPx(16)
                layoutParams.bottomMargin = context.dpToPx(16)
                videoView.layoutParams = layoutParams

//                val mediaController = MediaController(context)
//                mediaController.setAnchorView(videoView)
//                videoView.setMediaController(mediaController)


                videoView.setOnClickListener {
                    if (videoView.isPlaying) {
                        videoView.pause()
                    } else {
                        videoView.background = null
                        videoView.start()
                    }
                }

//                videoView.setOnPreparedListener {
//                    videoView.background = null
//                }


                Glide.with(context)
                    .load(item.poster)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            videoView.background = resource;
                            flMediaLayout.addView(videoView)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
//                            videoView.background = placeholder
                        }
                    })

            } else if (item.images == null) { } else if (item.images.size > 1) {
                val gridLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                gridLayoutParams.topMargin = context.dpToPx(12)
                gridLayoutParams.bottomMargin = context.dpToPx(12)

                val gridLayout = GridLayout(context).apply {
                    layoutParams = gridLayoutParams
                    columnCount = 3
                }
                for (i in 0..<item.images.size) {
                    val imageView = ImageView(context).apply {
                        id = View.generateViewId()
                    }
                    val layoutParams = ConstraintLayout.LayoutParams(context.dpToPx(104), context.dpToPx(104))
                    if (i%3 != 0) {
                        layoutParams.marginStart = context.dpToPx(6)
                    }
                    if (i >= 3) {
                        layoutParams.topMargin = context.dpToPx(6)
                    }
                    imageView.layoutParams = layoutParams

                    imageView.setOnClickListener {
                        imagesOnClickListener?.onClick(item.images, i, item.avatar, item.username)
                    }

                    Glide.with(context)
                        .asBitmap()
                        .load(item.images[i])
                        .transform(CenterCrop(), RoundedCorners(context.dpToPx(4)))
                        .into(imageView)
                    gridLayout.addView(imageView)
                }
                flMediaLayout.addView(gridLayout)

            } else if (item.images.size == 1) {
                val singleImage = ImageView(context).apply {
                    id = View.generateViewId()
                }
                singleImage.setOnClickListener {
                    imagesOnClickListener?.onClick(item.images, 0, item.avatar, item.username)
                }
                val image = item.images[0]
                Glide.with(context)
                    .asBitmap()
                    .load(image)
                    .transform(RoundedCorners(context.dpToPx(4)))
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            val width = resource.width
                            val height = resource.height
                            val (desiredWidth, desiredHeight) = if (width > height) {
                                context.dpToPx(320) to context.dpToPx(186)
                            } else {
                                context.dpToPx(189) to context.dpToPx(336)
                            }
                            singleImage.setImageBitmap(resource)
                            val layoutParams = ConstraintLayout.LayoutParams(desiredWidth, desiredHeight)
                            layoutParams.topMargin = context.dpToPx(12)
                            layoutParams.bottomMargin = context.dpToPx(12)
                            layoutParams.marginStart = context.dpToPx(0)
                            layoutParams.marginEnd = context.dpToPx(0)
                            singleImage.layoutParams = layoutParams
                            flMediaLayout.addView(singleImage)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) { }
                    })
            }
        }
    }

    class LoginEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: LoginEvent) {
        token = sharedPreferences?.getString("token", null)
        recordAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(mReceiver)
        EventBus.getDefault().unregister(this)
    }
}