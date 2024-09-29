package com.example.homework_day_10;

import androidx.annotation.NonNull;

public class CommonData<T> {
    public int code;
    public String msg;
    public T data;

    @NonNull
    @Override
    public String toString() {
        return "CommonData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
