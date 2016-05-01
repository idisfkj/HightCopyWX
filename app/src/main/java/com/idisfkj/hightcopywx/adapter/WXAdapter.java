package com.idisfkj.hightcopywx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.WXItemInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by idisfkj on 16/4/22.
 * Email : idisfkj@qq.com.
 */
public class WXAdapter extends RecyclerView.Adapter<WXAdapter.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    private List<WXItemInfo> mList;
    private OnItemClickListener mOnItemClickListener;

    public WXAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addData(List<WXItemInfo> list){
        mList = list;
    }

    public List<WXItemInfo> getData(){
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.wx_item, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.wxItemTitle.setText(mList.get(position).getTitle());
        holder.wxItemContent.setText(mList.get(position).getContent());
        holder.wxItemTime.setText(mList.get(position).getTime());
        holder.wxItemTitle.getRootView().setId(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.wx_item_picture)
        ImageView wxItemPicture;
        @InjectView(R.id.wx_item_title)
        TextView wxItemTitle;
        @InjectView(R.id.wx_item_time)
        TextView wxItemTime;
        @InjectView(R.id.wx_item_content)
        TextView wxItemContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }
}
