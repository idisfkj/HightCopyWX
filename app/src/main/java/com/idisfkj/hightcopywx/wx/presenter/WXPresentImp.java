package com.idisfkj.hightcopywx.wx.presenter;

import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.wx.model.WXModel;
import com.idisfkj.hightcopywx.wx.model.WXModelImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXPresentImp implements WXPresent{

    private WXModel mWXModel;
    private WXView mWXView;

    public WXPresentImp(WXView wxView) {
        mWXView = wxView;
        mWXModel = new WXModelImp();
    }

    @Override
    public void initData(WXDataHelper helper) {
        mWXModel.initData(helper);
    }

}
