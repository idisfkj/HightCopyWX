package com.idisfkj.hightcopywx.wx.model;

import android.content.Context;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatModel {
    void requestData(ChatModelImp.requestListener listener, String chatContent, String number, String regId, ChatMessageDataHelper helper);

    void insertData(ChatMessageInfo info, ChatMessageDataHelper helper);

    void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName);

    void getUserInfo(Context context, ChatModelImp.cursorListener listener, int _id);

    void updateUnReadNum(Context context, String regId, String number, int unReadNum);

    void updateLasterContent(Context context, String regId, String number);
}
