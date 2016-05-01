package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.RegisterInfo;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class RegisterDataHelper extends BaseDataHelper {

    public RegisterDataHelper(Context mContext) {
        super(mContext);
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.REGISTERS_CONTENT_URI;
    }

    private ContentValues getContentValues(RegisterInfo info) {
        ContentValues values = new ContentValues();
        values.put(RegisterDataInfo.USER_NAME,info.getUserName());
        values.put(RegisterDataInfo.NUMBER,info.getNumber());
        values.put(RegisterDataInfo.REGID,info.getRegId());
        return values;
    }

    public static final class RegisterDataInfo implements BaseColumns {

        public RegisterDataInfo() {
        }
        public static final String TABLE_NAME = "register";
        public static final String USER_NAME = "userName";
        public static final String REGID = "regId";
        public static final String NUMBER = "number";
        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(USER_NAME, Column.DataType.TEXT)
                .addColumn(NUMBER, Column.DataType.TEXT)
                .addColumn(REGID, Column.DataType.TEXT);
    }

    public Cursor query(String number,String regId) {
        Cursor cursor = query(null, RegisterDataInfo.NUMBER + "=?"+ " AND "+RegisterDataInfo.REGID+"=?"
                , new String[]{number,regId}, null);
        return cursor;
    }

    public void insert(RegisterInfo info){
        ContentValues values = getContentValues(info);
        insert(values);
    }

    public int update(RegisterInfo info,String number,String regId){
        ContentValues values = getContentValues(info);
        int row = update(values,RegisterDataInfo.NUMBER+"=?"+" AND "+RegisterDataInfo.REGID
                ,new String[]{number});
        return row;
    }

}
