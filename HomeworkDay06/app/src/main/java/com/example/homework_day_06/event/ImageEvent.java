package com.example.homework_day_06.event;

public class ImageEvent {

    int imageId;
    private int position;
    private boolean loveStatus;
    public ImageEvent(int imageId, boolean loveStatus, int position) {
        this.imageId = imageId;
        this.loveStatus = loveStatus;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean getLoveStatus() {
        return loveStatus;
    }

    public int getImageId() {
        return imageId;
    }
}
