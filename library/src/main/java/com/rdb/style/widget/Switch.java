package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.SwitchCompat;

import com.rdb.style.Style;
import com.rdb.style.helper.SwitchCompatHelper;

public class Switch extends SwitchCompat implements StyleView<SwitchCompatHelper> {

    private SwitchCompatHelper switchCompatHelper;

    public Switch(Context context) {
        super(context);
        switchCompatHelper = onCreateHelper();
    }

    public Switch(Context context, AttributeSet attrs) {
        super(context, attrs);
        switchCompatHelper = onCreateHelper();
    }

    public Switch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        switchCompatHelper = onCreateHelper();
    }

    @Override
    public SwitchCompatHelper onCreateHelper() {
        return new SwitchCompatHelper(this);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (switchCompatHelper != null) {
            super.setTextSize(unit, size * Style.getScaleValue());
            switchCompatHelper.onTextSizeSet();
        } else {
            super.setTextSize(unit, size);
        }
    }
}
