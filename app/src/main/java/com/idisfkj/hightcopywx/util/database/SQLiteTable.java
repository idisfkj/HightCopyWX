package com.idisfkj.hightcopywx.util.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class SQLiteTable {
    private String mTableName;
    private List<Column> mColumns = new ArrayList<>();

    /**
     * default add main key
     *
     * @param mTableName
     */
    public SQLiteTable(String mTableName) {
        this.mTableName = mTableName;
        mColumns.add(new Column(BaseColumns._ID, Column.Constraint.PRIMARY_KEY, Column.DataType.INTEGER));
    }

    public SQLiteTable addColumn(Column column) {
        mColumns.add(column);
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.DataType dataType) {
        mColumns.add(new Column(columnName, null, dataType));
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.Constraint constraint, Column.DataType dataType) {
        mColumns.add(new Column(columnName, constraint, dataType));
        return this;
    }

    public void create(SQLiteDatabase db) {
        String format = " %s";
        int index = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(mTableName);
        builder.append("(");
        int count = mColumns.size();
        for (Column column : mColumns) {
            builder.append(column.getColumnName());
            builder.append(String.format(format, column.getDataType().name()));
            Column.Constraint constraint = column.getConstraint();
            if (constraint != null) {
                builder.append(String.format(format, constraint.toString()));
            }
            if (index < count - 1) {
                builder.append(",");
            }
            index++;
        }
        builder.append(");");
        db.execSQL(builder.toString());
    }

    public void delete(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);
    }

}
