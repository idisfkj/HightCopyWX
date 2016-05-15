package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

import java.util.ArrayList;

/**
 * Created by idisfkj on 16/4/30.
 * Email : idisfkj@qq.com.
 */
public class ChatMessageDataHelper extends BaseDataHelper {
    public ChatMessageDataHelper(Context mContext) {
        super(mContext);
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.CHAT_MESSAGES_CONTENT_URI;
    }

    public ContentValues getContentValues(ChatMessageInfo info) {
        ContentValues values = new ContentValues();
        values.put(ChatMessageDataInfo.MESSAGE, info.getMessage());
        values.put(ChatMessageDataInfo.FLAG, info.getFlag());
        values.put(ChatMessageDataInfo.TIME, info.getTime());
        values.put(ChatMessageDataInfo.RECEIVER_NUMBER, info.getReceiverNumber());
        values.put(ChatMessageDataInfo.REGID, info.getRegId());
        values.put(ChatMessageDataInfo.SEND_NUMBER, info.getSendNumber());
        return values;
    }

    public static final class ChatMessageDataInfo implements BaseColumns {
        public static final String TABLE_NAME = "chat";
        public static final String MESSAGE = "message";
        public static final String FLAG = "flag";
        public static final String TIME = "time";
        public static final String RECEIVER_NUMBER = "receiverNumber";
        public static final String REGID = "regId";
        public static final String SEND_NUMBER = "sendNumber";
        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(MESSAGE, Column.DataType.TEXT)
                .addColumn(FLAG, Column.DataType.INTEGER)
                .addColumn(TIME, Column.DataType.TEXT)
                .addColumn(RECEIVER_NUMBER, Column.DataType.TEXT)
                .addColumn(REGID, Column.DataType.TEXT)
                .addColumn(SEND_NUMBER, Column.DataType.TEXT);
    }

    public Cursor query(String receiverNumber, String regId) {
        Cursor cursor = query(new String[]{ChatMessageDataInfo.MESSAGE, ChatMessageDataInfo.TIME}, "(" + ChatMessageDataInfo.RECEIVER_NUMBER + "=?" + " OR "
                + ChatMessageDataInfo.SEND_NUMBER + "=?" + ") AND "
                + ChatMessageDataInfo.REGID + "=?", new String[]{receiverNumber, receiverNumber, regId}, ChatMessageDataInfo._ID + " DESC");
        return cursor;
    }

    public void insert(ChatMessageInfo info) {
        ContentValues values = getContentValues(info);
        insert(values);
    }

    public void bulkInser(ArrayList<ChatMessageInfo> list) {
        ArrayList<ContentValues> valuesList = new ArrayList<>();
        for (ChatMessageInfo info : list) {
            ContentValues itemValues = getContentValues(info);
            valuesList.add(itemValues);
        }
        ContentValues[] valuesArray = new ContentValues[valuesList.size()];
        bulkInsert(valuesList.toArray(valuesArray));
    }

    public CursorLoader getCursorLoader(String receiverNumber, String regId) {
        return getCursorLoader(null, "(" + ChatMessageDataInfo.SEND_NUMBER + "=?" + " OR "
                + ChatMessageDataInfo.RECEIVER_NUMBER + "=?" + ") AND "
                + ChatMessageDataInfo.REGID + "=?", new String[]{receiverNumber, receiverNumber, regId}
                , ChatMessageDataInfo._ID + " ASC");
    }
}
