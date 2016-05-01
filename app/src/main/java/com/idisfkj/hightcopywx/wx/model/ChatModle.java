package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatModle {
    void requestData(ChatModleImp.requestListener listener,String chatContent,String number,String regId,ChatMessageDataHelper helper);
    void insertData(ChatMessageInfo info, ChatMessageDataHelper helper);
}
