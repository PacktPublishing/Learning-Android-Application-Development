package com.packt.rrafols.example;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;

    public RecyclerItemDecoration(int color) {
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for(int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            int top = child.getTop();
            int bottom = child.getBottom();
            int left = child.getLeft();
            int right = child.getRight();

            c.drawRoundRect(left, top, right, bottom, 10.f, 10.f, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 5;
        outRect.right = 5;
    }
}



