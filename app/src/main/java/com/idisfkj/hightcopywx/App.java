package com.idisfkj.hightcopywx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public class App extends Application{
    private static Context mContext;
    public static final String APP_ID = "2882303761517464903";
    public static final String APP_KEY = "5631746467903";
    public static final String APP_SECRET_KEY = "HxMA7STSUQMLEiDX+zo+5A==";
    public static final String TAG = "com.idisfkj.hightcopywx";
    public static final String DEVELOPER_ID = "or9bQepyi0Daf4woKJEAHfmjTXv8llNqH3G5gX6jvVc=";
    public static final String DEVELOPER_NUMBER = "666666";
    public static final String DEVELOPER_NAME = "idisfkj";
    public static final String DEVELOPER_MESSAGE = "欢迎注册高仿微信App,我是该App的开发者，因为还没开发添加好友，所以目前只能与我测试互动，如有问题可以在此留言与我。";
    public static final String HELLO_MESSAGE = "你已添加了%s，现在可以开始聊天了";
    public static String mNumber = "-1";
    public static String mRegId = "-1";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sp = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        //小米推送调试日记
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
