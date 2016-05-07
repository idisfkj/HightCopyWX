package com.idisfkj.hightcopywx.main.modle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.SearchResultAdapter;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class SearchResultModleImp implements SearchResultModle {

    private ProgressDialog pd;

    @Override
    public void buildDialog(final Context context, String number, final View view,
                            final SearchResultAdapter adapter, final requestListener listener) {
        new AlertDialog.Builder(context)
                .setIcon(R.drawable.icon)
                .setTitle(R.string.dialog_tip)
                .setMessage(String.format(context.getResources().getString(R.string.dialog_message), number))
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor cursor = adapter.getCursor();
                        if (cursor.moveToPosition(view.getId())) {
                            String number = CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.NUMBER);
                            String regId = CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.REGID);
                            String userName = CursorUtils.formatString(cursor, RegisterDataHelper.RegisterDataInfo.USER_NAME);

                            WXDataHelper wxHelper = new WXDataHelper(context);
                            Cursor wxCursor = wxHelper.query(number, regId, userName);
                            if (wxCursor.getCount() > 0 || (number.equals(SPUtils.getString("userPhone"))
                                    && regId.equals(SPUtils.getString("regId")))) {
                                ToastUtils.showShort("你已经添加了该好友！");
                            } else {
                                //发送添加好友请求
                                pd = ProgressDialog.show(context, "添加请求中...", "请稍后...", true);
                                request(userName, number, regId, listener, cursor);
                            }
                            wxCursor.close();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void request(final String userName, final String number, final String regId, final requestListener listener, final Cursor cursor) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlUtils.addFriendUrl(regId),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                listener.onSucceed(userName, number, regId, cursor, pd);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(pd);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(request, "addRequest");
    }

    public interface requestListener {
        void onSucceed(String userName, String number, String regId, Cursor cursor, ProgressDialog pd);

        void onError(ProgressDialog pd);
    }
}
