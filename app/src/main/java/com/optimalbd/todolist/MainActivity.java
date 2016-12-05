package com.optimalbd.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.optimalbd.todolist.Adapter.TitleAdapter;
import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ListView doneListView;
    ArrayList<Todo> todoArrayList;
    TodoManager todoManager;
    Context context;
    TitleAdapter titleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listView1);
        doneListView = (ListView) findViewById(R.id.listView2);

        setSupportActionBar(toolbar);
        todoArrayList = new ArrayList<>();
        todoManager = new TodoManager(context);

        todoArrayList = todoManager.getAllTodoTitle();
        titleAdapter = new TitleAdapter(context, todoArrayList);
        listView.setAdapter(titleAdapter);
        doneListView.setAdapter(titleAdapter);

        ListUtils.setDynamicHeight(listView);
        ListUtils.setDynamicHeight(doneListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", todoArrayList.get(i).getId());
                startActivity(intent);
            }
        });
    }

    public void addTdo(View view) {
        Intent intent = new Intent(context, AddTodoActivity.class);
        intent.putExtra("id","1");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
