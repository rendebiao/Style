package com.rdb.style.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.annotation.AttrRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.core.widget.EdgeEffectCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rdb.style.Theme;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ViewHelper<T extends View> implements View.OnAttachStateChangeListener {

    protected T view;

    public ViewHelper(T view) {
        this.view = view;
        view.addOnAttachStateChangeListener(this);
    }

    public static void updateEdgeEffectTheme(View view, Theme theme) {
        if (view instanceof ScrollView) {
            updateEdgeEffectTheme(view, ScrollView.class, "mEdgeGlowTop", theme);
            updateEdgeEffectTheme(view, ScrollView.class, "mEdgeGlowBottom", theme);
        } else if (view instanceof HorizontalScrollView) {
            updateEdgeEffectTheme(view, HorizontalScrollView.class, "mEdgeGlowLeft", theme);
            updateEdgeEffectTheme(view, HorizontalScrollView.class, "mEdgeGlowRight", theme);
        } else if (view instanceof AbsListView) {
            updateEdgeEffectTheme(view, AbsListView.class, "mEdgeGlowTop", theme);
            updateEdgeEffectTheme(view, AbsListView.class, "mEdgeGlowBottom", theme);
        } else if (view instanceof RecyclerView) {
            updateEdgeEffectTheme(view, RecyclerView.class, "mLeftGlow", theme);
            updateEdgeEffectTheme(view, RecyclerView.class, "mTopGlow", theme);
            updateEdgeEffectTheme(view, RecyclerView.class, "mRightGlow", theme);
            updateEdgeEffectTheme(view, RecyclerView.class, "mBottomGlow", theme);
        } else if (view instanceof ViewPager) {
            Field edgeEffectCompatField;
            try {
                edgeEffectCompatField = ViewPager.class.getDeclaredField("mLeftEdge");
                edgeEffectCompatField.setAccessible(true);
                Object left = edgeEffectCompatField.get(view);
                if (left instanceof EdgeEffect) {
                    updateEdgeEffectTheme((EdgeEffect) left, theme);
                } else if (left instanceof EdgeEffectCompat) {
                    updateEdgeEffectTheme(left, EdgeEffectCompat.class, "mEdgeEffect", theme);
                }
                edgeEffectCompatField = ViewPager.class.getDeclaredField("mRightEdge");
                edgeEffectCompatField.setAccessible(true);
                Object right = edgeEffectCompatField.get(view);
                if (right instanceof EdgeEffect) {
                    updateEdgeEffectTheme((EdgeEffect) right, theme);
                } else if (right instanceof EdgeEffectCompat) {
                    updateEdgeEffectTheme(right, EdgeEffectCompat.class, "mEdgeEffect", theme);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getColor(Context context, int resId) {
        try {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(resId, typedValue, true);
            if (typedValue.resourceId != 0) {
                return context.getResources().getColor(typedValue.resourceId);
            }
            return typedValue.data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Color.TRANSPARENT;
    }

    public static ColorStateList getColorStateList(Context context, int resId) {
        try {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(resId, typedValue, true);
            if (typedValue.resourceId != 0) {
                return context.getResources().getColorStateList(typedValue.resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateScrollBarDrawable(View view, Drawable thumb, Drawable track, boolean vertical) {
        if (Build.VERSION.SDK_INT < 28) {
            try {
                Method method0 = View.class.getDeclaredMethod("getScrollCache");
                method0.setAccessible(true);
                Object mScrollCache = method0.invoke(view);

                Field scrollBarField = mScrollCache.getClass().getDeclaredField("scrollBar");
                scrollBarField.setAccessible(true);
                Object scrollBar = scrollBarField.get(mScrollCache);

                Method method1 = scrollBar.getClass().getDeclaredMethod(vertical ? "setVerticalThumbDrawable" : "setHorizontalThumbDrawable", Drawable.class);//滚动条
                method1.setAccessible(true);
                Method method2 = scrollBar.getClass().getDeclaredMethod(vertical ? "setVerticalTrackDrawable" : "setHorizontalTrackDrawable", Drawable.class);//滚动条背景
                method2.setAccessible(true);
                if (thumb != null) {
                    method1.invoke(scrollBar, thumb);
                }
                if (track != null) {
                    method2.invoke(scrollBar, track);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateEdgeEffectTheme(Object object, Class clazz, String edgeEffectFieldName, Theme theme) {
        try {
            Field edgeEffectField = clazz.getDeclaredField(edgeEffectFieldName);
            edgeEffectField.setAccessible(true);
            EdgeEffect edgeEffect = (EdgeEffect) edgeEffectField.get(object);
            updateEdgeEffectTheme(edgeEffect, theme);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateEdgeEffectTheme(EdgeEffect edgeEffect, Theme theme) {
        try {
            if (edgeEffect != null) {
                if (Build.VERSION.SDK_INT < 21) {
                    Field mEdgeField = EdgeEffect.class.getDeclaredField("mEdge");
                    Field mGlowField = EdgeEffect.class.getDeclaredField("mGlow");
                    mEdgeField.setAccessible(true);
                    mGlowField.setAccessible(true);
                    Drawable mEdge = (Drawable) mEdgeField.get(edgeEffect);
                    Drawable mGlow = (Drawable) mGlowField.get(edgeEffect);
                    mEdge.setColorFilter(new ColorMatrixColorFilter(theme.getColorMatrix()));
                    mGlow.setColorFilter(new ColorMatrixColorFilter(theme.getColorMatrix()));
                } else {
                    edgeEffect.setColor(theme.getAlphaColor(50));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDrawableResourceId(Context context, AttributeSet set, @StyleableRes int[] attrs, @AttrRes int defStyleAttr,
                                            @StyleRes int defStyleRes, int styleable) {
        TypedArray a = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
        try {
            if (a.hasValue(styleable)) {
                return a.getResourceId(styleable, 0);
            }
        } finally {
            a.recycle();
        }
        return 0;
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }
}
