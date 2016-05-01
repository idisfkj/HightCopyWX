package com.idisfkj.hightcopywx.xiaomi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.RegisterInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mStartTime;
    private String mEndTime;

    /**
     * 接收服务器发送的透传消息
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Intent intent = new Intent();
        //user information
        if (mMessage.indexOf("^") != -1 && mMessage.indexOf("@") != -1) {
            int index1 = mMessage.lastIndexOf("^");
            int index2 = mMessage.lastIndexOf("@");
            String userName = mMessage.substring(0, index1);
            String regId = mMessage.substring(index1 + 1, index2);
            String number = mMessage.substring(index2 + 1);

            RegisterInfo info = new RegisterInfo();
            info.setUserName(userName);
            info.setNumber(number);
            info.setRegId(regId);
            RegisterDataHelper helper = new RegisterDataHelper(App.getAppContext());
            Cursor cursor = helper.query(number, regId);
            if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    if (CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.REGID).equals(regId)
                            && CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.NUMBER).equals(number)) {
                        if (!CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.USER_NAME).equals(userName))
                            // update user information
                            helper.update(info, number, regId);
                        cursor.close();
                        return;
                    }
                }
                cursor.close();
            }
            // insert user information
            helper.insert(info);
        } else {
            // chat information
            int index1 = mMessage.lastIndexOf("(");
            String rMessage = mMessage.substring(0, index1);
            String extraMessage = mMessage.substring(index1 + 1);

            int index2 = extraMessage.indexOf("@");
            int index3 = extraMessage.indexOf("@", index2 + 1);
            String receiverNumber = extraMessage.substring(0, index2);
            String regId = extraMessage.substring(index2 + 1, index3);
            String sendNumber = extraMessage.substring(index3 + 1);

            ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
            chatMessageInfo.setMessage(rMessage);
            chatMessageInfo.setFlag(0);
            chatMessageInfo.setTime(CalendarUtils.getCurrentDate());
            chatMessageInfo.setReceiverNumber(receiverNumber);
            chatMessageInfo.setRegId(regId);
            chatMessageInfo.setSendNumber(sendNumber);

            if (App.mNumber.equals(sendNumber) && App.mRegId.equals(regId)) {
                //在当前聊天界面
                intent.setAction("com.idisfkj.hightcopywx.chat");
                Bundle bundle = new Bundle();
                bundle.putSerializable("chatMessageInfo", chatMessageInfo);
                intent.putExtras(bundle);
                App.getAppContext().sendBroadcast(intent);
            } else {
                //不在当前聊天界面
                ChatMessageDataHelper helper = new ChatMessageDataHelper(App.getAppContext());
                helper.insert(chatMessageInfo);
            }
        }
    }

    /**
     * 接收服务器发来的通知栏消息
     * (用户点击通知栏触发)
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
    }

    /**
     * 接收服务器发来的通知栏消息
     * （消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Log.d("TAG", "message通知:" + mMessage);
        Log.d("TAG", "Alias通知:" + mAlias);
    }

    /**
     * 接收客户端向服务器发送命令消息后返回的响应
     *
     * @param context
     * @param message
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    /**
     * 接受客户端向服务器发送注册命令消息后返回的响应
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
        SPUtils.putString("regId", mRegId).commit();
    }
}
