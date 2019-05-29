package com.example.android.borisovskiy.todo.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.borisovskiy.todo.BuildConfig;
import com.example.android.borisovskiy.todo.R;
import com.example.android.borisovskiy.todo.data.Task;
import com.example.android.borisovskiy.todo.ui.MainActivity;
import com.example.android.borisovskiy.todo.ui.NewTaskActivity;
import com.example.android.borisovskiy.todo.utilities.ParcelableUtil;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String EXTRA_DATA_NOTIF = BuildConfig.APPLICATION_ID + ".receivers.EXTRA_DATA_NOTIF";
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        sendNotification(context, intent);
    }

    private void sendNotification(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //noinspection ConstantConditions
        byte[] bytes = bundle.getByteArray(NewTaskActivity.EXTRA_DATA_TASK);
        Task task = ParcelableUtil.unmarshall(bytes, Task.CREATOR);

        String taskTitle = task.getTaskTitle();
        Intent tapNotificationIntent = new Intent(context, MainActivity.class);
        tapNotificationIntent.putExtra(EXTRA_DATA_NOTIF, task);
        tapNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, tapNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(taskTitle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, context.getString(R.string.firingAlarm),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(context.getString(R.string.notificationDescription));

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            //noinspection ConstantConditions
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}