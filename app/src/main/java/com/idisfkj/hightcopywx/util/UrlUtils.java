package com.idisfkj.hightcopywx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class UrlUtils {
    private static final String PACKAGE_NAME = "com.idisfkj.hightcopywx";

    public UrlUtils() {
    }

    public static String chatUrl(String message, String number, String regId) {
        String content = null;
        try {
            content = URLEncoder.encode(message + "(" + number + "@" +
                    SPUtils.getString("regId", "") + "@" + SPUtils.getString("userPhone", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://api.xmpush.xiaomi.com/v2/message/regid" +
                "?payload=" + content +
                "&restricted_package_name=" + PACKAGE_NAME +
                "&registration_id=" + regId +
                "&notify_type=2" +
                "&pass_through=1" +
//                "&time_to_live=1000" +
                "&notify_id=0";
        return url;
    }

    public static String addFriendUrl(String regId) {
        String content = null;
        try {
            content = URLEncoder.encode(SPUtils.getString("userName") + "&" + SPUtils.getString("userPhone") + "@" +
                    SPUtils.getString("regId", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://api.xmpush.xiaomi.com/v2/message/regid" +
                "?payload=" + content +
                "&restricted_package_name=" + PACKAGE_NAME +
                "&registration_id=" + regId +
                "&notify_type=2" +
                "&pass_through=1" +
//                "&time_to_live=1000" +
                "&notify_id=0";
        return url;
    }

    public static String registerUrl(String userName, String number) {
        String content = userName + "^" + SPUtils.getString("regId", "") + "@" + number;
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://api.xmpush.xiaomi.com/v2/message/topic" +
                "?payload=" + content +
                "&restricted_package_name=" + PACKAGE_NAME +
                "&topic=register" +
                "&notify_type=2" +
                "&pass_through=1";
        return url;
    }
}
