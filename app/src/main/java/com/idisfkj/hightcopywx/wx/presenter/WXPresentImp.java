package com.idisfkj.hightcopywx.wx.presenter;

import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.wx.model.WXModel;
import com.idisfkj.hightcopywx.wx.model.WXModleImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXPresentImp implements WXPresent{

    private WXModel mWXModle;
    private WXView mWXView;
    private List<WXItemInfo> mList;

    public WXPresentImp(WXView wxView) {
        mWXView = wxView;
        mWXModle = new WXModleImp();
    }

    @Override
    public void loadData() {
        mList = mWXModle.initData();
        mWXView.setData(mList);
    }

}
