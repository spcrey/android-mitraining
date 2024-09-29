package com.example.homework_day_09.builder;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework_day_09.HomeworkActivity;
import com.example.homework_day_09.R;
import com.example.homework_day_09.adapter.QuickGameAdapter;
import com.example.homework_day_09.item.QuickGameItem;

import java.util.List;

public class SearchButtonBuilder {

    public static Button build(HomeworkActivity activity, List<QuickGameItem> data) {
        Button searchButton = activity.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                activity.setCurrent(1);
                activity.resetLoadMoreFlag();
                activity.requestGetQuickGame();
            }
        });
        return searchButton;
    }
}
