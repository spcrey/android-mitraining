package com.example.homework_day_06.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.homework_day_06.ItemPhoneImageActivity;
import com.example.homework_day_06.ItemPhoneTextActivity;
import com.example.homework_day_06.R;
import com.example.homework_day_06.adapter.PhoneAdapter;
import com.example.homework_day_06.event.BackImageEvent;
import com.example.homework_day_06.event.ImageEvent;
import com.example.homework_day_06.event.LoveStatusEvent;
import com.example.homework_day_06.event.TextEvent;
import com.example.homework_day_06.item.PhoneItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private static final String TAG = "HomeFragment";

    private RecyclerView recyclerView;
    private PhoneAdapter phoneAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<PhoneItem> data;
    private int max_num = 12;

    private Button button;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        data = new ArrayList<>();
        data.add(new PhoneItem("MI 7", R.drawable.ic_launcher_foreground));
        data.add(new PhoneItem("MI 8", R.drawable.orange));
        data.add(new PhoneItem("MI 9", R.drawable.ic_launcher_foreground));
        data.add(new PhoneItem("MI 10", R.drawable.ic_launcher_foreground));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        phoneAdapter = new PhoneAdapter(data, getContext());
        phoneAdapter.getLoadMoreModule().setAutoLoadMore(true);
        phoneAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);
        phoneAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        button = view.findViewById(R.id.item_love_status);

        phoneAdapter.addChildClickViewIds(R.id.item_text);
        phoneAdapter.addChildClickViewIds(R.id.item_image);
        phoneAdapter.addChildClickViewIds(R.id.item_love_status);
        //item子控件点击事件
        phoneAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.item_image) {
                    last_position = position;
                    EventBus.getDefault().postSticky(new ImageEvent(
                            data.get(position).getImageId(), data.get(position).getLoveStatus(), position));
                    Intent intent = new Intent(getContext(), ItemPhoneImageActivity.class);
                    startActivity(intent);
                }
                else if (view.getId() == R.id.item_text) {
                    last_position = position;
                    EventBus.getDefault().postSticky(new TextEvent(data.get(position).getTitle()));
                    Intent intent = new Intent(getContext(), ItemPhoneTextActivity.class);
                    startActivity(intent);
                }
                else if  (view.getId() == R.id.item_love_status) {
                    data.get(position).inverseLoveStatus();
                    phoneAdapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(phoneAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void loadMore() {
        if(data.size() >= max_num){
            phoneAdapter.getLoadMoreModule().loadMoreEnd();
        }
        else {
            recyclerView.postDelayed(() -> {
                data.add(new PhoneItem("Mi XXX", R.drawable.ic_launcher_foreground));
                phoneAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                phoneAdapter.getLoadMoreModule().loadMoreComplete();
            }, 1000);
        }
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(false);
        if(data.size() < max_num)
            data.add(0, new PhoneItem("Mi 000", R.drawable.ic_launcher_foreground));
        phoneAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private int last_position;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoveStatusEvent loveStatusEvent){
        BaseViewHolder viewHolder = (BaseViewHolder) recyclerView.findViewHolderForAdapterPosition(last_position);
        assert viewHolder != null;
        button = viewHolder.getView(R.id.item_love_status);
        data.get(last_position).inverseLoveStatus();
        if(data.get(last_position).getLoveStatus()) {
            button.setBackgroundColor(getResources().getColor(R.color.coral));
        }
        else {
            button.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BackImageEvent backImageEvent){
        data.get(backImageEvent.position).setLoveStatus(backImageEvent.loveStatus);
        phoneAdapter.notifyDataSetChanged();
    }

}