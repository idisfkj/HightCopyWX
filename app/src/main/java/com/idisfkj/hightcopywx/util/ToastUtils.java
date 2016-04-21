package com.idisfkj.hightcopywx.util;

import android.widget.Toast;

import com.idisfkj.hightcopywx.App;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public class ToastUtils {

    public ToastUtils() {
    }

    public static void showShort(CharSequence text){
        Toast.makeText(App.getAppContext(),text,Toast.LENGTH_SHORT).show();
    }

    public static void showLong(CharSequence text){
        Toast.makeText(App.getAppContext(),text,Toast.LENGTH_LONG).show();
    }
}
