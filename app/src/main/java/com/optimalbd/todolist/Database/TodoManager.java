package com.optimalbd.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.optimalbd.todolist.Model.DateTime;
import com.optimalbd.todolist.Model.Todo;

import java.util.ArrayList;

/**
 * Created by ripon on 12/1/2016.
 */

public class TodoManager {

    private TodoDbHelper todoDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor = null;
    private Context context;

    public TodoManager(Context context) {
        this.context = context;
        todoDbHelper = new TodoDbHelper(context);
    }

    public long addTodo(Todo todo) {
        long success = 0;

        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TODO_TITLE, todo.getTitle());
        values.put(TodoDbHelper.TODO_DETAILS, todo.getDetails());
        values.put(TodoDbHelper.TODO_DATE, todo.getDate());
        values.put(TodoDbHelper.TODO_TIME, todo.getTime());
        values.put(TodoDbHelper.TODO_SELECTED_TIME, todo.getSelectedTime());
        values.put(TodoDbHelper.TODO_TYPE, todo.getType());

        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TodoDbHelper.TODO_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
        }

        return success;
    }

    public ArrayList<Todo> getAllTodoTitle(long currentTime) {
        ArrayList<Todo> todoArrayList = null;
        Todo todo;

        try {
            todoArrayList = new ArrayList<>();
            sqLiteDatabase = todoDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_SELECTED_TIME + " > " + currentTime;
            cursor = sqLiteDatabase.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_ID));
                    String title = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TITLE));
                    String date = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_DATE));
                    String user_type = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TYPE));

                    todo = new Todo(id, title, date, user_type);
                    todoArrayList.add(todo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        }

        return todoArrayList;
    }

    public ArrayList<Todo> getAllDoneTitle(long currentTime) {
        ArrayList<Todo> todoArrayList = null;
        Todo todo;

        try {
            todoArrayList = new ArrayList<>();
            sqLiteDatabase = todoDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_SELECTED_TIME + " < " + currentTime;
            cursor = sqLiteDatabase.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_ID));
                    String title = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TITLE));
                    String date = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_DATE));
                    String user_type = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TYPE));

                    todo = new Todo(id, title, date, user_type);
                    todoArrayList.add(todo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        }

        return todoArrayList;
    }

    public ArrayList<Todo> getTodoDetails(String todoId) {
        ArrayList<Todo> todoArrayList = null;
        Todo todo;

        try {
            todoArrayList = new ArrayList<>();
            sqLiteDatabase = todoDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_ID + " = '" + todoId + "'";
            cursor = sqLiteDatabase.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_ID));
                    String title = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TITLE));
                    String details = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_DETAILS));
                    String date = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_DATE));
                    String time = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TIME));
                    String user_type = cursor.getString(cursor.getColumnIndex(TodoDbHelper.TODO_TYPE));

                    todo = new Todo(id, title, details, date, time, user_type);
                    todoArrayList.add(todo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        }

        return todoArrayList;
    }

    public long getSelectedTime(String todoId) {
        long success = 0;
        try {

            sqLiteDatabase = todoDbHelper.getReadableDatabase();

            String query = "SELECT " + TodoDbHelper.TODO_SELECTED_TIME + " FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_ID + " = '" + todoId + "'";
            cursor = sqLiteDatabase.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    success = cursor.getLong(cursor.getColumnIndex(TodoDbHelper.TODO_SELECTED_TIME));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        }

        return success;
    }

    public long updateTodoInfo(Todo todo, String id) {
        long success = 0;
        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoDbHelper.TODO_TITLE, todo.getTitle());
            contentValues.put(TodoDbHelper.TODO_DETAILS, todo.getDetails());
            contentValues.put(TodoDbHelper.TODO_DATE, todo.getDate());
            contentValues.put(TodoDbHelper.TODO_TIME, todo.getTime());
            contentValues.put(TodoDbHelper.TODO_SELECTED_TIME, todo.getSelectedTime());
            contentValues.put(TodoDbHelper.TODO_TYPE, todo.getType());

            success = sqLiteDatabase.update(TodoDbHelper.TODO_TABLE_NAME, contentValues, TodoDbHelper.TODO_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public long updateTodoTime(Todo todo, String id) {
        long success = 0;
        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoDbHelper.TODO_SELECTED_TIME, todo.getSelectedTime());

            success = sqLiteDatabase.update(TodoDbHelper.TODO_TABLE_NAME, contentValues, TodoDbHelper.TODO_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }


    public long deleteTodo(String id) {
        long success = 0;
        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();
            success = sqLiteDatabase.delete(TodoDbHelper.TODO_TABLE_NAME, TodoDbHelper.TODO_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public long addDateTime(DateTime dateTime) {
        long success = 0;

        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.DATETIME_TODO_ID, dateTime.getTodoId());
        values.put(TodoDbHelper.DATETIME_YEAR, dateTime.getYear());
        values.put(TodoDbHelper.DATETIME_MONTH, dateTime.getMonth());
        values.put(TodoDbHelper.DATETIME_DAY, dateTime.getDay());
        values.put(TodoDbHelper.DATETIME_HOUR, dateTime.getHour());
        values.put(TodoDbHelper.DATETIME_MINUTE, dateTime.getMinute());
        values.put(TodoDbHelper.DATETIME_SECOND, dateTime.getSecond());

        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TodoDbHelper.DATETIME_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
        }
        return success;
    }

    public int getLastInsertId() {
        int index = 0;
        SQLiteDatabase sdb = todoDbHelper.getReadableDatabase();
        Cursor cursor = sdb.query(
                "sqlite_sequence",
                new String[]{"seq"},
                "name = ?",
                new String[]{TodoDbHelper.TODO_TABLE_NAME},
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            index = cursor.getInt(cursor.getColumnIndex("seq"));
        }
        cursor.close();
        return index;
    }

    public ArrayList<DateTime> getAllDateTime(String todoId) {
        ArrayList<DateTime> dateTimeArrayList = null;
        DateTime dateTime;

        try {
            dateTimeArrayList = new ArrayList<>();
            sqLiteDatabase = todoDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + TodoDbHelper.DATETIME_TABLE_NAME + " where " + TodoDbHelper.DATETIME_TODO_ID + " = '" + todoId + "'";
            cursor = sqLiteDatabase.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_TODO_ID));
                    int year = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_YEAR));
                    int month = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_MONTH));
                    int day = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_DAY));
                    int hour = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_HOUR));
                    int minute = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_MINUTE));
                    int second = cursor.getInt(cursor.getColumnIndex(TodoDbHelper.DATETIME_SECOND));
                    dateTime = new DateTime(id, year, month, day, hour, minute, second);
                    dateTimeArrayList.add(dateTime);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        }

        return dateTimeArrayList;
    }

    public long updateDateTime(DateTime dateTime, String id) {

        long success = 0;
        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoDbHelper.DATETIME_YEAR, dateTime.getYear());
            contentValues.put(TodoDbHelper.DATETIME_MONTH, dateTime.getMonth());
            contentValues.put(TodoDbHelper.DATETIME_DAY, dateTime.getDay());
            contentValues.put(TodoDbHelper.DATETIME_HOUR, dateTime.getHour());
            contentValues.put(TodoDbHelper.DATETIME_MINUTE, dateTime.getMinute());
            contentValues.put(TodoDbHelper.DATETIME_SECOND, dateTime.getSecond());

            success = sqLiteDatabase.update(TodoDbHelper.DATETIME_TABLE_NAME, contentValues, TodoDbHelper.DATETIME_TODO_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }


}
