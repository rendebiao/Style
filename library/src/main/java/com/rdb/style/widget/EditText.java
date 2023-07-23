package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.rdb.style.Style;
import com.rdb.style.helper.EditTextHelper;

public class EditText extends AppCompatEditText implements StyleView<EditTextHelper> {

    private EditTextHelper editTextHelper;

    public EditText(Context context) {
        super(context);
        editTextHelper = onCreateHelper();
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        editTextHelper = onCreateHelper();
    }

    public EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        editTextHelper = onCreateHelper();
    }

    @Override
    public EditTextHelper onCreateHelper() {
        return new EditTextHelper(this);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (editTextHelper != null) {
            super.setTextSize(unit, size * Style.getScaleValue());
            editTextHelper.onTextSizeSet();
        } else {
            super.setTextSize(unit, size);
        }
    }
}
