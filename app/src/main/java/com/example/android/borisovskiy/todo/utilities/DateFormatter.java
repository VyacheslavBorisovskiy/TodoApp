package com.example.android.borisovskiy.todo.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatter {

    private static final String START_DATE_FORMAT = "MMM dd, h:mm a";

    private SimpleDateFormat startDateFormat = new SimpleDateFormat(DateFormatter.START_DATE_FORMAT, Locale.getDefault());

    public String formatStartDate(Calendar cal) {
        return startDateFormat.format(cal.getTime());
    }
}