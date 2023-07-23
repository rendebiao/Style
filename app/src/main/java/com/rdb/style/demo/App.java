package com.rdb.style.demo;

import android.app.Application;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.rdb.style.Style;
import com.rdb.style.Theme;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int defaultColor = R.color.theme_color_7c80f7;
        TypedArray colorArray = getResources().obtainTypedArray(R.array.colors);
        TypedArray themeArray = getResources().obtainTypedArray(R.array.themes);
        Theme[] themes = new Theme[colorArray.length()];
        for (int i = 0; i < colorArray.length(); i++) {
            int colorId = colorArray.getResourceId(i, defaultColor);
            int themesId = themeArray.getResourceId(i, R.array.theme_7c80f7);
            TypedArray typedArray = getResources().obtainTypedArray(themesId);
            Bundle bundle = new Bundle();
            bundle.putInt("fullscreenStyleId", typedArray.getResourceId(1, 0));
            bundle.putInt("translucentStyleId", typedArray.getResourceId(2, 0));
            bundle.putInt("translucentFullscreenStyleId", typedArray.getResourceId(3, 0));
            themes[i] = new Theme(getResources().getColor(colorId), typedArray.getResourceId(0, 0), bundle);
            typedArray.recycle();
        }
        colorArray.recycle();
        themeArray.recycle();
        Style.initStyle(themes, 1f, getResources().getColor(defaultColor), false, null);
    }
}
