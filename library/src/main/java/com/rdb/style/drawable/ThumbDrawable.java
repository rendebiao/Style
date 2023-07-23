package com.rdb.style.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class ThumbDrawable extends Drawable {

    private int color;
    private Paint paint = new Paint();
    private RectF rectF = new RectF();


    public ThumbDrawable(int color) {
        this.color = color;
        paint.setAntiAlias(true);
    }

    public void setColor(int color) {
        this.color = color;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
    }

    @Override
    public final void draw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(getRectF(), paint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isStateful() {
        return false;
    }

    private RectF getRectF() {
        Rect bounds = getBounds();
        rectF.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        return rectF;
    }
}

