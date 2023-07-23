package com.rdb.style.widget;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import com.rdb.style.helper.HorizontalScrollViewHelper;

public class HorizontalScrollView extends android.widget.HorizontalScrollView implements StyleView<HorizontalScrollViewHelper> {

    private OnScrollListener scrollListener;
    private HorizontalScrollViewHelper scrollViewHelper;

    public HorizontalScrollView(Context context) {
        super(context);
        initTheme();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTheme();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTheme();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTheme();
    }

    private void initTheme() {
        scrollViewHelper = onCreateHelper();
    }

    @Override
    public HorizontalScrollViewHelper onCreateHelper() {
        return new HorizontalScrollViewHelper(this);
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
        void onScrollChanged(HorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
