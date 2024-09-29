package com.example.homework_day_09.builder;

import android.app.Activity;
import android.widget.EditText;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.homework_day_09.HomeworkActivity;
import com.example.homework_day_09.R;
import com.example.homework_day_09.adapter.QuickGameAdapter;

public class SwipeRefreshLayoutBuilder {
    public static SwipeRefreshLayout build(HomeworkActivity activity, QuickGameAdapter adapter, EditText searchEditText) {
        SwipeRefreshLayout swipeRefreshLayout = activity.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activity.refreshData();
            }
        });
        return swipeRefreshLayout;
    }
}
