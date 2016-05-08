package com.idisfkj.hightcopywx.wx.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.wx.model.ChatModel;
import com.idisfkj.hightcopywx.wx.model.ChatModelImp;
import com.idisfkj.hightcopywx.wx.view.ChatView;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterImp implements ChatPresenter, ChatModelImp.requestListener {
    private ChatView mChatView;
    private ChatModel mChatModel;

    public ChatPresenterImp(ChatView chatView) {
        mChatView = chatView;
        mChatModel = new ChatModelImp();
    }

    @Override
    public void sendData(String chatContent,String number,String regId,ChatMessageDataHelper helper) {
        mChatModel.requestData(this,chatContent,number,regId,helper);
    }

    @Override
    public void receiveData(Intent intent,ChatMessageDataHelper helper) {
        Bundle bundle = intent.getExtras();
        ChatMessageInfo info = (ChatMessageInfo) bundle.getSerializable("chatMessageInfo");
        mChatModel.insertData(info,helper);
    }

    @Override
    public void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName) {
        mChatModel.initData(helper,mRegId,mNumber,userName);
    }

    @Override
    public void onSucceed(ChatMessageInfo chatMessageInfo,ChatMessageDataHelper helper) {
        mChatModel.insertData(chatMessageInfo,helper);
    }

    @Override
    public void onError(String errorMessage) {
        ToastUtils.showShort("网络异常,请检查网络");
    }
}
