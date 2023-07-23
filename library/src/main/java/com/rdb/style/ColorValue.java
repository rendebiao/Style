package com.rdb.style;

import android.graphics.Color;

public class ColorValue {

    IntValue alpha;
    IntValue red;
    IntValue green;
    IntValue blue;

    public ColorValue() {
        alpha = new IntValue(0);
        red = new IntValue(0);
        green = new IntValue(0);
        blue = new IntValue(0);
    }

    public ColorValue(int color) {
        alpha = new IntValue(Color.alpha(color));
        red = new IntValue(Color.red(color));
        green = new IntValue(Color.green(color));
        blue = new IntValue(Color.blue(color));
    }

    public void moveTo(int toColor, boolean animateTo) {
        alpha.moveTo(Color.alpha(toColor), animateTo);
        red.moveTo(Color.red(toColor), animateTo);
        green.moveTo(Color.green(toColor), animateTo);
        blue.moveTo(Color.blue(toColor), animateTo);
    }

    public int getColor() {
        return Color.argb(alpha.getCurValue(), red.getCurValue(), green.getCurValue(), blue.getCurValue());
    }

    public int getToColor() {
        return Color.argb(alpha.getToValue(), red.getToValue(), green.getToValue(), blue.getToValue());
    }

    public boolean updateValue(float animatorValue) {
        boolean alphaUpdate = alpha.updateValue(animatorValue);
        boolean redUpdate = red.updateValue(animatorValue);
        boolean greenUpdate = green.updateValue(animatorValue);
        boolean blueUpdate = blue.updateValue(animatorValue);
        return alphaUpdate || redUpdate || greenUpdate || blueUpdate;
    }

    public static class IntValue {

        int curValue;
        int fromValue;
        int toValue;

        public IntValue() {
        }

        public IntValue(int curValue) {
            this.curValue = curValue;
        }

        public void moveTo(int toValue, boolean animateToValue) {
            if (animateToValue) {
                this.toValue = toValue;
                this.fromValue = curValue;
            } else {
                this.fromValue = this.curValue = this.toValue = toValue;
            }
        }

        public int getCurValue() {
            return curValue;
        }

        public int getFromValue() {
            return fromValue;
        }

        public int getToValue() {
            return toValue;
        }

        public boolean updateValue(float animatorValue) {
            if (fromValue != toValue) {
                curValue = (int) (fromValue + (toValue - fromValue) * animatorValue);
                return true;
            }
            return false;
        }
    }
}
