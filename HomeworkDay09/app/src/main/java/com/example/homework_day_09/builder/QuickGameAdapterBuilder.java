package com.example.homework_day_09.builder;

import android.app.Activity;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.homework_day_09.HomeworkActivity;
import com.example.homework_day_09.adapter.QuickGameAdapter;
import com.example.homework_day_09.item.QuickGameItem;

import java.util.List;

public class QuickGameAdapterBuilder {

    public static QuickGameAdapter build(HomeworkActivity activity, List<QuickGameItem> data) {
        QuickGameAdapter adapter = new QuickGameAdapter(data);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                activity.loadMoreItems();
            }
        });
        return adapter;
    }
}
