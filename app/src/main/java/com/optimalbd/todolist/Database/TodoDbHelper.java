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
    public static final String TODO_TIME = "time";
    public static final String TODO_TYPE = "type";

    private static final String TODO_TABLE = "CREATE TABLE" + TODO_TABLE_NAME + "("
            + TODO_ID + " integer primary key autoincrement not null,"
            + TODO_TITLE + " varchar,"
            + TODO_DETAILS + " varchar,"
            + TODO_DATE + " varchar,"
            + TODO_TIME + " varchar,"
            + TODO_TYPE + " varchar);";


    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS" + TODO_TABLE);
        onCreate(sqLiteDatabase);
    }
}
