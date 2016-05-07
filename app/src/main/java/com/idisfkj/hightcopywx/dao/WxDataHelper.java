package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class WXDataHelper extends BaseDataHelper {

    public WXDataHelper(Context mContext) {
        super(mContext);
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.WXS_CONTENT_URI;
    }

    public static final class WXItemDataInfo implements BaseColumns {
        public WXItemDataInfo() {
        }

        public static final String TABLE_NAME = "wx";
        public static final String PICTURE_URL = "pictureUrl";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String TIME = "time";
        public static final String REGID = "regId";
        public static final String NUMBER = "number";
        public static final String CURRENT_ACCOUNT = "currentAccount";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(PICTURE_URL, Column.DataType.TEXT)
                .addColumn(TITLE, Column.DataType.TEXT)
                .addColumn(CONTENT, Column.DataType.TEXT)
                .addColumn(TIME, Column.DataType.TEXT)
                .addColumn(REGID, Column.DataType.TEXT)
                .addColumn(NUMBER, Column.DataType.TEXT)
                .addColumn(CURRENT_ACCOUNT, Column.DataType.TEXT);
    }

    public ContentValues getContentValues(WXItemInfo itemInfo) {
        ContentValues values = new ContentValues();
        values.put(WXItemDataInfo.PICTURE_URL, itemInfo.getPictureUrl());
        values.put(WXItemDataInfo.TITLE, itemInfo.getTitle());
        values.put(WXItemDataInfo.CONTENT, itemInfo.getContent());
        values.put(WXItemDataInfo.TIME, itemInfo.getTime());
        values.put(WXItemDataInfo.REGID, itemInfo.getRegId());
        values.put(WXItemDataInfo.NUMBER, itemInfo.getNumber());
        values.put(WXItemDataInfo.CURRENT_ACCOUNT, itemInfo.getCurrentAccount());
        return values;
    }

    public Cursor query(int id) {
        Cursor cursor = query(null, WXItemDataInfo._ID + "=?"
                , new String[]{String.valueOf(id)}, WXItemDataInfo._ID + " ASC");
        return cursor;
    }

    public Cursor query(String number, String regId, String userName) {
        Cursor cursor = query(null, WXItemDataInfo.NUMBER + "=?" + " AND "
                + WXItemDataInfo.REGID + "=?" + " AND "
                + WXItemDataInfo.TITLE + "=?"
                , new String[]{number, regId, userName}, WXItemDataInfo._ID + " ASC");
        return cursor;
    }

    public void insert(WXItemInfo itemInfo) {
        ContentValues values = getContentValues(itemInfo);
        insert(values);
    }

    public int update(String content, String regId, String number) {
        ContentValues values = new ContentValues();
        values.put(WXItemDataInfo.CONTENT, content);
        int row = update(values, WXItemDataInfo.REGID + "=?" + " AND " + WXItemDataInfo.NUMBER + "=?"
                , new String[]{regId, number});
        return row;
    }

    public int delet(String regId, String number) {
        int row = delet(WXItemDataInfo.REGID + "=?" + " AND " + WXItemDataInfo.NUMBER + "=?"
                , new String[]{regId, number});
        return row;
    }

    public android.support.v4.content.CursorLoader getCursorLoader(String currentAccout) {
        return getV4CursorLoader(null, WXItemDataInfo.CURRENT_ACCOUNT + "=?"
                , new String[]{currentAccout}, WXItemDataInfo._ID + " ASC");
    }
}
