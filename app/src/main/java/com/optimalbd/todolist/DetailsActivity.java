package com.optimalbd.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.todolist.Adapter.DetailsAdapter;
import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    String id;
    ListView detailsListView;
    TodoManager todoManager;
    ArrayList<Todo> todoArrayList;

    private TextView titleTextView;
    private TextView detailsTextView;
    private TextView dateTextView;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        detailsTextView = (TextView) findViewById(R.id.detailsTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        todoArrayList = new ArrayList<>();

        id = getIntent().getStringExtra("id");

        todoManager = new TodoManager(context);
        todoArrayList = todoManager.getTodoDetails(id);

        titleTextView.setText(todoArrayList.get(0).getTitle());
        detailsTextView.setText(todoArrayList.get(0).getDetails());
        dateTextView.setText(todoArrayList.get(0).getDate());
        timeTextView.setText(todoArrayList.get(0).getTime());

    }

    public void edit(View view) {
        Intent intent = new Intent(context, AddTodoActivity.class);
        intent.putExtra("id","2");
        intent.putExtra("todoId", id);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Delete...");
        builder.setMessage("Do you want to delete this todo ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long success = todoManager.deleteTodo(id);
                if (success > 0) {
                    Toast.makeText(DetailsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    Toast.makeText(DetailsActivity.this, "Delete Fail !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
