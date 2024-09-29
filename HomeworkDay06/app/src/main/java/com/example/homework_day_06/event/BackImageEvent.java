package com.example.homework_day_06.event;

public class BackImageEvent {

    public int position;
    public boolean loveStatus;
    public BackImageEvent(boolean loveStatus, int position) {
        this.loveStatus = loveStatus;
        this.position = position;
    }
}
