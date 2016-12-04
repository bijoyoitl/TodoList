package com.optimalbd.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.optimalbd.todolist.Model.Todo;

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

}
