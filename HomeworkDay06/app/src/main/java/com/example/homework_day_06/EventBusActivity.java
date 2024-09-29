package com.example.homework_day_06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework_day_06.event.IndexAddEvent;
import com.example.homework_day_06.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {

    private static final String TAG = "EventBusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        EventBus.getDefault().register(this);
        Button button;
        button = findViewById(R.id.to_change_main_activity_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("changed text"));
            }
        });
        button = findViewById(R.id.to_reset_main_activity_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("no text"));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onMessageEvent(IndexAddEvent event){
        int indexNum = event.getIndexNum();
        Log.d(TAG, "[BACKGROUND]" + indexNum);
        runOnUiThread(() -> {
            Log.d(TAG, "[MAIN]" + indexNum);
            TextView textView = findViewById(R.id.index_num);
            textView.setText("event bus: " + indexNum);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}