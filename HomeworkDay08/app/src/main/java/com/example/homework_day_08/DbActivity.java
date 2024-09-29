package com.example.homework_day_08;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.homework_day_08.context.DbContect;

public class DbActivity extends AppCompatActivity {

    DbContect helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        helper = new DbContect(DbActivity.this);
        SQLiteDatabase db=helper.getWritableDatabase();

    }
}