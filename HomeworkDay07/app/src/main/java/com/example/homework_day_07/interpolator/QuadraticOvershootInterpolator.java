package com.example.homework_day_07.interpolator;

import android.view.animation.BaseInterpolator;

import com.example.homework_day_07.tools.OverShoot;

public class QuadraticOvershootInterpolator extends BaseInterpolator  {
    private final float overshootTime;
    private final float overshootAmp;

    public QuadraticOvershootInterpolator(float overshootTime, float overshootAmp) {
        this.overshootTime = overshootTime;
        this.overshootAmp = overshootAmp;
    }

    @Override
    public float getInterpolation(float t) {
        if (t < overshootTime) {
            return t / overshootTime;
        } else {
            return (float) (
                    Math.sin(2 * Math.PI / (1 - overshootTime) * (t - overshootTime)) * overshootAmp + 1);
        }
    }
}
