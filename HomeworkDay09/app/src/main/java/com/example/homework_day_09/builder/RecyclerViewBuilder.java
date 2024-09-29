package com.example.homework_day_09.builder;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.homework_day_09.R;
import com.example.homework_day_09.adapter.QuickGameAdapter;

public class RecyclerViewBuilder {
    public static RecyclerView build(Activity activity, QuickGameAdapter quickGameAdapter) {
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(quickGameAdapter);
        return recyclerView;
    }
}
