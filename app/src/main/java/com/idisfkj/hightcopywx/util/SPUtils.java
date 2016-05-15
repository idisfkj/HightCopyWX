package com.idisfkj.hightcopywx.util;

import android.content.SharedPreferences;

import com.idisfkj.hightcopywx.App;

/** SharedPreferences 工具类
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class SPUtils {
    public SPUtils() {
    }
    public static SharedPreferences.Editor putString(String key, String value){
        App.editor = App.sp.edit();
        App.editor.putString(key,value);
        return App.editor;
    }
    public static String getString(String key){
        String result = App.sp.getString(key,"");
        return result;
    }
    public static String getString(String key,String defValue){
        String result = App.sp.getString(key,defValue);
        return result;
    }

    public static SharedPreferences.Editor putInt(String key,int number){
        App.editor = App.sp.edit();
        App.editor.putInt(key,number);
        return App.editor;
    }

    public static int getInt(String key, int defValue){
        return  App.sp.getInt(key,defValue);
    }

    public static int getInt(String key){
        return App.sp.getInt(key,0);
    }
}
