package com.idisfkj.hightcopywx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.util.CursorUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 搜寻好友适配器
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class SearchResultAdapter extends RecyclerViewCursorBaseAdapter<SearchResultAdapter.SearchViewHolder> implements View.OnClickListener {
    private Context mContext;
    private SearchItemClickListener listener;

    public SearchResultAdapter(Context context) {
        super(context, null);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, Cursor cursor) {
        holder.searchItemName.setText(CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.USER_NAME));
        holder.searchItemNumber.setText(CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.NUMBER));
        holder.searchItemNumber.getRootView().setId(cursor.getPosition());
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_result_item, parent, false);
        view.setOnClickListener(this);
        return new SearchViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v);
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.search_item_name)
        TextView searchItemName;
        @InjectView(R.id.search_item_number)
        TextView searchItemNumber;

        SearchViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void setOnItemClickListener(SearchItemClickListener listener){
        this.listener = listener;
    }

    public interface SearchItemClickListener{
        void onItemClick(View view);
    }
}
