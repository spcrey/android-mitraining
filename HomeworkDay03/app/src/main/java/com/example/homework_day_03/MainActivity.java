package com.example.homework_day_03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private void bind_button() {
        Button button;

        button = findViewById(R.id.btn_to_multi_fragment_activity);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MultiFragmentActivity.class);
            startActivity(intent);
        });

        button = findViewById(R.id.btn_to_interface_top_bottom_activity);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InterfaceTopBottomActivity.class);
            startActivity(intent);
        });

        button = findViewById(R.id.btn_to_public_top_bottom_activity);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PublicTopBottomActivity.class);
            startActivity(intent);
        });

        button = findViewById(R.id.btn_to_collection_pager_activity);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CollectionPagerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        bind_button();
    }
}
