package com.rdb.style.helper;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import androidx.core.graphics.drawable.DrawableCompat;

import com.rdb.style.State;
import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;

import java.lang.reflect.Field;

public class CheckedTextViewHelper extends TextViewHelper<CheckedTextView> {

    private Drawable markDrawable;
    private VariableColor buttonStateColor;

    public CheckedTextViewHelper(CheckedTextView view) {
        super(view);
        markDrawable = getCheckMarkDrawable();
        if (markDrawable != null) {
            buttonStateColor = new VariableColor(State.STATES_DISABLE_CHECKED_OTHER) {
                @Override
                protected void updateColor(int[] colors) {
                    colors[0] = Style.getAlphaDisableColor();
                    colors[1] = Style.getCurTheme().getColor();
                    colors[2] = Style.getCurTheme().getAlphaColor(128);
                }
            };
            Style.registerVariableColor(buttonStateColor);
            setCheckMarkTintList(buttonStateColor);
        }
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        if (buttonStateColor != null) {
            setCheckMarkTintList(null);
            setCheckMarkTintList(buttonStateColor);
        }
        return super.onThemeChanged(theme);
    }

    @Override
    public Style.OnDarkUpdateListener onDarkChanged(boolean dark) {
        if (buttonStateColor != null) {
            setCheckMarkTintList(null);
            setCheckMarkTintList(buttonStateColor);
        }
        return super.onDarkChanged(dark);
    }

    private Drawable getCheckMarkDrawable() {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getCheckMarkDrawable();
        } else {
            try {
                Field field = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
                field.setAccessible(true);
                return (Drawable) field.get(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void setCheckMarkTintList(ColorStateList checkMarkTintList) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setCheckMarkTintList(checkMarkTintList);
        } else {
            markDrawable = markDrawable.mutate();
            DrawableCompat.setTintList(markDrawable, checkMarkTintList);
            if (markDrawable.isStateful()) {
                markDrawable.setState(view.getDrawableState());
            }
        }
    }
}
