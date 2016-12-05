package com.optimalbd.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    RadioButton personalRB;
    RadioButton officialRB;
    RadioGroup typeRadioGroup;

    EditText titleEditText;
    EditText detailsEditText;
    TextView dateEditText;
    TextView timeEditText;
    Button submitButton;
    Button cancelButton;

    TodoManager todoManager;
    Todo todo;

    int type;
    String title;
    String details;
    String date;
    String time;
    long selectedTime;

    Calendar calendar;
    Calendar calendar1;
    ArrayList<Todo> todoArrayList;
    String id;
    String todoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        this.context = this;

        idsReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Todo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");

        todoManager = new TodoManager(context);
        calendar = Calendar.getInstance();
        calendar1 = Calendar.getInstance();

        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dateEditText.setOnClickListener(this);
        timeEditText.setOnClickListener(this);


        if (id.equals("2")) {
            todoId = getIntent().getStringExtra("todoId");
            getSupportActionBar().setTitle("Update Todo");
            todoArrayList = new ArrayList<>();
            todoArrayList = todoManager.getTodoDetails(todoId);

            titleEditText.setText(todoArrayList.get(0).getTitle());
            detailsEditText.setText(todoArrayList.get(0).getDetails());
            dateEditText.setText(todoArrayList.get(0).getDate());
            timeEditText.setText(todoArrayList.get(0).getTime());
            String type = todoArrayList.get(0).getType();
            typeRadioGroup.check(Integer.parseInt(type));
            submitButton.setText("Update");
        }

    }

    private void idsReference() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        typeRadioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
        personalRB = (RadioButton) findViewById(R.id.personalRB);
        officialRB = (RadioButton) findViewById(R.id.officialRB);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        detailsEditText = (EditText) findViewById(R.id.detailsEditText);
        dateEditText = (TextView) findViewById(R.id.dateEditText);
        timeEditText = (TextView) findViewById(R.id.timeEditText);

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
                date = dateEditText.getText().toString().trim();
                time = timeEditText.getText().toString().trim();

                selectedTime = calendar1.getTimeInMillis();
                Log.e("AA", "select time : " + selectedTime);
                if (title.equals("")) {
                    Toast.makeText(context, "Please Enter Title !", Toast.LENGTH_SHORT).show();
                } else {
                    todo = new Todo(title, details, date, time, selectedTime, type + "");
                    if (id.equals("1")) {
                        long success = todoManager.addTodo(todo);
                        if (success > 0) {
                            Toast.makeText(context, "Todo Add Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "Todo Add Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                        builder.setTitle("Update Alert !");
                        builder.setMessage("Do you want to update this todo info ? ");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                long success = todoManager.updateTodoInfo(todo, todoId);

                                if (success > 0) {
                                    Toast.makeText(context, "Todo Update Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Todo Update Failed", Toast.LENGTH_SHORT).show();
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

                }

                break;

            case R.id.cancelButton:
                this.finish();
                break;

            case R.id.dateEditText:

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        calendar1.set(Calendar.YEAR, selectedYear);
                        calendar1.set(Calendar.MONTH, selectedMonth);
                        calendar1.set(Calendar.DATE, selectedDay);


                        dateEditText.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                    }
                }, year, month, day);
                datePickerDialog.show();

                break;
            case R.id.timeEditText:

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;

                timePickerDialog = new TimePickerDialog(AddTodoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        calendar1.set(Calendar.HOUR, selectedHour);
                        calendar1.set(Calendar.MINUTE, selectedMinute);
                        calendar1.set(Calendar.SECOND, 0);

                        try {
                            timeEditText.setText(Hour12(selectedHour + ":" + selectedMinute + ":00"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, hour, minute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();

                break;
        }

    }

    private String Hour12(String data) throws ParseException {

        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
        Date d = f1.parse(data);
        DateFormat f2 = new SimpleDateFormat("h:mm a");
        return f2.format(d);
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
