package com.rdb.style.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.rdb.style.helper.ViewPagerHelper;

public class ViewPager extends androidx.viewpager.widget.ViewPager implements StyleView<ViewPagerHelper> {

    private ViewPagerHelper viewPagerHelper;

    public ViewPager(Context context) {
        super(context);
        viewPagerHelper = onCreateHelper();
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewPagerHelper = onCreateHelper();
    }

    @Override
    public ViewPagerHelper onCreateHelper() {
        return new ViewPagerHelper(this);
    }
}
