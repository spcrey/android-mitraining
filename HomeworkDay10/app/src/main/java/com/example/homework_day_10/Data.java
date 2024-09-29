package com.example.homework_day_10;

import androidx.annotation.NonNull;

public class Data {
    public int code;
    public String msg;

    @NonNull
    @Override
    public String toString() {
        return "Data{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
