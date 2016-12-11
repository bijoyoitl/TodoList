package com.optimalbd.todolist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import com.stacktips.view.CustomCalendarView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ripon on 12/8/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class EventDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calender_event, null);
        dialogBuilder.setView(dialogView);


        AlertDialog alertDialog = dialogBuilder.create();
        return alertDialog;
    }
}
