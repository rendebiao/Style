package com.rdb.style;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.util.SparseIntArray;

public class Theme {

    private int color;
    private int styleId;
    private Bundle bundle;
    private ColorMatrix colorMatrix;
    private SparseIntArray alphaColors = new SparseIntArray();

    public Theme(int color, int styleId, Bundle bundle) {
        this.color = color;
        this.styleId = styleId;
        this.bundle = bundle;
        colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, Color.red(color) * 1f / 255,
                0, 0, 0, 0, Color.green(color) * 1f / 255,
                0, 0, 0, 0, Color.blue(color) * 1f / 255,
                0, 0, 0, 1, 0,
        });
    }

    public int getColor() {
        return color;
    }

    public int getStyleId() {
        return styleId;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public int getAlphaColor() {
        return getAlphaColor(144);
    }

    public int getAlphaColor(int alpha) {
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 255) {
            alpha = 255;
        }
        int alphaColor = alphaColors.get(alpha);
        if (alphaColor == 0) {
            alphaColor = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
            alphaColors.put(alpha, alphaColor);
        }
        return alphaColor;
    }

    public ColorMatrix getColorMatrix() {
        return colorMatrix;
    }
}
