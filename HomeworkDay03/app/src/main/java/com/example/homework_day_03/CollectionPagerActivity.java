package com.example.homework_day_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.homework_day_03.R;
import com.example.homework_day_03.adapter.CollectionPagerAdapter;
import com.example.homework_day_03.fragment.DynamicFragment;

public class CollectionPagerActivity extends AppCompatActivity {

    private static final String TAG = "CollectionPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_pager);
        CollectionPagerAdapter collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(collectionPagerAdapter);
    }
}