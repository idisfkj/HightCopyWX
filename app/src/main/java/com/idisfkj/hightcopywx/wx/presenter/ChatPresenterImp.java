package com.idisfkj.hightcopywx.wx.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.wx.model.ChatModle;
import com.idisfkj.hightcopywx.wx.model.ChatModleImp;
import com.idisfkj.hightcopywx.wx.view.ChatView;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterImp implements ChatPresenter, ChatModleImp.requestListener {
    private ChatView mChatView;
    private ChatModle mChatModle;

    public ChatPresenterImp(ChatView chatView) {
        mChatView = chatView;
        mChatModle = new ChatModleImp();
    }

    @Override
    public void sendData(String chatContent,String number,String regId,ChatMessageDataHelper helper) {
        mChatModle.requestData(this,chatContent,number,regId,helper);
    }

    @Override
    public void receiveData(Intent intent,ChatMessageDataHelper helper) {
        Bundle bundle = intent.getExtras();
        ChatMessageInfo info = (ChatMessageInfo) bundle.getSerializable("chatMessageInfo");
        mChatModle.insertData(info,helper);
    }

    @Override
    public void onSucceed(ChatMessageInfo chatMessageInfo,ChatMessageDataHelper helper) {
        helper.insert(chatMessageInfo);
    }

    @Override
    public void onError(String errorMessage) {
        ToastUtils.showShort("网络异常,请检查网络");
    }
}
