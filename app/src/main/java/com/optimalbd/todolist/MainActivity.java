package com.optimalbd.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.optimalbd.todolist.Adapter.TitleAdapter;
import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.Model.Todo;
import com.stacktips.view.CustomCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    ListView doneListView;
    ArrayList<Todo> todoArrayList;
    ArrayList<Todo> doneArrayList;
    TodoManager todoManager;
    Context context;
    TitleAdapter titleAdapter;
    TitleAdapter doneTitleAdapter;
    long currentTime;
    TextView todoTextView;
    TextView doneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listView1);
        doneListView = (ListView) findViewById(R.id.listView2);
        todoTextView = (TextView) findViewById(R.id.todoTextView);
        doneTextView = (TextView) findViewById(R.id.doneTextView);
        setSupportActionBar(toolbar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("todoId", todoArrayList.get(i).getId());
                intent.putExtra("id", "1");
                startActivity(intent);
            }
        });
        doneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("todoId", doneArrayList.get(i).getId());
                intent.putExtra("id", "2");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentTime = System.currentTimeMillis();

        todoArrayList = new ArrayList<>();
        doneArrayList = new ArrayList<>();
        todoManager = new TodoManager(context);

        todoArrayList = todoManager.getAllTodoTitle(currentTime);
        doneArrayList = todoManager.getAllDoneTitle(currentTime);

        int todoSize = todoArrayList.size();
        int doneSize = doneArrayList.size();


        todoTextView.setText("Todo " + "(" + todoSize + ")");
        doneTextView.setText("Done " + "(" + doneSize + ")");

        titleAdapter = new TitleAdapter(context, todoArrayList);
        listView.setAdapter(titleAdapter);
        doneTitleAdapter = new TitleAdapter(context, doneArrayList);
        doneListView.setAdapter(doneTitleAdapter);

        ListUtils.setDynamicHeight(listView);
        ListUtils.setDynamicHeight(doneListView);

    }

    public void addTdo(View view) {
        Intent intent = new Intent(context, AddTodoActivity.class);
        intent.putExtra("id", "1");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_id:
                Intent intent = new Intent(context,CalenderEventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                android.app.FragmentManager manager = getFragmentManager();
//                EventDialog dialog = new EventDialog();
//                dialog.show(manager, "Event_Dialog");
                return true;
            default:
                return true;
        }
    }


    @SuppressLint("ValidFragment")
    private class EventDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.calender_event, null);
            dialogBuilder.setView(dialogView);

            CustomCalendarView calendarView = (CustomCalendarView)dialogView.findViewById(R.id.calendar_view);
            Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

            calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

            calendarView.setShowOverflowDate(false);

//call refreshCalendar to update calendar the view
            calendarView.refreshCalendar(currentCalendar);
            AlertDialog alertDialog = dialogBuilder.create();

            return alertDialog;

        }

    }
}
