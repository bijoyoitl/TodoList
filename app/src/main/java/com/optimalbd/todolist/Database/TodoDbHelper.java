package com.optimalbd.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ripon on 12/1/2016.
 */

public class TodoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todoApp.sqlite";
    public static final int DATABASE_VERSION = 1;

    public static final String TODO_TABLE_NAME = "todo";
    public static final String TODO_ID = "id";
    public static final String TODO_TITLE = "title";
    public static final String TODO_DETAILS = "details";
    public static final String TODO_DATE = "date";
    public static final String TODO_LONG_DATE = "long_date";
    public static final String TODO_TIME = "time";
    public static final String TODO_SELECTED_TIME = "selectedTime";
    public static final String TODO_TYPE = "type";

    public static final String DATETIME_TABLE_NAME = "datetime";
    public static final String DATETIME_ID = "_id";
    public static final String DATETIME_TODO_ID = "todoId";
    public static final String DATETIME_YEAR = "year";
    public static final String DATETIME_MONTH = "month";
    public static final String DATETIME_DAY = "day";
    public static final String DATETIME_HOUR = "hour";
    public static final String DATETIME_MINUTE = "minute";
    public static final String DATETIME_SECOND = "second";

    private static final String TODO_TABLE = " CREATE TABLE " + TODO_TABLE_NAME + "("
            + TODO_ID + " integer primary key autoincrement not null,"
            + TODO_TITLE + " varchar,"
            + TODO_DETAILS + " varchar,"
            + TODO_DATE + " varchar,"
            + TODO_LONG_DATE + " long,"
            + TODO_TIME + " varchar,"
            + TODO_SELECTED_TIME + " long,"
            + TODO_TYPE + " varchar);";

    private static final String DATETIME_TABLE = " CREATE TABLE " + DATETIME_TABLE_NAME + "("
            + DATETIME_ID + " integer primary key autoincrement not null,"
            + DATETIME_TODO_ID + " integer,"
            + DATETIME_YEAR + " integer,"
            + DATETIME_MONTH + " integer,"
            + DATETIME_DAY + " integer,"
            + DATETIME_HOUR + " integer,"
            + DATETIME_MINUTE + " integer,"
            + DATETIME_SECOND + " integer);";


    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TODO_TABLE);
        sqLiteDatabase.execSQL(DATETIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS" + TODO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS" + DATETIME_TABLE);
        onCreate(sqLiteDatabase);
    }
}
