package com.optimalbd.todolist;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.optimalbd.todolist.Adapter.TitleAdapter;
import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalenderEventActivity extends AppCompatActivity {

    ListView calenderListView;
    CustomCalendarView calendarView;
    Calendar currentCalendar;
    ArrayList<Todo> todoArrayList;
    long dateMilli;
    TodoManager todoManager;
    CompactCalendarView compactCalendarView;
    ArrayList<Todo> longDateArrayList;
    SimpleDateFormat df;
    TextView monthTextView;
    SimpleDateFormat dateFormatForMonth;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_event);

        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calenderListView = (ListView) findViewById(R.id.calenderListView);
        monthTextView = (TextView) findViewById(R.id.monthTextView);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");

        todoManager = new TodoManager(this);
        todoArrayList = new ArrayList<>();
        longDateArrayList = new ArrayList<>();
        dateMilli = System.currentTimeMillis();


        currentCalendar = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.getDefault());


        monthTextView.setText(dateFormatForMonth.format(currentCalendar.getTime()));

        longDateArrayList = todoManager.getAllLongDate();

        for (int i = 0; i < longDateArrayList.size(); i++) {
            int color;
            Long date = longDateArrayList.get(i).getLongDate();

            if (currentCalendar.getTimeInMillis() > date) {
                color = Color.GREEN;
            } else {
                color = Color.BLUE;
            }
            Event ev1 = new Event(color, date);
            compactCalendarView.addEvent(ev1);
        }


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                String selectDate = df.format(dateClicked);
                todoArrayList = todoManager.getAllCalenderEvent(selectDate);
                TitleAdapter titleAdapter = new TitleAdapter(CalenderEventActivity.this, todoArrayList);
                calenderListView.setAdapter(titleAdapter);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        String formattedDate = df.format(currentCalendar.getTime());
        todoArrayList = todoManager.getAllCalenderEvent(formattedDate);
        TitleAdapter titleAdapter = new TitleAdapter(CalenderEventActivity.this, todoArrayList);
        calenderListView.setAdapter(titleAdapter);
    }


    public void preClick(View view) {
        compactCalendarView.showPreviousMonth();
    }

    public void nextClick(View view) {
        compactCalendarView.showNextMonth();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
