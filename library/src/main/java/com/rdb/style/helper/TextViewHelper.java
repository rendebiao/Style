package com.rdb.style.helper;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;

public class TextViewHelper<T extends TextView> extends ViewHelper<T> implements
        Style.OnThemeChangeListener, Style.OnDarkChangeListener, Style.OnScaleChangeListener, Style.OnTypefaceChangeListener {

    private float originalSize;

    public TextViewHelper(T textView) {
        super(textView);
        originalSize = view.getTextSize();
        Typeface typeface = Style.getTypeface();
        if (typeface != null) {
            view.setTypeface(typeface, Typeface.NORMAL);
        }
    }

    private void updateTextColor() {
        ColorStateList colorStateList = view.getTextColors();
        if (colorStateList instanceof VariableColor) {
            VariableColor color = (VariableColor) colorStateList;
            if (color.isInstant()) {
                color.notifyChanged();
                view.setTextColor(colorStateList);
            }
        }
        colorStateList = view.getHintTextColors();
        if (colorStateList instanceof VariableColor) {
            VariableColor color = (VariableColor) colorStateList;
            if (color.isInstant()) {
                color.notifyChanged();
                view.setHintTextColor(colorStateList);
            }
        }
        colorStateList = view.getLinkTextColors();
        if (colorStateList instanceof VariableColor) {
            VariableColor color = (VariableColor) colorStateList;
            if (color.isInstant()) {
                color.notifyChanged();
                view.setLinkTextColor(colorStateList);
            }
        }
    }

    public void onTextSizeSet() {
        originalSize = view.getTextSize() / Style.getScaleValue();
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        super.onViewAttachedToWindow(v);
        Style.registerScaleChangeListener(this, true);
        Style.registerTypefaceChangeListener(this, true);
        Style.registerThemeChangeListener(this, true);
        Style.registerDarkChangeListener(this, true);
        updateSize();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        super.onViewDetachedFromWindow(v);
        Style.registerScaleChangeListener(this, false);
        Style.registerTypefaceChangeListener(this, false);
        Style.registerThemeChangeListener(this, false);
        Style.registerDarkChangeListener(this, false);
    }

    @Override
    public void onScaleChanged(float scaleValue) {
        updateSize();
    }

    public void updateSize() {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, originalSize);
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        updateTextColor();
        return null;
    }

    @Override
    public Style.OnDarkUpdateListener onDarkChanged(boolean dark) {
        updateTextColor();
        return null;
    }

    @Override
    public void onTypefaceChange(Typeface typeface, int style, String path) {
        view.setTypeface(typeface, style);
    }
}
