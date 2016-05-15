package com.idisfkj.hightcopywx.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "hightcopywx.db";
    private static final int VERSION = 5;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RegisterDataHelper.RegisterDataInfo.TABLE.create(db);
        WXDataHelper.WXItemDataInfo.TABLE.create(db);
        ChatMessageDataHelper.ChatMessageDataInfo.TABLE.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 5){
            db.execSQL("ALTER TABLE "+ WXDataHelper.WXItemDataInfo.TABLE_NAME
                    + " ADD "+ WXDataHelper.WXItemDataInfo.UNREAD_NUM+" default '0'");
        }
        if (newVersion == 4) {
            WXDataHelper.WXItemDataInfo.TABLE.delete(db);
            onCreate(db);
        }
    }
}
