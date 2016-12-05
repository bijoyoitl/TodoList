package com.optimalbd.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.optimalbd.todolist.Model.Todo;

import java.util.ArrayList;

/**
 * Created by ripon on 12/1/2016.
 */

public class TodoManager {

    TodoDbHelper todoDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor = null;
    Context context;

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

            String query = "SELECT " + TodoDbHelper.TODO_ID + "," + TodoDbHelper.TODO_TITLE + "," + TodoDbHelper.TODO_DATE + "," + TodoDbHelper.TODO_TYPE + " FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_SELECTED_TIME + " > " + currentTime + " order by " + TodoDbHelper.TODO_SELECTED_TIME + " asc";
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

            String query = "SELECT " + TodoDbHelper.TODO_ID + "," + TodoDbHelper.TODO_TITLE + "," + TodoDbHelper.TODO_DATE + "," + TodoDbHelper.TODO_TYPE + " FROM " + TodoDbHelper.TODO_TABLE_NAME + " where " + TodoDbHelper.TODO_SELECTED_TIME + " < " + currentTime;
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

    public long updateTodoInfo(Todo todo, String id) {
        long success = 0;
        try {
            sqLiteDatabase = todoDbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoDbHelper.TODO_TITLE, todo.getTitle());
            contentValues.put(TodoDbHelper.TODO_DETAILS, todo.getDetails());
            contentValues.put(TodoDbHelper.TODO_DATE, todo.getDate());
            contentValues.put(TodoDbHelper.TODO_TIME, todo.getTime());
            contentValues.put(TodoDbHelper.TODO_TYPE, todo.getType());

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
}
