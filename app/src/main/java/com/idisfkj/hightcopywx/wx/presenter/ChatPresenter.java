package com.idisfkj.hightcopywx.wx.presenter;

import android.content.Context;
import android.content.Intent;

import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public interface ChatPresenter {
    void sendData(String chatContent, String number, String regId, ChatMessageDataHelper helper);

    void receiveData(Intent intent, ChatMessageDataHelper helper);

    void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName);

    /**
     * 加载当前聊天用户状态信息
     * @param context
     * @param _id
     */
    void loadData(Context context, int _id);

    void cleanUnReadNum(Context context, String regId, String number, int unReadNum);

    void updateLasterContent(Context context,String regId,String number);

}
