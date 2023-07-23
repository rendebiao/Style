package com.rdb.style;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;

import androidx.annotation.IntRange;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Style {

    private static boolean dark;
    private static float scaleValue = 1.0f;
    private static Theme curTheme;
    private static Theme[] themes;
    private static StyleColor darkColor;
    private static StyleColor lightColor;
    private static Typeface typeface;
    private static ColorValue themeColorValue = new ColorValue();
    private static ColorValue foregroundColorValue = new ColorValue();
    private static ColorValue backgroundColorValue = new ColorValue();
    private static ValueAnimator themeColorAnimator = ValueAnimator.ofFloat(0, 1);
    private static ValueAnimator backgroundColorAnimator = ValueAnimator.ofFloat(0, 1);
    private static List<WeakReference<VariableColor>> variableColors = new ArrayList<>();
    private static List<OnScaleChangeListener> scaleChangeListeners = new ArrayList<>();
    private static List<OnDarkChangeListener> darkChangeListeners = new ArrayList<>();
    private static List<OnDarkUpdateListener> darkUpdateListeners = new ArrayList<>();
    private static List<OnThemeChangeListener> themeChangeListeners = new ArrayList<>();
    private static List<OnThemeUpdateListener> themeUpdateListeners = new ArrayList<>();
    private static List<OnTypefaceChangeListener> typefaceChangeListeners = new ArrayList<>();

    public static void initStyle(Theme[] themes, float scaleValue, int themeColor, boolean dark, String typefacePath) {
        Style.dark = dark;
        Style.themes = themes;
        Style.scaleValue = scaleValue;
        setTypeface(typefacePath);
        initDarkColor(0xff999999, 0xff202020, 0xff303030, 0xff616161);
        initLightColor(0xff000000, 0xffffffff, 0xffeeeeee, 0xffbdbdbd);
        backgroundColorAnimator.setDuration(300);
        backgroundColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                foregroundColorValue.updateValue(value);
                backgroundColorValue.updateValue(value);
                for (OnDarkUpdateListener darkUpdateListener : darkUpdateListeners) {
                    darkUpdateListener.onDarkUpdate(value, foregroundColorValue.getColor(), backgroundColorValue.getColor(), Style.dark);
                }
            }
        });
        themeColorAnimator.setDuration(300);
        themeColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                themeColorValue.updateValue(value);
                for (OnThemeUpdateListener themeUpdateListener : themeUpdateListeners) {
                    themeUpdateListener.onThemeUpdate(value, themeColorValue.getColor(), curTheme);
                }
            }
        });

        curTheme = fromColor(themeColor);
        apply(curTheme);
        themeColorValue.moveTo(curTheme.getColor(), false);
        foregroundColorValue.moveTo(getForegroundColor(), false);
        backgroundColorValue.moveTo(getBackgroundColor(), false);
    }

    public static void initDarkColor(int darkForegroundColor, int darkBackgroundColor, int darkBackgroundPressedColor, int darkDisableColor) {
        Style.darkColor = new StyleColor(darkBackgroundColor, darkBackgroundPressedColor, darkForegroundColor, darkDisableColor);
    }

    public static void initLightColor(int lightForegroundColor, int lightBackgroundColor, int lightBackgroundPressedColor, int lightDisableColor) {
        Style.lightColor = new StyleColor(lightBackgroundColor, lightBackgroundPressedColor, lightForegroundColor, lightDisableColor);
    }

    public static Theme fromColor(int color) {
        for (Theme theme : themes) {
            if (theme.getColor() == color) {
                return theme;
            }
        }
        return defaultTheme();
    }

    public static boolean isDark() {
        return dark;
    }

    public static void setDark(boolean dark) {
        Style.dark = dark;
        Iterator<WeakReference<VariableColor>> iterator = variableColors.iterator();
        while (iterator.hasNext()) {
            VariableColor variableColor = iterator.next().get();
            if (variableColor != null) {
                variableColor.notifyChanged();
            } else {
                iterator.remove();
            }
        }
        darkUpdateListeners.clear();
        for (OnDarkChangeListener darkChangeListener : darkChangeListeners) {
            OnDarkUpdateListener darkUpdateListener = darkChangeListener.onDarkChanged(dark);
            if (darkUpdateListener != null) {
                darkUpdateListeners.add(darkUpdateListener);
            }
        }
        if (darkUpdateListeners.size() > 0) {
            foregroundColorValue.moveTo(getForegroundColor(), true);
            backgroundColorValue.moveTo(getBackgroundColor(), true);
            if (backgroundColorAnimator.isRunning()) {
                backgroundColorAnimator.cancel();
            }
            backgroundColorAnimator.start();
        } else {
            foregroundColorValue.moveTo(getForegroundColor(), false);
            backgroundColorValue.moveTo(getBackgroundColor(), false);
        }
    }

    public static Theme defaultTheme() {
        return themes[9];
    }

    public static Theme[] getThemes() {
        return themes;
    }

    public static Theme getCurTheme() {
        return curTheme;
    }

    public static boolean isUsingTheme(Theme theme) {
        return curTheme.getColor() == theme.getColor();
    }

    public static Typeface getTypeface() {
        return typeface;
    }

    public static boolean setTypeface(String path) {
        try {
            Typeface typeface = TextUtils.isEmpty(path) ? Typeface.DEFAULT : Typeface.createFromFile(path);
            Style.typeface = typeface;
            for (OnTypefaceChangeListener typefaceChangeListener : typefaceChangeListeners) {
                typefaceChangeListener.onTypefaceChange(typeface, Typeface.NORMAL, path);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static StyleColor getStyleColor() {
        return dark ? darkColor : lightColor;
    }

    public static StyleColor getLightStyleColor() {
        return lightColor;
    }

    public static StyleColor getDarkStyleColor() {
        return darkColor;
    }

    public static int getForegroundColor() {
        return getStyleColor().getForegroundColor();
    }

    public static int getAlphaForegroundColor() {
        return getStyleColor().getAlphaForegroundColor(170);
    }

    public static int getAlphaForegroundColor(@IntRange(from = 0, to = 255) int alpha) {
        return getStyleColor().getAlphaForegroundColor(alpha);
    }

    public static int getDisableColor() {
        return getStyleColor().getDisableColor();
    }

    public static int getAlphaDisableColor() {
        return getStyleColor().getAlphaDisableColor(128);
    }

    public static int getAlphaDisableColor(@IntRange(from = 0, to = 255) int alpha) {
        return getStyleColor().getAlphaDisableColor(alpha);
    }

    public static int getBackgroundColor() {
        return getStyleColor().getBackgroundColor();
    }

    public static int getAlphaBackgroundColor() {
        return getStyleColor().getAlphaBackgroundColor(170);
    }

    public static int getAlphaBackgroundColor(@IntRange(from = 0, to = 255) int alpha) {
        return getStyleColor().getAlphaBackgroundColor(alpha);
    }

    public static void registerVariableColor(VariableColor variableColor) {
        variableColors.add(new WeakReference<>(variableColor));
    }

    public static void registerThemeChangeListener(OnThemeChangeListener themeChangeListener, boolean register) {
        themeChangeListeners.remove(themeChangeListener);
        if (register) {
            themeChangeListeners.add(themeChangeListener);
        }
    }

    public static void registerDarkChangeListener(OnDarkChangeListener darkChangeListener, boolean register) {
        darkChangeListeners.remove(darkChangeListener);
        if (register) {
            darkChangeListeners.add(darkChangeListener);
        }
    }

    public static void registerTypefaceChangeListener(OnTypefaceChangeListener typefaceChangeListener, boolean register) {
        typefaceChangeListeners.remove(typefaceChangeListener);
        if (register) {
            typefaceChangeListeners.add(typefaceChangeListener);
        }
    }

    public static int getThemeColorByContext(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeColor, typedValue, true);
        return typedValue.data;
    }

    public static Theme getThemeByContext(Context context) {
        return fromColor(getThemeColorByContext(context));
    }

    public static void apply(Theme theme) {
        curTheme = theme;
        Iterator<WeakReference<VariableColor>> iterator = variableColors.iterator();
        while (iterator.hasNext()) {
            VariableColor variableColor = iterator.next().get();
            if (variableColor != null) {
                variableColor.notifyChanged();
            } else {
                iterator.remove();
            }
        }
        themeUpdateListeners.clear();
        for (Style.OnThemeChangeListener themeChangeListener : themeChangeListeners) {
            Style.OnThemeUpdateListener themeUpdateListener = themeChangeListener.onThemeChanged(curTheme);
            if (themeUpdateListener != null) {
                themeUpdateListeners.add(themeUpdateListener);
            }
        }
        if (themeUpdateListeners.size() > 0) {
            themeColorValue.moveTo(curTheme.getColor(), true);
            if (themeColorAnimator.isRunning()) {
                themeColorAnimator.cancel();
            }
            themeColorAnimator.start();
        } else {
            themeColorValue.moveTo(curTheme.getColor(), false);
        }
    }

    public static void registerScaleChangeListener(OnScaleChangeListener scaleChangeListener, boolean register) {
        scaleChangeListeners.remove(scaleChangeListener);
        if (register) {
            scaleChangeListeners.add(scaleChangeListener);
        }
    }

    public static float getScaleValue() {
        return scaleValue;
    }

    public static void setScaleValue(float scaleValue) {
        if (Style.scaleValue != scaleValue) {
            Style.scaleValue = scaleValue;
            for (int i = 0; i < scaleChangeListeners.size(); i++) {
                scaleChangeListeners.get(i).onScaleChanged(Style.scaleValue);
            }
        }
    }

    public interface OnThemeChangeListener {
        OnThemeUpdateListener onThemeChanged(Theme theme);
    }

    public interface OnThemeUpdateListener {

        void onThemeUpdate(float updateValue, int color, Theme tTheme);
    }

    public interface OnDarkChangeListener {
        OnDarkUpdateListener onDarkChanged(boolean dark);
    }

    public interface OnDarkUpdateListener {
        void onDarkUpdate(float updateValue, int foregroundColor, int backgroundColor, boolean toDark);
    }

    public interface OnTypefaceChangeListener {
        void onTypefaceChange(Typeface typeface, int style, String path);
    }

    public interface OnScaleChangeListener {

        void onScaleChanged(float scaleValue);
    }
}
