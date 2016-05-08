package com.idisfkj.hightcopywx.util;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.idisfkj.hightcopywx.App;

/**
 * Volley网络请求工具类
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class VolleyUtils {

    public VolleyUtils() {
    }

    private static RequestQueue requestQueue = Volley.newRequestQueue(App.getAppContext());

    public static void addQueue(Request<?> request ,Object object){
        if (object!=null){
            request.setTag(object);
        }
        requestQueue.add(request);
    }

    public static void cancelAll(Object object){
        requestQueue.cancelAll(object);
    }


}
