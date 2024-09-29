package com.example.homework_day_06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework_day_06.event.IndexAddEvent;
import com.example.homework_day_06.event.MessageEvent;
import com.example.homework_day_06.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_fragment_container_view, MainFragment.class, null)
                .commit();

        EventBus.getDefault().register(this);

        Button button;
        button = findViewById(R.id.to_event_bus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventBusActivity.class);
                startActivity(intent);
            }
        });
        IndexAddEvent indexAddEvent = new IndexAddEvent();
        button = findViewById(R.id.to_add_event_bus_index);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "indexAddEvent");
                indexAddEvent.add();
                EventBus.getDefault().postSticky(indexAddEvent);
            }
        });
        button = findViewById(R.id.to_homework);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEvent messageEvent){
        String message = messageEvent.getMessage();
        Log.d(TAG, "[BACKGROUND]" + message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runOnUiThread(() -> {
            Log.d(TAG, "[MAIN]" + message);
            TextView textView = findViewById(R.id.tem_text);
            textView.setText(message);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}