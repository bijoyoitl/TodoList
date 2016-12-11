package com.optimalbd.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimalbd.todolist.Database.TodoManager;
import com.optimalbd.todolist.MainActivity;
import com.optimalbd.todolist.Model.DateTime;
import com.optimalbd.todolist.Model.Todo;
import com.optimalbd.todolist.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ripon on 12/5/2016.
 */

public class TitleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Todo> todoArrayList;
    private LayoutInflater inflater;

    Date date1;
    long dateMilli;

    public TitleAdapter(Context context, ArrayList<Todo> todoArrayList) {
        this.context = context;
        this.todoArrayList = todoArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return todoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return todoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View rowView = view;

        if (view == null) {
            rowView = inflater.inflate(R.layout.title_list, null);
            viewHolder = new ViewHolder();

            viewHolder.titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
            viewHolder.dateTextView = (TextView) rowView.findViewById(R.id.dateTextView);
            viewHolder.timeTextView = (TextView) rowView.findViewById(R.id.timeTextView);
            viewHolder.typeTextView = (TextView) rowView.findViewById(R.id.typeTextView);
            viewHolder.completeTextView = (TextView) rowView.findViewById(R.id.completeTextView);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.titleTextView.setText(todoArrayList.get(i).getTitle());
        viewHolder.dateTextView.setText(todoArrayList.get(i).getDate());
        viewHolder.timeTextView.setText(todoArrayList.get(i).getTime());


        String type = todoArrayList.get(i).getType();
        if (type.equals("2131492976")){
            viewHolder.typeTextView.setText("Official");
        }else {
            viewHolder.typeTextView.setText("Personal");
        }


        viewHolder.completeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoManager todoManager = new TodoManager(context);
                String id = todoArrayList.get(i).getId();

                Calendar calendar = Calendar.getInstance();
                int Year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                int f_month = month+1;

                String date= day+"-"+f_month+"-"+Year;
                String time= null;

                try {
                    time = Hour12(hour + ":" + minute + ":00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long cTime= System.currentTimeMillis();


                if (!date.equals("")) {
                    try {
                        date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateMilli = date1.getTime();
                } else {
                    dateMilli = 0L;
                }


                Todo todo = new Todo(date,dateMilli,time,cTime);
                long a= todoManager.updateTodoCompleteTime(todo,id);

                if (a > 0) {
                    Log.e("TA", " complete successful");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else {
                    Log.e("TA", "complete fail");
                }

                DateTime dateTime = new DateTime(Year, month, day, hour, minute, 0);
                long s = todoManager.updateDateTime(dateTime, id);
                if (s > 0) {
                    Log.e("TA", " date update  successful");
                } else {
                    Log.e("TA", "date update fail");
                }

            }
        });


        return rowView;
    }

    public class ViewHolder {
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView typeTextView;
        private TextView completeTextView;
    }
    private String Hour12(String data) throws ParseException {

        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
        Date d = f1.parse(data);
        DateFormat f2 = new SimpleDateFormat("h:mm a");
        return f2.format(d);
    }
}
