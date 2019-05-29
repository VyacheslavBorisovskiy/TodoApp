package com.example.android.borisovskiy.todo.ui.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.android.borisovskiy.todo.utilities.DateTimeSetter;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnTimeSelectedListener listener;

    public static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        DateTimeSetter dateTimeSetter = new DateTimeSetter();
        dateTimeSetter.presetTime(cal);

        return new TimePickerDialog(getActivity(), this, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onTimeSelected(hourOfDay, minute);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (OnTimeSelectedListener) context;
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(int hourOfDay, int minute);
    }
}