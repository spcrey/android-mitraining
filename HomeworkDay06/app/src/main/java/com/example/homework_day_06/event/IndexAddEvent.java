package com.example.homework_day_06.event;

public class IndexAddEvent {
    private int index = 0;
    public void add() {
        index += 1;
    }
    public int getIndexNum() {
        return index;
    }
}
