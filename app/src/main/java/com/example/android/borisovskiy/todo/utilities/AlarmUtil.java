package com.example.android.borisovskiy.todo.utilities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.android.borisovskiy.todo.data.Task;
import com.example.android.borisovskiy.todo.receivers.AlarmReceiver;
import com.example.android.borisovskiy.todo.ui.NewTaskActivity;

import java.util.Calendar;

public class AlarmUtil {

    private static final int NOTIFICATION_ID = 0;

    public static void setAlarmManager(Bundle bundle, Calendar cal, Task task, Context context,
                                       NotificationManager notificationManager) {
        Intent notifyIntent = new Intent(context, AlarmReceiver.class);

        byte[] bytes = ParcelableUtil.marshall(task);
        bundle.putByteArray(NewTaskActivity.EXTRA_DATA_TASK, bytes);
        notifyIntent.putExtras(bundle);

        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(context,
                NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notifyPendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notifyPendingIntent);
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notifyPendingIntent);
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notifyPendingIntent);
        } else {
            notificationManager.cancelAll();
            //noinspection ConstantConditions
            alarmManager.cancel(notifyPendingIntent);
        }
    }
}