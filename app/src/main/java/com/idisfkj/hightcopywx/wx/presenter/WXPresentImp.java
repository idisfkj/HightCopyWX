package com.idisfkj.hightcopywx.wx.presenter;

import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.wx.model.WXModle;
import com.idisfkj.hightcopywx.wx.model.WXModleImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXPresentImp implements WXPresent{

    private WXModle mWXModle;
    private WXView mWXView;

    public WXPresentImp(WXView wxView) {
        mWXView = wxView;
        mWXModle = new WXModleImp();
    }

    @Override
    public void initData(WXDataHelper helper) {
        mWXModle.initData(helper);
    }

}
