package com.idisfkj.hightcopywx.ui.modle;

import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class RegisterModleImp implements RegisterModle {
    private String[] user;

    @Override
    public void saveData(saveDataListener listener, EditText... editTexts) {
        user = new String[editTexts.length];
        for (int i = 0; i < editTexts.length; i++) {
            user[i] = editTexts[i].getText().toString().trim();
            if (user[i].length() <= 0) {
                ToastUtils.showShort("昵称、号码或密码不能为空");
                return;
            }
        }
        SPUtils.putString("userName", user[0])
                .putString("userPhone", user[1])
                .putString("userPassword", user[2])
                .commit();
        listener.onSucceed(user[0], user[1]);
    }

    @Override
    public void sendAll(final sendAllListener listener, String userName, String number) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlUtils.RegisterUrl(userName, number)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                listener.onSendSucceed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showShort("sendError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(request, "registerRequest");
    }

    public interface saveDataListener {
        void onSucceed(String userName, String number);
    }

    public interface sendAllListener {
        void onSendSucceed();
    }
}
