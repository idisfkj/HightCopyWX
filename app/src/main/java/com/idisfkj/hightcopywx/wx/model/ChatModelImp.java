package com.idisfkj.hightcopywx.wx.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatModelImp implements ChatModel {

    private ChatMessageInfo mChatMessageInfo;

    @Override
    public void requestData(final requestListener listener, final String chatContent, final String number, final String regId, final ChatMessageDataHelper helper) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlUtils.chatUrl(chatContent, number, regId)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mChatMessageInfo = new ChatMessageInfo(chatContent, 1, CalendarUtils.getCurrentDate(),
                        number, regId, SPUtils.getString("userPhone", ""));
                listener.onSucceed(mChatMessageInfo, helper);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG", volleyError.getMessage());
                listener.onError(volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(jsonObjectRequest, "chatRequest");
    }

    @Override
    public void insertData(ChatMessageInfo info, ChatMessageDataHelper helper) {
        helper.insert(info);

    }

    @Override
    public void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName) {
        if (mRegId.equals(App.DEVELOPER_ID)) {
            ChatMessageInfo info = new ChatMessageInfo(App.DEVELOPER_MESSAGE, 0, CalendarUtils.getCurrentDate(),
                    SPUtils.getString("userPhone"), App.DEVELOPER_ID, App.DEVELOPER_NUMBER);
            helper.insert(info);
        }
        helper = null;
    }

    @Override
    public void getUserInfo(Context context, cursorListener listener, int _id) {
        WXDataHelper wxHelper = new WXDataHelper(context);
        Cursor cursor = wxHelper.query(_id);
        if (cursor.moveToFirst()) {
            String mRegId = CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.REGID);
            String mNumber = CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.NUMBER);
            String userName = CursorUtils.formatString(cursor, WXDataHelper.WXItemDataInfo.TITLE);
            int unReadNum = CursorUtils.formatInt(cursor, WXDataHelper.WXItemDataInfo.UNREAD_NUM);
            listener.onSucceed(mRegId, mNumber, userName, unReadNum);
        }
        cursor.close();
        wxHelper = null;
    }

    @Override
    public void updateUnReadNum(Context context, String regId, String number, int unReadNum) {
        SPUtils.putInt("unReadNum", SPUtils.getInt("unReadNum") - unReadNum).commit();
        WXDataHelper wxHelper = new WXDataHelper(context);
        wxHelper.update(0, regId, number);
        wxHelper = null;
    }

    @Override
    public void updateLasterContent(Context context, String regId, String number) {
        ChatMessageDataHelper chatHelper = new ChatMessageDataHelper(context);
        WXDataHelper wxHelper = new WXDataHelper(context);
        Cursor cursor = chatHelper.query(number, regId);
        String lasterContent = null;
        String time = null;
        if (cursor.moveToFirst()) {
            lasterContent = CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.MESSAGE);
            time = CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.TIME);
        }
        cursor.close();
        wxHelper.update(lasterContent, time, regId, number);
        chatHelper = null;
        wxHelper = null;
    }

    public interface requestListener {
        void onSucceed(ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper);

        void onError(String errorMessage);
    }

    public interface cursorListener {
        void onSucceed(String regId, String number, String userName, int unReadNum);
    }
}
