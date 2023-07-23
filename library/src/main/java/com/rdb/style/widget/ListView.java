package com.rdb.style.widget;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;

import androidx.annotation.RequiresApi;

import com.rdb.style.helper.AbsListViewHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DB on 2017/1/17.
 */

public class ListView extends android.widget.ListView implements StyleView<AbsListViewHelper> {

    private AbsListViewHelper absListViewHelper;
    private Set<OnScrollListener> scrollListeners = new HashSet<>();
    private OnScrollListener scrollListenerProxy = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            for (OnScrollListener listener : scrollListeners) {
                listener.onScrollStateChanged(absListView, i);
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            for (OnScrollListener listener : scrollListeners) {
                listener.onScroll(absListView, i, i1, i2);
            }
        }
    };

    public ListView(Context context) {
        super(context);
        initTheme();
    }

    public ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTheme();
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTheme();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTheme();
    }

    private void initTheme() {
        absListViewHelper = onCreateHelper();
        super.setOnScrollListener(scrollListenerProxy);
    }

    @Override
    public AbsListViewHelper onCreateHelper() {
        return new AbsListViewHelper(this);
    }

    @Override
    @Deprecated
    public final void setOnScrollListener(OnScrollListener l) {
        addOnScrollListener(l);
    }

    public void addOnScrollListener(OnScrollListener l) {
        scrollListeners.add(l);
    }

    public void removeOnScrollListener(OnScrollListener l) {
        scrollListeners.remove(l);
    }

}
