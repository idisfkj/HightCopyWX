package com.idisfkj.hightcopywx.wx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idisfkj.hightcopywx.R;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    public WXItemDecoration(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.wx_decoration_bg,null);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int count = parent.getChildCount();
        int left = parent.getLeft() - parent.getPaddingLeft();
        int right = parent.getRight() - parent.getPaddingRight();
        for (int i = 0; i<count;i++){
            int top = parent.getChildAt(i).getBottom() - parent.getChildAt(i).getPaddingBottom();
            int bottom = parent.getChildAt(i).getBottom() + drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,drawable.getIntrinsicHeight());
    }
}
