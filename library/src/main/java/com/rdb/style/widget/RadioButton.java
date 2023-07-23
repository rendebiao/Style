package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.rdb.style.Style;
import com.rdb.style.helper.CompoundButtonHelper;

public class RadioButton extends AppCompatRadioButton implements StyleView<CompoundButtonHelper> {

    private CompoundButtonHelper compoundButtonHelper;

    public RadioButton(Context context) {
        super(context);
        compoundButtonHelper = onCreateHelper();
    }

    public RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        compoundButtonHelper = onCreateHelper();
    }

    public RadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        compoundButtonHelper = onCreateHelper();
    }

    @Override
    public CompoundButtonHelper onCreateHelper() {
        return new CompoundButtonHelper(this);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (compoundButtonHelper != null) {
            super.setTextSize(unit, size * Style.getScaleValue());
            compoundButtonHelper.onTextSizeSet();
        } else {
            super.setTextSize(unit, size);
        }
    }
}
