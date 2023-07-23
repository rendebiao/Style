package com.rdb.style.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rdb.style.helper.RecyclerViewHelper;

public class RecyclerView extends androidx.recyclerview.widget.RecyclerView implements StyleView<RecyclerViewHelper> {

    private int scrollPosition;
    private RecyclerViewHelper recyclerViewHelper;
    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (scrollPosition != -1) {
                scrollToPosition(scrollPosition);
                scrollPosition = -1;
            }
        }
    };

    public RecyclerView(Context context) {
        super(context);
        recyclerViewHelper = onCreateHelper();
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        recyclerViewHelper = onCreateHelper();
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        recyclerViewHelper = onCreateHelper();
    }

    @Override
    public RecyclerViewHelper onCreateHelper() {
        return new RecyclerViewHelper(this);
    }

    public final int getFirstVisiblePosition() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view != null && view.getBottom() > 0) {
                return getChildAdapterPosition(view);
            }
        }
        return -1;
    }

    public final int getLastVisiblePosition() {
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (view != null && view.getTop() < getHeight()) {
                return getChildAdapterPosition(view);
            }
        }
        return -1;
    }

    public void scrollToPositionEx(int position) {
        int curPosition = getFirstVisiblePosition();
        if (position > curPosition) {
            scrollToPosition(getAdapter().getItemCount() - 1);
            scrollPosition = position;
            removeCallbacks(scrollRunnable);
            post(scrollRunnable);
        } else if (position < curPosition) {
            scrollToPosition(position);
        }
    }

    public static class DefaultItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {

        public static final int HORIZONTAL = androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
        public static final int VERTICAL = androidx.recyclerview.widget.RecyclerView.VERTICAL;
        public static final int GRID = Integer.MIN_VALUE;
        @Type
        private int type;
        private Drawable divider;
        private int dividerWidth;
        private int dividerHeight;
        private int[] attrs = new int[]{
                android.R.attr.listDivider, android.R.attr.dividerHeight
        };

        public DefaultItemDecoration(Context context, @Type int type) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            divider = typedArray.getDrawable(0);
            int size = typedArray.getDimensionPixelOffset(1, 0);
            dividerWidth = size == 0 ? divider.getIntrinsicWidth() : size;
            dividerHeight = size == 0 ? divider.getIntrinsicHeight() : size;
            typedArray.recycle();
            this.type = type;
        }

        public DefaultItemDecoration(Context context, @Type int type, int color, int dividerSize) {
            divider = new ColorDrawable(color);
            dividerWidth = dividerHeight = dividerSize;
            this.type = type;
        }

        @Override
        public void onDraw(Canvas c, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
            //调用这个绘制方法，RecyclerView会回调该绘制方法，需要我们自己去绘制条目的间隔线
            if (type == VERTICAL) {
                //垂直
                drawVertical(c, parent);
            } else if (type == HORIZONTAL) {
                //水平
                drawHorizontal(c, parent);
            } else if (type == GRID) {
                drawGridVertical(c, parent);
                drawGridHorizontal(c, parent);
            }
        }

        private void drawVertical(Canvas c, androidx.recyclerview.widget.RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                androidx.recyclerview.widget.RecyclerView.LayoutParams params = (androidx.recyclerview.widget.RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
                int bottom = top + dividerHeight;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

        private void drawHorizontal(Canvas c, androidx.recyclerview.widget.RecyclerView parent) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                androidx.recyclerview.widget.RecyclerView.LayoutParams params = (androidx.recyclerview.widget.RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
                int right = left + +dividerWidth;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

        private void drawGridVertical(Canvas c, androidx.recyclerview.widget.RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                androidx.recyclerview.widget.RecyclerView.LayoutParams params = (androidx.recyclerview.widget.RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getRight() + params.rightMargin;
                int right = left + dividerWidth;
                int top = child.getTop() - params.topMargin;
                int bottom = child.getBottom() + params.bottomMargin;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

        private void drawGridHorizontal(Canvas c, androidx.recyclerview.widget.RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                androidx.recyclerview.widget.RecyclerView.LayoutParams params = (androidx.recyclerview.widget.RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getLeft() - params.leftMargin;
                int right = child.getRight() + params.rightMargin + dividerHeight;
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + dividerHeight;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
            if (type == VERTICAL) {
                outRect.set(0, 0, 0, divider.getIntrinsicHeight());
            } else if (type == HORIZONTAL) {
                outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
            } else if (type == GRID) {
                int right = divider.getIntrinsicWidth();
                int bottom = divider.getIntrinsicHeight();
                androidx.recyclerview.widget.RecyclerView.LayoutParams params = (androidx.recyclerview.widget.RecyclerView.LayoutParams) view.getLayoutParams();
                int itemPosition = params.getViewAdapterPosition();
                if (isLastColum(itemPosition, parent)) {
                    right = 0;
                }
                if (isLastRow(itemPosition, parent)) {
                    bottom = 0;
                }
                outRect.set(0, 0, right, bottom);
            }
        }

        private boolean isLastRow(int itemPosition, androidx.recyclerview.widget.RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            if (spanCount != -1) {
                int childCount = parent.getAdapter().getItemCount();
                int lastRowCount = childCount % spanCount;
                //最后一行的数量小于spanCount
                if (lastRowCount == 0 || lastRowCount < spanCount) {
                    return true;
                }
            }
            return false;
        }

        private int getSpanCount(androidx.recyclerview.widget.RecyclerView parent) {
            androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager lm = (GridLayoutManager) layoutManager;
                return lm.getSpanCount();
            }
            return -1;
        }

        private boolean isLastColum(int itemPosition, androidx.recyclerview.widget.RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            if (spanCount != -1) {
                if ((itemPosition + 1) % spanCount == 0) {
                    return true;
                }
            }
            return false;
        }

        @IntDef({DefaultItemDecoration.VERTICAL, DefaultItemDecoration.HORIZONTAL, DefaultItemDecoration.GRID})
        public @interface Type {

        }
    }
}
