package com.example.homework_day_06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.homework_day_06.event.BackImageEvent;
import com.example.homework_day_06.event.ImageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ItemPhoneImageActivity extends AppCompatActivity {

    private static final String TAG = "ItemPhoneImageActivity";

    private int position;

    private boolean loveStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_image);
        EventBus.getDefault().register(this);

        Button button = findViewById(R.id.item_love_status);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loveStatus = !loveStatus;
                EventBus.getDefault().post(new BackImageEvent(loveStatus, position));
                if(loveStatus) {
                    button.setBackgroundColor(getResources().getColor(R.color.coral));
                }
                else {
                    button.setBackgroundColor(getResources().getColor(R.color.gray));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onMessageEvent(ImageEvent imageEvent){
        position = imageEvent.getPosition();
        runOnUiThread(() -> {
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageResource(imageEvent.getImageId());
            Button button = findViewById(R.id.item_love_status);
            loveStatus = imageEvent.getLoveStatus();
            if(loveStatus) {
                button.setBackgroundColor(getResources().getColor(R.color.coral));
            }
            else {
                button.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}