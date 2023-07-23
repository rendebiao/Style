package com.rdb.style.helper;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.rdb.style.Style;
import com.rdb.style.Theme;

public class ViewPagerHelper extends ViewHelper<ViewPager> implements Style.OnThemeChangeListener {

    public ViewPagerHelper(ViewPager view) {
        super(view);
        ViewHelper.updateEdgeEffectTheme(view, Style.getCurTheme());
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        super.onViewAttachedToWindow(v);
        Style.registerThemeChangeListener(this, true);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        super.onViewDetachedFromWindow(v);
        Style.registerThemeChangeListener(this, false);
    }

    @Override
    public Style.OnThemeUpdateListener onThemeChanged(Theme theme) {
        ViewHelper.updateEdgeEffectTheme(view, theme);
        return null;
    }
}
