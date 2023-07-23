package com.rdb.style.helper;

import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.core.widget.TintableCompoundButton;

import com.rdb.style.State;
import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;

public class CompoundButtonHelper extends TextViewHelper<CompoundButton> {

    private VariableColor buttonStateColor;

    public CompoundButtonHelper(CompoundButton view) {
        super(view);
        if (view instanceof TintableCompoundButton) {
            buttonStateColor = new VariableColor(State.STATES_DISABLE_CHECKED_OTHER) {
                @Override
                protected void updateColor(int[] colors) {
                    colors[0] = Style.getAlphaDisableColor();
                    colors[1] = Style.getCurTheme().getColor();
                    colors[2] = Style.getCurTheme().getAlphaColor(128);
                }
            };
            Style.registerVariableColor(buttonStateColor);
            ((TintableCompoundButton) view).setSupportButtonTintList(buttonStateColor);
        }
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        if (buttonStateColor != null) {
            ((TintableCompoundButton) view).setSupportButtonTintList(null);
            ((TintableCompoundButton) view).setSupportButtonTintList(buttonStateColor);
        }
        return super.onThemeChanged(theme);
    }

    @Override
    public Style.OnDarkUpdateListener onDarkChanged(boolean dark) {
        if (buttonStateColor != null) {
            ((TintableCompoundButton) view).setSupportButtonTintList(null);
            ((TintableCompoundButton) view).setSupportButtonTintList(buttonStateColor);
        }
        return super.onDarkChanged(dark);
    }
}
