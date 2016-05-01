package com.idisfkj.hightcopywx.wx.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.adapter.WXAdapter;
import com.idisfkj.hightcopywx.wx.WXItemDecoration;
import com.idisfkj.hightcopywx.wx.presenter.WXPresent;
import com.idisfkj.hightcopywx.wx.presenter.WXPresentImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class WXFragment extends Fragment implements WXView, WXAdapter.OnItemClickListener {
    @InjectView(R.id.wx_recyclerView)
    RecyclerView wxRecyclerView;
    private WXAdapter wxAdapter;
    private WXPresent mWXPresent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.wx_layout, null);
        ButterKnife.inject(this, view);
        wxAdapter = new WXAdapter(getContext());
        mWXPresent = new WXPresentImp(this);
        mWXPresent.loadData();
        wxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wxRecyclerView.addItemDecoration(new WXItemDecoration(getContext()));
        wxRecyclerView.setAdapter(wxAdapter);
        wxAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setData(List<WXItemInfo> list) {
       wxAdapter.addData(list);
    }

    @Override
    public void onItemClick(View v) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        Bundle bundle = new Bundle();
        WXItemInfo itemInfo = wxAdapter.getData().get(v.getId());
        bundle.putString("regId",itemInfo.getRegId());
        bundle.putString("number",itemInfo.getNumber());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
