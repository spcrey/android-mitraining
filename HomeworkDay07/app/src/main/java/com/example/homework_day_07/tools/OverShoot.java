package com.example.homework_day_07.tools;

public class OverShoot {
    private final float time;
    private final float amp;
    public OverShoot(float time, float amp) {
        this.time = time;
        this.amp = amp;
    }
    public float getTime() {
        return time;
    }
    public float getAmp() {
        return amp;
    }
}
