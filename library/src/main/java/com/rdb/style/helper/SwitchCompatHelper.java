package com.rdb.style.helper;

import androidx.appcompat.widget.SwitchCompat;

import com.rdb.style.State;
import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;

public class SwitchCompatHelper extends TextViewHelper<SwitchCompat> {

    private VariableColor thumbStateColor;
    private VariableColor trackStateColor;

    public SwitchCompatHelper(SwitchCompat view) {
        super(view);
        thumbStateColor = new VariableColor(State.STATES_DISABLE_CHECKED_OTHER, true) {
            @Override
            protected void updateColor(int[] colors) {
                colors[0] = Style.getDisableColor();
                colors[1] = Style.getCurTheme().getColor();
                colors[2] = Style.getBackgroundColor();
            }
        };
        Style.registerVariableColor(thumbStateColor);
        view.setThumbTintList(thumbStateColor);
        trackStateColor = new VariableColor(State.STATES_DISABLE_CHECKED_OTHER, true) {
            @Override
            protected void updateColor(int[] colors) {
                colors[0] = Style.getAlphaDisableColor();
                colors[1] = Style.getCurTheme().getAlphaColor(128);
                colors[2] = Style.getAlphaForegroundColor(80);
            }
        };
        Style.registerVariableColor(trackStateColor);
        view.setTrackTintList(trackStateColor);
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        if (thumbStateColor != null) {
            view.setThumbTintList(null);
            view.setThumbTintList(thumbStateColor);
        }
        if (trackStateColor != null) {
            view.setTrackTintList(null);
            view.setTrackTintList(trackStateColor);
        }
        return super.onThemeChanged(theme);
    }

    @Override
    public Style.OnDarkUpdateListener onDarkChanged(boolean dark) {
        if (thumbStateColor != null) {
            view.setThumbTintList(null);
            view.setThumbTintList(thumbStateColor);
        }
        if (trackStateColor != null) {
            view.setTrackTintList(null);
            view.setTrackTintList(trackStateColor);
        }
        return super.onDarkChanged(dark);
    }
}
