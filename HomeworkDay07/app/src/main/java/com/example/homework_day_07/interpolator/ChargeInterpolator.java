package com.example.homework_day_07.interpolator;

import android.view.animation.BaseInterpolator;

public class ChargeInterpolator extends BaseInterpolator {
    @Override
    public float getInterpolation(float t) {
        return 4 * t * t * t - 3 * t * t;
    }
}
