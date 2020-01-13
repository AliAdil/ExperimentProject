package com.softnology.experimentproject.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softnology.experimentproject.R;

public class GridSpanDecoration extends RecyclerView.ItemDecoration{
    /*private final int padding;*/
    private final int indicatorHeight;
    private final int mIndicatorItemLength =8;
    private final int mIndicatorItemPadding = 1;
    private final int mIndicatorHeight ;
    private  Paint mPaint;
    private  Context context;

   public GridSpanDecoration(int indicatorHeight , Context _context) {
        this.indicatorHeight = indicatorHeight;
         mIndicatorHeight = indicatorHeight;
        this.context = _context;
        mPaint = new Paint();
    }
/*
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = gridLayoutManager.getSpanCount();

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        int spanIndex = params.getSpanIndex();
        int spanSize = params.getSpanSize();

        // If it is in column 0 you apply the full offset on the start side, else only half
        if (spanIndex == 0) {
            outRect.left = padding;
        } else {
            outRect.left = padding / 2;
        }

        // If spanIndex + spanSize equals spanCount (it occupies the last column) you apply the full offset on the end, else only half.
        if (spanIndex + spanSize == spanCount) {
            outRect.right = padding;
        } else {
            outRect.right = padding / 2;
        }

        // just add some vertical padding as well
        outRect.top = padding / 2;
        outRect.bottom = padding / 2;

        if(isLayoutRTL(parent)) {
            int tmp = outRect.left;
            outRect.left = outRect.right;
            outRect.right = tmp;
        }
    }

    @SuppressLint({"NewApi", "WrongConstant"})
    private static boolean isLayoutRTL(RecyclerView parent) {
        return parent.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
    }*/
   @Override
   public void getItemOffsets(Rect outRect, View view,
                              RecyclerView parent, RecyclerView.State state) {
       super.getItemOffsets(outRect, view, parent, state);
       outRect.bottom = indicatorHeight;
   }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        /*int itemCount = parent.getAdapter().getItemCount();*/
        int itemCount = 2;

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);
    }


    private void drawInactiveIndicators(Canvas c, float indicatorStartX,
                                        float indicatorPosY, int itemCount) {
        mPaint.setColor(context.getResources().getColor(R.color.colorAccent));

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY,
                    start + mIndicatorItemLength, indicatorPosY, mPaint);
            start += itemWidth;
        }
    }


    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress, int itemCount) {
        mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorItemLength * progress;

            // draw the cut off highlight
            c.drawLine(highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint);
            }
        }
    }
}
