package com.idisfkj.hightcopywx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 请求api工具类
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class UrlUtils {
    private static final String PACKAGE_NAME = "com.idisfkj.hightcopywx";
    private static final String REGID_API = "https://api.xmpush.xiaomi.com/v2/message/regid";
    private static final String ALL_API = "https://api.xmpush.xiaomi.com/v2/message/all";
    private static final String PAYLOAD = "?payload=";
    private static final String RESTRICTED_PACKAGE_NAME = "&restricted_package_name=";
    private static final String REGISTRATION_ID = "&registration_id=";
    private static final String NOTIFY_TYPE = "&notify_type=2";
    private static final String PASS_THROUGH = "&pass_through=1";
    private static final String NOTIFY_ID = "&notify_id=0";

    public UrlUtils() {
    }

    /**
     * 聊天URl
     * @param message
     * @param number
     * @param regId
     * @return
     */
    public static String chatUrl(String message, String number, String regId) {
        String content = null;
        try {
            content = URLEncoder.encode(message + "(" + number + "@" +
                    SPUtils.getString("regId", "") + "@" + SPUtils.getString("userPhone", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = REGID_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                REGISTRATION_ID + regId +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }

    /**
     * 添加朋友Url
     * @param regId
     * @return
     */
    public static String addFriendUrl(String regId) {
        String content = null;
        try {
            content = URLEncoder.encode(SPUtils.getString("userName") + "&" + SPUtils.getString("userPhone") + "@" +
                    SPUtils.getString("regId", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = REGID_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                REGISTRATION_ID + regId +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }

    /**
     * 注册URl
     * @param userName
     * @param number
     * @return
     */
    public static String registerUrl(String userName, String number) {
        String content = userName + "^" + SPUtils.getString("regId", "") + "@" + number;
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = ALL_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                NOTIFY_TYPE +
                PASS_THROUGH;
        return url;
    }
}
