package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.beans.WXItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXModleImp implements WXModel{
    private List<WXItemInfo> mList;
    private WXItemInfo wxItemInfo;

    @Override
    public List<WXItemInfo> initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            wxItemInfo = new WXItemInfo();
            wxItemInfo.setTitle("测试标题" + i);
            wxItemInfo.setContent("测试内容" + i);
            wxItemInfo.setTime(i + ":11");
            mList.add(wxItemInfo);
        }
        return mList;
    }
}
