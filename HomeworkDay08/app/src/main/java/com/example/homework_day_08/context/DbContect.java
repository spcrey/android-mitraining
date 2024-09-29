package com.example.homework_day_08.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContect extends SQLiteOpenHelper {

    private static final int VERSION=1;

    private static final String DBNAME="Users.db";
    public DbContect(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table pwd_tb (pwd varchar(20) primary key)"
        );
        db.execSQL(
                "create table user_tb(_id integer primary key autoincrement, money decimal," +
                " time varchar(10),type varchar(10),handler varchar(100),mark varchar(200))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists pwd_tb");
        db.execSQL("drop table if exists user_tb");
        onCreate(db);
    }
}
