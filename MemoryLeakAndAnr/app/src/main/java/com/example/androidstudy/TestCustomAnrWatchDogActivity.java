package com.example.androidstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class TestCustomAnrWatchDogActivity extends AppCompatActivity {

    private static final String TAG = "WatchDogTestActivity";

    CustomAnrWatchDog anrWatchDog = new CustomAnrWatchDog(5, 1000);

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_anr_watch_dog);
        anrWatchDog.start();

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
        anrWatchDog.stop();
    }
}