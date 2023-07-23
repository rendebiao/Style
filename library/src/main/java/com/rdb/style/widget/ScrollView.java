package com.rdb.style.widget;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import com.rdb.style.helper.ScrollViewHelper;

public class ScrollView extends android.widget.ScrollView implements StyleView<ScrollViewHelper> {

    private OnScrollListener scrollListener;
    private ScrollViewHelper scrollViewHelper;

    public ScrollView(Context context) {
        super(context);
        initTheme();
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTheme();
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTheme();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTheme();
    }

    private void initTheme() {
        scrollViewHelper = onCreateHelper();
    }

    @Override
    public ScrollViewHelper onCreateHelper() {
        return new ScrollViewHelper(this);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollListener != null) {
            scrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface OnScrollListener {
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
