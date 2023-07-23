package com.rdb.style;

import android.graphics.Color;
import android.util.SparseIntArray;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;


public class StyleColor {

    private SparseIntArray backgroundColor = new SparseIntArray();
    private SparseIntArray backgroundPressedColor = new SparseIntArray();
    private SparseIntArray foregroundColor = new SparseIntArray();
    private SparseIntArray disableColor = new SparseIntArray();

    public StyleColor(@ColorInt int backgroundColor, @ColorInt int backgroundPressedColor, @ColorInt int foregroundColor, @ColorInt int disableColor) {
        this.backgroundColor.put(255, getAlphaColor(backgroundColor, 255));
        this.backgroundPressedColor.put(255, getAlphaColor(backgroundPressedColor, 255));
        this.foregroundColor.put(255, getAlphaColor(foregroundColor, 255));
        this.disableColor.put(255, getAlphaColor(disableColor, 255));
    }

    private int getAlphaColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    private int getAlphaColor(SparseIntArray colorArray, @IntRange(from = 0, to = 255) int alpha) {
        int alphaColor = colorArray.get(alpha, Integer.MIN_VALUE);
        if (alphaColor == Integer.MIN_VALUE) {
            int color = colorArray.get(255);
            alphaColor = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
            colorArray.put(alpha, alphaColor);
        }
        return alphaColor;
    }

    public int getBackgroundColor() {
        return getAlphaBackgroundColor(255);
    }

    public int getAlphaBackgroundColor(@IntRange(from = 0, to = 255) int alpha) {
        return getAlphaColor(backgroundColor, alpha);
    }

    public int getBackgroundPressedColor() {
        return getAlphaBackgroundPressedColor(255);
    }

    public int getAlphaBackgroundPressedColor(@IntRange(from = 0, to = 255) int alpha) {
        return getAlphaColor(backgroundPressedColor, alpha);
    }

    public int getForegroundColor() {
        return getAlphaForegroundColor(255);
    }

    public int getAlphaForegroundColor(@IntRange(from = 0, to = 255) int alpha) {
        return getAlphaColor(foregroundColor, alpha);
    }

    public int getDisableColor() {
        return getAlphaDisableColor(255);
    }

    public int getAlphaDisableColor(@IntRange(from = 0, to = 255) int alpha) {
        return getAlphaColor(disableColor, alpha);
    }
}
