package com.rdb.style.helper;

import android.util.AttributeSet;
import android.widget.EditText;

import androidx.core.view.TintableBackgroundView;

import com.rdb.style.State;
import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;

;

public class EditTextHelper extends TextViewHelper<EditText> {

    private VariableColor backgroundStateColor;

    public EditTextHelper(EditText view) {
        super(view);
        if (view instanceof TintableBackgroundView) {
            backgroundStateColor = new VariableColor(State.STATES_DISABLE_FOCUSED_OTHER) {
                @Override
                protected void updateColor(int[] colors) {
                    colors[0] = Style.getDisableColor();
                    colors[1] = Style.getCurTheme().getColor();
                    colors[2] = Style.getCurTheme().getAlphaColor(128);
                }
            };
            Style.registerVariableColor(backgroundStateColor);
            ((TintableBackgroundView) view).setSupportBackgroundTintList(backgroundStateColor);
        }
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        if (backgroundStateColor != null) {
            ((TintableBackgroundView) view).setSupportBackgroundTintList(null);
            ((TintableBackgroundView) view).setSupportBackgroundTintList(backgroundStateColor);
        }
        return super.onThemeChanged(theme);
    }

    @Override
    public Style.OnDarkUpdateListener onDarkChanged(boolean dark) {
        if (backgroundStateColor != null) {
            ((TintableBackgroundView) view).setSupportBackgroundTintList(null);
            ((TintableBackgroundView) view).setSupportBackgroundTintList(backgroundStateColor);
        }
        return super.onDarkChanged(dark);
    }
}
