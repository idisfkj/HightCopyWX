package com.idisfkj.hightcopywx.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.idisfkj.hightcopywx.App;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class DataProvider extends ContentProvider {

    public final String TAG = DataProvider.class.getSimpleName();
    public static final Object DBLock = new Object();
    private static final String AUTHORITY = "com.idisfkj.hightcopywx.provider";
    private static final String SCHEME = "content://";

    private static final String PATH_REGISTERS = "/registers";
    private static final String PATH_WXS = "/wxs";
    private static final String PATH_CHAT_MESSAGES = "/chats";

    public static final Uri REGISTERS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_REGISTERS);
    public static final Uri WXS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_WXS);
    public static final Uri CHAT_MESSAGES_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_CHAT_MESSAGES);

    private static final int REGISTERS = 0;
    private static final int WXS = 1;
    private static final int CHAT_MESSAGES = 2;

    private static final String REGISTERS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.idisfkj.hightcopywx.register";
    private static final String WXS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.idisfkj.hightcopywx.wx";
    private static final String CHAT_MESSAGES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.idisfkj.hightcopywx.chat";

    private static final UriMatcher sUriMatcher;
    private static DBHelper mDBHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "registers", REGISTERS);
        sUriMatcher.addURI(AUTHORITY, "wxs", WXS);
        sUriMatcher.addURI(AUTHORITY, "chats", CHAT_MESSAGES);
    }

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(App.getAppContext());
        }
        return mDBHelper;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (DBLock) {
            SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
            String table = matchTable(uri);
            builder.setTables(table);
            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case REGISTERS:
                return REGISTERS_CONTENT_TYPE;
            case WXS:
                return WXS_CONTENT_TYPE;
            case CHAT_MESSAGES:
                return CHAT_MESSAGES_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknow Uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (DBLock) {
            long row = 0;
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            db.beginTransaction();
            try {
                row = db.insert(table, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            if (row > 0) {
                Uri resultUri = ContentUris.withAppendedId(uri, row);
                getContext().getContentResolver().notifyChange(uri, null);
                return resultUri;
            }
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            int row = 0;
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            db.beginTransaction();
            try {
                row = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return row;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            int row = 0;
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            db.beginTransaction();
            try {
                row = db.update(table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return row;
        }
    }

    private String matchTable(Uri uri) {
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case REGISTERS:
                table = RegisterDataHelper.RegisterDataInfo.TABLE_NAME;
                break;
            case WXS:
                table = WXDataHelper.WXItemDataInfo.TABLE_NAME;
                break;
            case CHAT_MESSAGES:
                table = ChatMessageDataHelper.ChatMessageDataInfo.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unkonw Uri" + uri);
        }
        return table;
    }
}
