package com.jph.xxxrecyclerviewdivider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 针对GridLayoutManager的分割线
 * (暂未支持跨行)
 * Created by jph on 2016/12/14.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mDividerWidth;
    private int mDividerHeight;
    private int mSpanCount;

    private final int mOrientation;

    public GridItemDecoration(Context context, @DrawableRes int drawRes,
                              @DimenRes int dividerSizeRes, int spanCount, int orientation) {
        this(ContextCompat.getDrawable(context, drawRes), context.getResources().
                getDimensionPixelSize(dividerSizeRes), spanCount, orientation);
    }

    public GridItemDecoration(Context context, @DrawableRes int drawRes,
                              @DimenRes int dividerWidthRes, @DimenRes int dividerHeightRes,
                              int spanCount, int orientation) {
        this(ContextCompat.getDrawable(context, drawRes), context.getResources().
                getDimensionPixelSize(dividerWidthRes), context.getResources().
                getDimensionPixelSize(dividerHeightRes), spanCount, orientation);
    }

    public GridItemDecoration(@NonNull Drawable divider, int dividerSize, int spanCount,
                              int orientation) {
        this(divider, dividerSize, dividerSize, spanCount, orientation);
    }

    public GridItemDecoration(@NonNull Drawable divider, int dividerWidth, int dividerHeight,
                              int spanCount, int orientation) {
        mDivider = divider;
        mDividerWidth = dividerWidth;
        mDividerHeight = dividerHeight;
        mSpanCount = spanCount;
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent, state);
        drawVertical(c, parent, state);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        top = parent.getPaddingTop();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + params.rightMargin;
            right = left + mDividerWidth;
            bottom = child.getBottom() + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        left = parent.getPaddingLeft();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDividerHeight;
            right = child.getRight() + mDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == GridLayoutManager.VERTICAL) {
            getVerticalItemOffsets(outRect, view, parent, state);
        } else {
            getHorizontalItemOffsets(outRect, view, parent, state);
        }
    }

    private void getVerticalItemOffsets(Rect outRect, View view, RecyclerView parent,
                                        RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

        outRect.top = 0;
        if (isAllowShowDivider(position, parent)) {
            outRect.bottom = mDividerHeight;
        } else {
            outRect.bottom = 0;
        }

        int parentWidth = parent.getWidth();
        //每个item实际显示的宽度
        int itemContentWidth = (parentWidth - (mSpanCount - 1) * mDividerWidth) / mSpanCount;
        //每个item中预留出的边距总宽度,可能是单边有边距，可能是双边有边距
        int itemSpacingWidth = parentWidth / mSpanCount - itemContentWidth;

        int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position,
                mSpanCount);
        outRect.left = spanIndex * (mDividerWidth - itemSpacingWidth);
        int realItemIndexInGroup = spanIndex + gridLayoutManager.getSpanSizeLookup()
                .getSpanSize(position) - 1;//算上跨度的index
        outRect.right = (realItemIndexInGroup + 1) * itemSpacingWidth -
                realItemIndexInGroup * mDividerWidth;
    }

    private void getHorizontalItemOffsets(Rect outRect, View view, RecyclerView parent,
                                          RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

        outRect.left = 0;
        if (isAllowShowDivider(position, parent)) {
            outRect.right = mDividerWidth;
        } else {
            outRect.right = 0;
        }

        int parentHeight = parent.getHeight();
        //每个item实际显示的宽度
        int itemContentHeight = (parentHeight - (mSpanCount - 1) * mDividerHeight) / mSpanCount;
        //每个item中预留出的边距总宽度,可能是单边有边距，可能是双边有边距
        int itemSpacingHeight = parentHeight / mSpanCount - itemContentHeight;

        int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position,
                mSpanCount);
        outRect.top = spanIndex * (mDividerHeight - itemSpacingHeight);
        int realItemIndexInGroup = spanIndex + gridLayoutManager.getSpanSizeLookup()
                .getSpanSize(position) - 1;//算上跨度的index
//        int realItemIndexInGroup = position % mSpanCount;
        outRect.bottom = (realItemIndexInGroup + 1) * itemSpacingHeight -
                realItemIndexInGroup * mDividerHeight;
    }

    /**
     * 是否在最后一行/列
     *
     * @param position
     * @param parent
     * @return
     */
    private boolean isInLastGroup(int position, RecyclerView parent) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        return spanSizeLookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, mSpanCount)
                == spanSizeLookup.getSpanGroupIndex(position, mSpanCount);
    }

    /**
     * 得到总共多少行/列
     *
     * @param parent
     * @return
     */
    private int getGroupCount(RecyclerView parent) {
        if (parent == null || parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return 0;
        }
        if (parent.getAdapter().getItemCount() == 0) {
            return 0;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        return gridLayoutManager.getSpanSizeLookup().getSpanGroupIndex(parent.getAdapter()
                .getItemCount() - 1, mSpanCount) + 1;
    }

    /**
     * 子类可控制是否显示分割
     *
     * @param position
     * @param parent
     * @return
     */
    protected boolean isAllowShowDivider(int position, RecyclerView parent) {
        if (isInLastGroup(position, parent)) {
            //末尾不显示分割
            return false;
        }
        return true;
    }

}