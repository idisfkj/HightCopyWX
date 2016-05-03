package com.idisfkj.hightcopywx.wx.model;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
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
public class ChatModleImp implements ChatModle {

    private ChatMessageInfo mChatMessageInfo;

    @Override
    public void requestData(final requestListener listener, final String chatContent, final String number, final String regId, final ChatMessageDataHelper helper) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlUtils.ChatUrl(chatContent, number, regId)
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
    }

    public interface requestListener {
        void onSucceed(ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper);

        void onError(String errorMessage);
    }
}
