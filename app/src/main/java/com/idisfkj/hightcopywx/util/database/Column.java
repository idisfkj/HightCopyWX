package com.idisfkj.hightcopywx.util.database;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class Column {
    private Constraint mConstraint;
    private DataType mDataType;
    private String mColumnName;

    public Column(String columnName, Constraint constraint, DataType dataType) {
        mColumnName = columnName;
        mConstraint = constraint;
        mDataType = dataType;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public Constraint getConstraint() {
        return mConstraint;
    }

    public DataType getDataType() {
        return mDataType;
    }

    public static enum Constraint {

        NUll("NULL"), UNIQUE("UNIQUE"), PRIMARY_KEY("PRIMARY KEY"), NOT("NOT"), FOREIGN_KEY("FOREIGN KEY"), CHECK("CHECK");
        private String mValue;

        Constraint(String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }
    }

    public static enum DataType {
        NULL, REAL, INTEGER, TEXT, BLOB;
    }
}
