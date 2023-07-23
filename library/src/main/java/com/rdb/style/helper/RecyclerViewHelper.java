package com.rdb.style.helper;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.drawable.ThumbDrawable;

public class RecyclerViewHelper extends ViewHelper<RecyclerView> implements Style.OnThemeChangeListener {

    private ThumbDrawable thumbDrawable1;
    private ThumbDrawable thumbDrawable2;

    public RecyclerViewHelper(RecyclerView view) {
        super(view);
        thumbDrawable1 = new ThumbDrawable(Style.getCurTheme().getColor());
        thumbDrawable2 = new ThumbDrawable(Style.getCurTheme().getColor());
        ViewHelper.updateScrollBarDrawable(view, thumbDrawable1, null, true);
        ViewHelper.updateScrollBarDrawable(view, thumbDrawable2, null, false);
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
        thumbDrawable1.setColor(theme.getColor());
        thumbDrawable2.setColor(theme.getColor());
        ViewHelper.updateEdgeEffectTheme(view, theme);
        return null;
    }
}
