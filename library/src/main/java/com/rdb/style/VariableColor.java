package com.rdb.style;

import android.content.res.ColorStateList;
import android.util.StateSet;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class VariableColor extends ColorStateList {

    private boolean instant;
    private int[] colors;
    private int[][] states;

    public VariableColor(int[][] states) {
        this(states, false);
    }

    public VariableColor(int[][] states, boolean instant) {
        this(states, new int[states.length], instant);
    }

    private VariableColor(int[][] states, int[] colors, boolean instant) {
        super(states, colors);
        this.colors = colors;
        this.states = states;
        this.instant = instant;
        notifyChanged();
    }

    public final void notifyChanged() {
        try {
            Field colorsField = ColorStateList.class.getDeclaredField("mColors");
            Method changeMethod = ColorStateList.class.getDeclaredMethod("onColorsChanged");
            colorsField.setAccessible(true);
            changeMethod.setAccessible(true);
            updateColor(colors);
            colorsField.set(this, colors);
            changeMethod.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
            instant = true;
        }
    }

    @Override
    public int getColorForState(@Nullable int[] stateSet, int defaultColor) {
        if (instant) {
            updateColor(colors);
            final int setLength = states.length;
            for (int i = 0; i < setLength; i++) {
                final int[] stateSpec = states[i];
                if (StateSet.stateSetMatches(stateSpec, stateSet)) {
                    return colors[i];
                }
            }
            return defaultColor;
        } else {
            return super.getColorForState(stateSet, defaultColor);
        }
    }

    protected abstract void updateColor(int[] colors);

    public boolean isInstant() {
        return instant;
    }

    @Override
    public boolean isStateful() {
        return instant ? true : super.isStateful();
    }
}

