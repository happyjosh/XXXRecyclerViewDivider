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

    private Drawable mHorizontalDivider;//横向的分割线
    private Drawable mVerticalDivider;//纵向的分割线
    private int mDividerWidth;
    private int mDividerHeight;
    private int mUnderLayerOrientation = GridLayoutManager.HORIZONTAL;//横向还是纵向的分割线在下，因为有重叠部分

    public GridItemDecoration(Context context, @DrawableRes int drawRes,
                              @DimenRes int dividerSizeRes) {
        this(ContextCompat.getDrawable(context, drawRes), context.getResources().
                getDimensionPixelSize(dividerSizeRes));
    }

    public GridItemDecoration(Context context, @DrawableRes int drawRes,
                              @DimenRes int dividerWidthRes, @DimenRes int dividerHeightRes) {
        this(ContextCompat.getDrawable(context, drawRes), context.getResources().
                getDimensionPixelSize(dividerWidthRes), context.getResources().
                getDimensionPixelSize(dividerHeightRes));
    }

    public GridItemDecoration(Context context, @DrawableRes int horizontalDrawRes,
                              @DrawableRes int verticalDrawRes, @DimenRes int dividerWidthRes,
                              @DimenRes int dividerHeightRes) {
        this(ContextCompat.getDrawable(context, horizontalDrawRes), ContextCompat.getDrawable(
                context, verticalDrawRes), context.getResources().getDimensionPixelSize(
                dividerWidthRes), context.getResources().getDimensionPixelSize(dividerHeightRes));
    }

    public GridItemDecoration(@NonNull Drawable divider, int dividerSize) {
        this(divider, dividerSize, dividerSize);
    }

    public GridItemDecoration(@NonNull Drawable divider, int dividerWidth, int dividerHeight) {
        this(divider, divider, dividerWidth, dividerHeight);
    }

    public GridItemDecoration(@NonNull Drawable horizontalDivider,
                              @NonNull Drawable verticalDivider, int dividerWidth,
                              int dividerHeight) {
        mHorizontalDivider = horizontalDivider;
        mVerticalDivider = verticalDivider;
        mDividerWidth = dividerWidth;
        mDividerHeight = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mUnderLayerOrientation == GridLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent, state);
            drawVertical(c, parent, state);
        } else {
            drawVertical(c, parent, state);
            drawHorizontal(c, parent, state);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + params.rightMargin;
            right = left + mDividerWidth;
            top = child.getTop();
            bottom = child.getBottom() + mDividerHeight;
            mHorizontalDivider.setBounds(left, top, right, bottom);
            mHorizontalDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDividerHeight;
            left = child.getLeft();
            right = child.getRight() + mDividerWidth;
            mVerticalDivider.setBounds(left, top, right, bottom);
            mVerticalDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (getOrientation(parent) == GridLayoutManager.VERTICAL) {
            getVerticalItemOffsets(outRect, view, parent, state);
        } else {
            getHorizontalItemOffsets(outRect, view, parent, state);
        }
    }

    private void getVerticalItemOffsets(Rect outRect, View view, RecyclerView parent,
                                        RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

        outRect.top = 0;
        if (isAllowShowDivider(position, parent)) {
            outRect.bottom = mDividerHeight;
        } else {
            outRect.bottom = 0;
        }

        int parentWidth = parent.getWidth();
        //每个item实际显示的宽度
        int itemContentWidth = (parentWidth - (spanCount - 1) * mDividerWidth) / spanCount;
        //每个item中预留出的边距总宽度,可能是单边有边距，可能是双边有边距
        int itemSpacingWidth = parentWidth / spanCount - itemContentWidth;

        int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position,
                spanCount);
        outRect.left = spanIndex * (mDividerWidth - itemSpacingWidth);
        int realItemIndexInGroup = spanIndex + gridLayoutManager.getSpanSizeLookup()
                .getSpanSize(position) - 1;//算上跨度的index
        outRect.right = (realItemIndexInGroup + 1) * itemSpacingWidth -
                realItemIndexInGroup * mDividerWidth;
    }

    private void getHorizontalItemOffsets(Rect outRect, View view, RecyclerView parent,
                                          RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

        outRect.left = 0;
        if (isAllowShowDivider(position, parent)) {
            outRect.right = mDividerWidth;
        } else {
            outRect.right = 0;
        }

        int parentHeight = parent.getHeight();
        //每个item实际显示的宽度
        int itemContentHeight = (parentHeight - (spanCount - 1) * mDividerHeight) / spanCount;
        //每个item中预留出的边距总宽度,可能是单边有边距，可能是双边有边距
        int itemSpacingHeight = parentHeight / spanCount - itemContentHeight;

        int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position,
                spanCount);
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
        int spanCount = getSpanCount(parent);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        return spanSizeLookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, spanCount)
                == spanSizeLookup.getSpanGroupIndex(position, spanCount);
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
                .getItemCount() - 1, getSpanCount(parent)) + 1;
    }

    private int getOrientation(RecyclerView parent) {
        checkLayoutManager(parent);
        return ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
    }

    private int getSpanCount(RecyclerView parent) {
        checkLayoutManager(parent);
        return ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
    }

    private void checkLayoutManager(RecyclerView parent) {
        if (parent.getLayoutManager() == null) {
            throw new NullPointerException("Must set LayoutManager to RecyclerView");
        }
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            throw new IllegalArgumentException("Invalid LayoutManager for this ItemDecoration, " +
                    "please set GridLayoutManager.");
        }
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

    /**
     * 横向还是纵向的分割线在下，因为有重叠部分
     *
     * @param orientation
     */
    public void setUnderLayer(int orientation) {
        mUnderLayerOrientation = orientation;
    }
}