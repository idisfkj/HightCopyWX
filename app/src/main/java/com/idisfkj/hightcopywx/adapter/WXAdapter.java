package com.idisfkj.hightcopywx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.util.BadgeViewUtils;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 聊天通信适配器
 * Created by idisfkj on 16/4/22.
 * Email : idisfkj@qq.com.
 */
public class WXAdapter extends RecyclerViewCursorBaseAdapter<WXAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public WXAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.wx_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.wxItemTitle.setText(CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.TITLE));
        holder.wxItemContent.setText(CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.CONTENT));
        holder.wxItemTime.setText(CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.TIME));
        holder.unReadNum = CursorUtils.formatInt(cursor, WXDataHelper.WXItemDataInfo.UNREAD_NUM);
        //回收 防止影响更新
        if (holder.badgeView != null)
            holder.badgeView.hide();
        if (holder.unReadNum > 0) {
            //显示未读信息数
            holder.badgeView = BadgeViewUtils.create(mContext, holder.wxItemPicture, String.valueOf(holder.unReadNum));
        }
//        holder.wxItemTitle.getRootView().setId(cursor.getPosition());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.wx_item_picture)
        ImageView wxItemPicture;
        @InjectView(R.id.wx_item_title)
        TextView wxItemTitle;
        @InjectView(R.id.wx_item_time)
        TextView wxItemTime;
        @InjectView(R.id.wx_item_content)
        TextView wxItemContent;
        public int unReadNum;
        public BadgeView badgeView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
