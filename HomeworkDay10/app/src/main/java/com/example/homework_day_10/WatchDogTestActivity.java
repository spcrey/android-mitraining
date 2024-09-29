package com.example.homework_day_10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.homework_day_10.tools.CustomAnrWatchDag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WatchDogTestActivity extends AppCompatActivity {

    private static final String TAG = "WatchDogTestActivity";

    CustomAnrWatchDag customAnrWatchDag = new CustomAnrWatchDag(5, 1000);

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customAnrWatchDag.start();

        setContentView(R.layout.activity_watch_dog_test);
        TextView textView = findViewById(R.id.delayAfter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("等待8s");
                        textView.setText("等待8s");
                        textView.setText("等待8s");
                        Log.d(TAG, "等待8s");
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        textView.setText("已经结束啦");
                        Log.d(TAG, "已经结束啦");
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customAnrWatchDag.stop();
        handler.removeCallbacksAndMessages(null);
    }
}