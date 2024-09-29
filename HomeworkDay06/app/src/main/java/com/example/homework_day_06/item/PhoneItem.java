package com.example.homework_day_06.item;

public class PhoneItem {
    private String text;
    private  int imageId;

    private boolean loveStatus = false;

    public PhoneItem(String title, int resId) {
        this.text = title;
        this.imageId = resId;
    }

    public void inverseLoveStatus() {
        loveStatus = !loveStatus;
    }

    public boolean getLoveStatus() {
        return loveStatus;
    }

    public void setLoveStatus(boolean loveStatus) {
        this.loveStatus = loveStatus;
    }

    public String getTitle() {
        return text;
    }

    public int getImageId() {
        return imageId;
    }
}
