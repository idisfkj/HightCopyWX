package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXModleImp implements WXModle {
    private List<WXItemInfo> mList;
    private WXItemInfo wxItemInfo;

    @Override
    public List<WXItemInfo> initData() {
        mList = new ArrayList<>();
        wxItemInfo = new WXItemInfo();
        if (!SPUtils.getString("regId").equals("BA8EwyLKgNGawldnEQYbbVu+vIQsCsw/FFeka9DvZLo=")) {
            wxItemInfo.setTitle("idisfkj");
            wxItemInfo.setContent("欢迎注册高仿微信App,我是该App的开发者.");
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId("BA8EwyLKgNGawldnEQYbbVu+vIQsCsw/FFeka9DvZLo=");
            wxItemInfo.setNumber("15779707839");
            mList.add(wxItemInfo);
        }else {
            wxItemInfo.setTitle("kangkang");
            wxItemInfo.setContent("你好我们现在是朋友了.");
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId("7qs+l0jHcpybN0sZlAnak8TDVwT8WcXp+Xw5sBPOAGk=");
            wxItemInfo.setNumber("2");
            mList.add(wxItemInfo);
        }
        return mList;
    }
}
