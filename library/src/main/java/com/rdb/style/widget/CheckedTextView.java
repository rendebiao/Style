package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckedTextView;

import com.rdb.style.Style;
import com.rdb.style.helper.CheckedTextViewHelper;

public class CheckedTextView extends AppCompatCheckedTextView implements StyleView<CheckedTextViewHelper> {

    private CheckedTextViewHelper checkedTextViewHelper;

    public CheckedTextView(Context context) {
        super(context);
        checkedTextViewHelper = onCreateHelper();
    }

    public CheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkedTextViewHelper = onCreateHelper();
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        checkedTextViewHelper = onCreateHelper();
    }

    @Override
    public CheckedTextViewHelper onCreateHelper() {
        return new CheckedTextViewHelper(this);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (checkedTextViewHelper != null) {
            super.setTextSize(unit, size * Style.getScaleValue());
            checkedTextViewHelper.onTextSizeSet();
        } else {
            super.setTextSize(unit, size);
        }
    }
}
