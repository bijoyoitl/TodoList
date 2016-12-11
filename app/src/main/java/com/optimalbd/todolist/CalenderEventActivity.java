package com.optimalbd.todolist;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_event);

        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calenderListView = (ListView) findViewById(R.id.calenderListView);

        todoArrayList = new ArrayList<>();
        todoManager = new TodoManager(this);




        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        Event ev1 =new Event(Color.GREEN, 1481738400000L, "Some extra data that I want to store.");

        compactCalendarView.addEvent(ev1);

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        Event ev2 = new Event(Color.GREEN, 1481479200000L);
        compactCalendarView.addEvent(ev2);    // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT

        Event q = new Event(Color.GREEN, 1481738400000L);
        compactCalendarView.addEvent(q);    // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT

        Event w = new Event(Color.GREEN, 1481738400000L);
        compactCalendarView.addEvent(w);

//        List<Event> events = compactCalendarView.getEvents(1481392800000L); // can also take a Date object



        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });












       /* currentCalendar = Calendar.getInstance(Locale.getDefault());

        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        calendarView.setShowOverflowDate(false);
        calendarView.refreshCalendar(currentCalendar);


        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Toast.makeText(CalenderEventActivity.this, df.format(date), Toast.LENGTH_SHORT).show();

                todoArrayList = todoManager.getAllCalenderEvent(df.format(date) + "");
                TitleAdapter titleAdapter = new TitleAdapter(CalenderEventActivity.this, todoArrayList);
                calenderListView.setAdapter(titleAdapter);
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
                Toast.makeText(CalenderEventActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
        if (null != typeface) {
            calendarView.setCustomTypeface(typeface);
            calendarView.refreshCalendar(currentCalendar);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(currentCalendar.getTime());
//
//        todoArrayList = todoManager.getAllCalenderEvent(formattedDate);
//        TitleAdapter titleAdapter = new TitleAdapter(CalenderEventActivity.this, todoArrayList);
//        calenderListView.setAdapter(titleAdapter);
    }



}
