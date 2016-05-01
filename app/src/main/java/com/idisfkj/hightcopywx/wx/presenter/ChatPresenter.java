package com.idisfkj.hightcopywx.wx.presenter;

import android.content.Intent;

import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public interface ChatPresenter {
    void sendData(String chatContent,String number, String regId,ChatMessageDataHelper helper);
    void receiveData(Intent intent,ChatMessageDataHelper helper);
}
