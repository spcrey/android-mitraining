package com.example.homework_day_11.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.homework_day_11.ItemPhoneImageActivity
import com.example.homework_day_11.ItemPhoneNameActivity
import com.example.homework_day_11.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : Fragment() {

    private val data: MutableList<PhoneItem> = arrayListOf()

    private val phoneAdapter: PhoneAdapter by lazy {
        PhoneAdapter(data)
    }

    private val maxDataSize: Int = 30

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        data.add(PhoneItem("Mi 10", imageResourceId = R.drawable.image1))
        data.add(PhoneItem("Mi 11", imageResourceId = R.drawable.image2))
        data.add(PhoneItem("Mi 12", imageResourceId = R.drawable.image3))
        data.add(PhoneItem("Mi 13", imageResourceId = R.drawable.image4))
        data.add(PhoneItem("Mi 14", imageResourceId = R.drawable.image5))
        data.add(PhoneItem("Mi 15", imageResourceId = R.drawable.image6))
        data.add(PhoneItem("Mi 16", imageResourceId = R.drawable.image7))
        data.add(PhoneItem("Mi 17", imageResourceId = R.drawable.image8))
        data.add(PhoneItem("Mi 18", imageResourceId = R.drawable.image9))
        data.add(PhoneItem("Mi 19", imageResourceId = R.drawable.image10))

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)

        recyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        phoneAdapter.loadMoreModule.isAutoLoadMore = true
        phoneAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        phoneAdapter.loadMoreModule.setOnLoadMoreListener {
            if (data.size < maxDataSize) {
                recyclerView.postDelayed({
                    for (i in 1..4) {
                        data.add(PhoneItem("Mi XXX", R.drawable.orange))
                    }
                    phoneAdapter.notifyDataSetChanged()
                    phoneAdapter.loadMoreModule.loadMoreComplete()
                }, 500)
            } else {
                phoneAdapter.loadMoreModule.loadMoreEnd()
            }
        }
        recyclerView.adapter = phoneAdapter
        phoneAdapter.addChildClickViewIds(R.id.item_name)
        phoneAdapter.addChildClickViewIds(R.id.item_image)
        phoneAdapter.addChildClickViewIds(R.id.item_love_status)

        swipeRefreshLayout.setOnRefreshListener {
            if (data.size < maxDataSize) {
                recyclerView.postDelayed({
                    for (i in 1..2) {
                        data.add(0, PhoneItem("Mi 0", R.drawable.orange))
                    }
                    swipeRefreshLayout.isRefreshing = false
                    phoneAdapter.notifyDataSetChanged()
                    phoneAdapter.loadMoreModule.loadMoreComplete()
                }, 500)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(loveStatusEvent: LoveStatusEvent) {
        data[loveStatusEvent.layoutPosition].isLove = loveStatusEvent.isLove
        phoneAdapter.notifyDataSetChanged()
    }

    data class LoveStatusEvent(val layoutPosition: Int, val isLove: Boolean)

    class PhoneAdapter(
        data: MutableList<PhoneItem>?
    ): BaseQuickAdapter<PhoneItem, BaseViewHolder>(R.layout.item_phone, data),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: PhoneItem) {
            val textView = holder.getView<TextView>(R.id.item_name)
            val layoutPosition = holder.layoutPosition
            textView.setOnClickListener {

                val intent = Intent(context, ItemPhoneNameActivity::class.java)
                intent.putExtra("layoutPosition", layoutPosition)
                intent.putExtra("text", item.name)
                intent.putExtra("isLove", item.isLove)
                context.startActivity(intent)
            }
            textView.text = item.name
            val imageView = holder.getView<ImageView>(R.id.item_image)
            imageView.setOnClickListener {
                val intent = Intent(
                    context, ItemPhoneImageActivity::class.java
                )
                intent.putExtra("layoutPosition", layoutPosition)
                intent.putExtra("imageResourceId", item.imageResourceId)
                intent.putExtra("isLove", item.isLove)
                context.startActivity(intent)
            }
            Glide.with(imageView.context)
                .load(item.imageResourceId)
                .into(imageView)
            val buttonView = holder.getView<Button>(R.id.item_love_status);
            val color = if (item.isLove) {
                ContextCompat.getColor(context, R.color.coral)
            } else {
                ContextCompat.getColor(context, R.color.gray)
            }
            buttonView.setBackgroundColor(color)

            buttonView.setOnClickListener {
                item.changeLoveStatus()
                notifyDataSetChanged()
            }
        }
    }
    data class PhoneItem(val name: String, val imageResourceId: Int, var isLove: Boolean = false) {
        fun changeLoveStatus() { isLove = !isLove }
    }

    class ItemLoader() {

    }
}