package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.rdb.style.Style;
import com.rdb.style.helper.TextViewHelper;

public class Button extends AppCompatButton implements StyleView<TextViewHelper> {

    private TextViewHelper textViewHelper;

    public Button(Context context) {
        super(context);
        textViewHelper = onCreateHelper();
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        textViewHelper = onCreateHelper();
    }

    public Button(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textViewHelper = onCreateHelper();
    }

    @Override
    public TextViewHelper onCreateHelper() {
        return new TextViewHelper(this);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (textViewHelper != null) {
            super.setTextSize(unit, size * Style.getScaleValue());
            textViewHelper.onTextSizeSet();
        } else {
            super.setTextSize(unit, size);
        }
    }
}
