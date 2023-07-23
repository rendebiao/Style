package com.rdb.style;

public enum Scale {

    SMALLER(0.8f, "极小"), SMALL(0.9f, "较小"), NORMAL(1.0f, "中等"), LARGE(1.1f, "较大"), LARGER(1.2f, "极大");


    private float value;
    private String text;

    Scale(float value, String text) {
        this.value = value;
        this.text = text;
    }

    public static Scale fromValue(float value) {
        for (Scale type : Scale.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return NORMAL;
    }

    public float getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
