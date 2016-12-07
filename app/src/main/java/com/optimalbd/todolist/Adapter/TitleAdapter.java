package com.optimalbd.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimalbd.todolist.Model.Todo;
import com.optimalbd.todolist.R;

import java.util.ArrayList;

/**
 * Created by ripon on 12/5/2016.
 */

public class TitleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Todo> todoArrayList;
    private LayoutInflater inflater;

    public TitleAdapter(Context context, ArrayList<Todo> todoArrayList) {
        this.context = context;
        this.todoArrayList = todoArrayList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View rowView= view;

        if (view ==null){
            rowView = inflater.inflate(R.layout.title_list,null);
            viewHolder = new ViewHolder();

            viewHolder.titleTextView = (TextView)rowView.findViewById(R.id.titleTextView);
            viewHolder.dateTextView = (TextView)rowView.findViewById(R.id.dateTextView);
            viewHolder.timeTextView = (TextView)rowView.findViewById(R.id.timeTextView);

            rowView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.titleTextView.setText(todoArrayList.get(i).getTitle());
        viewHolder.dateTextView.setText(todoArrayList.get(i).getDate());
        viewHolder.timeTextView.setText(todoArrayList.get(i).getTime());
        return rowView;
    }

    public class ViewHolder{
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView timeTextView;
    }
}
