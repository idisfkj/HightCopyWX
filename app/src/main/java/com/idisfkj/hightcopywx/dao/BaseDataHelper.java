package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public abstract class BaseDataHelper {
    private Context mContext;

    public BaseDataHelper(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract Uri getContentUri();

    protected final void notifyChange() {
        mContext.getContentResolver().notifyChange(getContentUri(), null);
    }

    protected final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    protected final Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mContext.getContentResolver().query(getContentUri(), projection, selection, selectionArgs, sortOrder);
    }

    protected final Uri insert(ContentValues values) {
        return mContext.getContentResolver().insert(getContentUri(), values);
    }

    protected final int bulkInsert(ContentValues[] values) {
        return mContext.getContentResolver().bulkInsert(getContentUri(), values);
    }

    protected final int detet(String where, String[] selectionArgs) {
        return mContext.getContentResolver().delete(getContentUri(), where, selectionArgs);
    }

    protected final int update(ContentValues values, String where, String[] selectionArgs) {
        return mContext.getContentResolver().update(getContentUri(), values, where, selectionArgs);
    }

    protected final CursorLoader getCursorLoader(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return new CursorLoader(mContext, getContentUri(), projection, selection, selectionArgs, sortOrder);
    }

    protected final android.support.v4.content.CursorLoader getV4CursorLoader(String[] projection,String selection,String[] selectionArgs,String sortOrder){
        return new android.support.v4.content.CursorLoader(mContext,getContentUri(),projection,selection,selectionArgs,sortOrder);
    }
}
