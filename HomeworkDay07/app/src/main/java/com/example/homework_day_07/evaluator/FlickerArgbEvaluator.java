package com.example.homework_day_07.evaluator;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;

public class FlickerArgbEvaluator implements TypeEvaluator {
    private float flickerFrequency;
    private float flickerAmount;

    public FlickerArgbEvaluator(float flickerFrequency, float flickerAmount) {
        this.flickerFrequency = flickerFrequency;
        this.flickerAmount = flickerAmount;
    }


    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // Add flicker effect if applicable
        if (fraction < 1.0f) {
            int startInt = (Integer) startValue;
            // Extract ARGB values from start and end colors
            int startA = (startInt >> 24) & 0xff;
            int startR = (startInt >> 16) & 0xff;
            int startG = (startInt >>  8) & 0xff;
            int startB =  startInt        & 0xff;

            int endInt = (Integer) endValue;
            int endA = (endInt >> 24) & 0xff;
            int endR = (endInt >> 16) & 0xff;
            int endG = (endInt >>  8) & 0xff;
            int endB =  endInt        & 0xff;

            // Compute the interpolated color in linear space
            int a, r, g, b;
            a = startA + (int) (fraction * (endA - startA));
            r = startR + (int) (fraction * (endR - startR));
            g = startG + (int) (fraction * (endG - startG));
            b = startB + (int) (fraction * (endB - startB));
            float flicker = (float) Math.sin(fraction * flickerFrequency) * flickerAmount;
            r += (int) flicker;
            g += (int) flicker;
            b += (int) flicker;

            // Clamp values to [0, 255] range
            r = Math.max(0, Math.min(r, 255));
            g = Math.max(0, Math.min(g, 255));
            b = Math.max(0, Math.min(b, 255));
            return (a << 24) | (r << 16) | (g << 8) | b;
        } else {
            return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
        }
    }
}
