package com.rdb.style.helper;

import android.view.View;
import android.webkit.WebView;

import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.drawable.ThumbDrawable;

public class WebViewHelper extends ViewHelper<WebView> implements Style.OnThemeChangeListener {

    private ThumbDrawable thumbDrawable;

    public WebViewHelper(WebView view) {
        super(view);
        thumbDrawable = new ThumbDrawable(Style.getCurTheme().getColor());
        ViewHelper.updateScrollBarDrawable(view, thumbDrawable, null, true);
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
        thumbDrawable.setColor(theme.getColor());
        ViewHelper.updateEdgeEffectTheme(view, theme);
        return null;
    }
}
