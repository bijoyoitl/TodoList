package com.optimalbd.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RadioButton personalRB;
    RadioButton officialRB;
    RadioGroup typeRadioGroup;

    EditText titleEditText;
    EditText detailsEditText;
    EditText dateEditText;
    EditText timeEditText;
    Button submitButton;
    Button cancelButton;

    TodoManager todoManager;
    Todo todo;

    int type;
    String title;
    String details;
    String date;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        this.context = this;

        idsReference();
        todoManager = new TodoManager(context);

        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


    }

    private void idsReference() {
        typeRadioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
        personalRB = (RadioButton) findViewById(R.id.personalRB);
        officialRB = (RadioButton) findViewById(R.id.officialRB);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        detailsEditText = (EditText) findViewById(R.id.detailsEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitButton:
                type = typeRadioGroup.getCheckedRadioButtonId();
                title = titleEditText.getText().toString().trim();
                details = detailsEditText.getText().toString().trim();


                break;
            case R.id.cancelButton:
                break;
        }
    }
}
