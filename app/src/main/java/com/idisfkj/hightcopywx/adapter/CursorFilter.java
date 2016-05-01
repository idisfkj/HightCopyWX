package com.idisfkj.hightcopywx.adapter;

/**
 * Created by idisfkj on 16/4/30.
 * Email : idisfkj@qq.com.
 */

import android.database.Cursor;
import android.widget.Filter;

/**
 * <p>The CursorFilter delegates most of the work to the BaseRecyclerCursorAdapter.
 * Subclasses should override these delegate methods to run the queries
 * and convert the results into String that can be used by auto-completion
 * widgets.</p>
 */
public class CursorFilter extends Filter {
    public CursorFilterClient mCursorFilterClient;

    public CursorFilter(CursorFilterClient mCursorFilterClient) {
        this.mCursorFilterClient = mCursorFilterClient;
    }

    public interface CursorFilterClient {
        CharSequence convertToString(Cursor cursor);

        Cursor runQueryOnBackgroundThread(CharSequence constraint);

        Cursor getCursor();

        void changeCursor(Cursor cursor);
    }


    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return mCursorFilterClient.convertToString((Cursor) resultValue);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Cursor cursor = mCursorFilterClient.runQueryOnBackgroundThread(constraint);
        FilterResults results = new FilterResults();
        if (cursor != null) {
            results.count = cursor.getCount();
            results.values = cursor;
        } else {
            results.count = 0;
            results.values = null;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        Cursor oldCursor = mCursorFilterClient.getCursor();
        if (constraint != null && results.values != oldCursor) {
            mCursorFilterClient.changeCursor((Cursor) results.values);
        }

    }
}
