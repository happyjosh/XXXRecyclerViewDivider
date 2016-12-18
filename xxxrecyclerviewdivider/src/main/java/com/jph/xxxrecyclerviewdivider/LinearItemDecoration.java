package com.jph.xxxrecyclerviewdivider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 对应LinearLayoutManager的ItemDecoration
 * 当设置滑动方向上有padding时有bug（例如VERTICAL时，设置上下padding）
 * Created by jph on 2016/12/14.
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final int mDividerSize;

    public LinearItemDecoration(Context context, @DrawableRes int drawRes, @DimenRes int sizeRes) {
        this(ContextCompat.getDrawable(context, drawRes), context.getResources().
                getDimensionPixelSize(sizeRes));
    }

    public LinearItemDecoration(@NonNull Drawable divider, int dividerSize) {
        mDivider = divider;
        mDividerSize = dividerSize;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        if (getOrientation(parent) == LinearLayoutManager.HORIZONTAL) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                left = child.getRight() + params.rightMargin;
                right = left + mDividerSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        } else {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDividerSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (!isAllowShowDivider(view, parent)) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (getOrientation(parent) == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, mDividerSize, 0);
        } else {
            outRect.set(0, 0, 0, mDividerSize);
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() == null) {
            throw new NullPointerException("Must set LayoutManager to RecyclerView");
        }
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("Invalid LayoutManager for this ItemDecoration, " +
                    "please set LinearLayoutManager.");
        }

        return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
    }

    /**
     * 是否显示分割
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isAllowShowDivider(View view, RecyclerView parent) {
        return isAllowShowDivider(parent.getChildAdapterPosition(view), parent);
    }

    /**
     * 子类可控制是否显示分割
     *
     * @param position
     * @param parent
     * @return
     */
    protected boolean isAllowShowDivider(int position, RecyclerView parent) {
        if (position == parent.getAdapter().getItemCount() - 1) {
            //末尾不显示分割
            return false;
        }
        return true;
    }
}
