package com.example.homework_day_09.tools;

import androidx.annotation.NonNull;

public class CommonData<T> {
    public int code;
    public T data;
    public String msg;

    public CommonData(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @NonNull
    @Override
    public String toString() {
        return "CommonData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
