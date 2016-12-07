package com.optimalbd.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import com.optimalbd.todolist.Model.DateTime;
import com.optimalbd.todolist.Model.Todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateTodoActivity extends AppCompatActivity implements View.OnClickListener {

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
    long oldSelectedTime;
    long currentTime;

    Calendar calendar;
    Calendar calendar1;
    ArrayList<Todo> todoArrayList;
    ArrayList<DateTime> dateTimeArrayList;
    String id;
    String todoId;
    String listId;

    int selectYear;
    int selectMonth;
    int selectDay;
    int selectHour;
    int selectMinute;
    int selectSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        this.context = this;

        idsReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Todo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");
        todoId = getIntent().getStringExtra("tId");


        todoManager = new TodoManager(context);
        calendar = Calendar.getInstance();
        calendar1 = Calendar.getInstance();

        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dateEditText.setOnClickListener(this);
        timeEditText.setOnClickListener(this);

        currentTime = System.currentTimeMillis();


        if (id.equals("2")) {
            listId = getIntent().getStringExtra("listId");
            todoId = getIntent().getStringExtra("todoId");
            getSupportActionBar().setTitle("Update Todo");
            todoArrayList = new ArrayList<>();

            todoArrayList = todoManager.getTodoDetails(todoId);
            oldSelectedTime = todoManager.getSelectedTime(todoId);

            dateTimeArrayList = new ArrayList<>();

            dateTimeArrayList = todoManager.getAllDateTime(todoId);

            titleEditText.setText(todoArrayList.get(0).getTitle());
            detailsEditText.setText(todoArrayList.get(0).getDetails());
            dateEditText.setText(todoArrayList.get(0).getDate());
            timeEditText.setText(todoArrayList.get(0).getTime());

            String type = todoArrayList.get(0).getType();
            typeRadioGroup.check(Integer.parseInt(type));

            submitButton.setText("Update");

            if (listId.equals("2")) {
                personalRB.setClickable(false);
                officialRB.setClickable(false);
                timeEditText.setClickable(false);

                titleEditText.setInputType(InputType.TYPE_NULL);
                detailsEditText.setInputType(InputType.TYPE_NULL);

                titleEditText.setTextColor(getResources().getColor(R.color.colorPrimary));
                detailsEditText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

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


                if (title.equals("")) {
                    Toast.makeText(context, "Please Enter Title !", Toast.LENGTH_SHORT).show();
                } else {
                    todo = new Todo(title, details, date, time, selectedTime, type + "");

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Update Alert !");
                    builder.setMessage("Do you want to update this todo info ? ");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            long success = todoManager.updateTodoInfo(todo, todoId);

                            if (success > 0) {
                                Toast.makeText(context, "Todo Update Successful", Toast.LENGTH_SHORT).show();
                                DateTime dateTime = new DateTime(selectYear, selectMonth, selectDay, selectHour, selectMinute, selectSecond);
                                long a = todoManager.updateDateTime(dateTime, todoId);
                                if (a > 0) {
                                    Log.e("UA", "update date s");
                                } else {
                                    Log.e("UA", "update date f");
                                }

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

                break;

            case R.id.cancelButton:
                this.finish();
                break;

            case R.id.dateEditText:
                int year;
                int month;
                int day;

                year = dateTimeArrayList.get(0).getYear();
                month = dateTimeArrayList.get(0).getMonth();
                day = dateTimeArrayList.get(0).getDay();


                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        calendar1.set(Calendar.YEAR, selectedYear);
                        calendar1.set(Calendar.MONTH, selectedMonth);
                        calendar1.set(Calendar.DATE, selectedDay);

                        selectYear = selectedYear;
                        selectMonth = selectedMonth;
                        selectDay = selectedDay;

                        int month = 0;

                        if (selectedMonth == 0) {
                            month = 1;
                        } else if (selectedMonth == 1) {
                            month = 2;
                        } else if (selectedMonth == 2) {
                            month = 3;
                        } else if (selectedMonth == 3) {
                            month = 4;
                        } else if (selectedMonth == 4) {
                            month = 5;
                        } else if (selectedMonth == 5) {
                            month = 6;
                        } else if (selectedMonth == 6) {
                            month = 7;
                        } else if (selectedMonth == 7) {
                            month = 8;
                        } else if (selectedMonth == 8) {
                            month = 9;
                        } else if (selectedMonth == 9) {
                            month = 10;
                        } else if (selectedMonth == 10) {
                            month = 11;
                        } else if (selectedMonth == 11) {
                            month = 12;
                        }


                        dateEditText.setText(selectedDay + "-" + month + "-" + selectedYear);
                    }
                }, year, month, day);
                datePickerDialog.show();

                break;
            case R.id.timeEditText:
                int hour;
                int minute;

                hour = dateTimeArrayList.get(0).getHour();

                minute = dateTimeArrayList.get(0).getMinute();

                TimePickerDialog timePickerDialog;

                timePickerDialog = new TimePickerDialog(UpdateTodoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        calendar1.set(Calendar.HOUR, selectedHour);
                        calendar1.set(Calendar.MINUTE, selectedMinute);
                        calendar1.set(Calendar.SECOND, 0);

                        selectHour = selectedHour;
                        selectMinute = selectedMinute;
                        selectSecond = 0;

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
