package com.idisfkj.hightcopywx.util;

import android.database.Cursor;

/**
 * Created by idisfkj on 16/4/30.
 * Email : idisfkj@qq.com.
 */
public class CursorUtils {
    public CursorUtils() {
    }

    public static String formatString(Cursor cursor, String columnName) {
        String result = cursor.getString(cursor.getColumnIndex(columnName));
        return result;
    }

    public static int formatInt(Cursor cursor, String columnName) {
        int result = cursor.getInt(cursor.getColumnIndex(columnName));
        return result;
    }
}
