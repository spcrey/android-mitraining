package com.example.homework_day_07.evaluator;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;

public class JumpArgbEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        if (fraction < 1.0f){
            int startInt = (Integer) startValue;
            float startA = ((startInt >> 24) & 0xff) / 255.0f;
            float startR = ((startInt >> 16) & 0xff) / 255.0f;
            float startG = ((startInt >>  8) & 0xff) / 255.0f;
            float startB = ( startInt        & 0xff) / 255.0f;

            int endInt = (Integer) endValue;
            float endA = ((endInt >> 24) & 0xff) / 255.0f;
            float endR = ((endInt >> 16) & 0xff) / 255.0f;
            float endG = ((endInt >>  8) & 0xff) / 255.0f;
            float endB = ( endInt        & 0xff) / 255.0f;
            // Introduce randomness for color jump effect
            float jumpFactor = 0.1f; // Adjust the jump factor as needed
            float randomJumpR = (float) Math.random() * jumpFactor;
            float randomJumpG = (float) Math.random() * jumpFactor;
            float randomJumpB = (float) Math.random() * jumpFactor;

            // compute the interpolated color in linear space
            float a = startA + fraction * (endA - startA);
            float r = startR + fraction * (endR - startR) + randomJumpR;
            float g = startG + fraction * (endG - startG) + randomJumpG;
            float b = startB + fraction * (endB - startB) + randomJumpB;

            // Convert back to sRGB in the [0..255] range
            a = a * 255.0f;
            r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
            g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
            b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

            return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
        }
        else {
            return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
        }
    }
}
