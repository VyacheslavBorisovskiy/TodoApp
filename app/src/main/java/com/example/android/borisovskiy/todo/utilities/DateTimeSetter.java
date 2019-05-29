package com.example.android.borisovskiy.todo.utilities;

import java.util.Calendar;

public class DateTimeSetter {

    public void setLaterTodayStartTime(Calendar cal) {
        cal.add(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);
    }

    public void setTomorrowStartTime(Calendar cal) {
        cal.add(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
    }

    public void setNextWeekStartDateTime(Calendar cal) {
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        int days = Calendar.MONDAY - weekDay;
        if (days <= 0) {
            days += 7;
        }

        cal.add(Calendar.DAY_OF_WEEK, days);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
    }

    public void presetTime(Calendar cal) {
        cal.add(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
    }
}