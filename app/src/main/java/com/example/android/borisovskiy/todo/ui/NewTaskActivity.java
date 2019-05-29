package com.example.android.borisovskiy.todo.ui;

import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.borisovskiy.todo.BuildConfig;
import com.example.android.borisovskiy.todo.R;
import com.example.android.borisovskiy.todo.data.Task;
import com.example.android.borisovskiy.todo.models.TaskViewModel;
import com.example.android.borisovskiy.todo.ui.pickers.DatePickerFragment;
import com.example.android.borisovskiy.todo.ui.pickers.TimePickerFragment;
import com.example.android.borisovskiy.todo.utilities.AlarmUtil;
import com.example.android.borisovskiy.todo.utilities.DateFormatter;
import com.example.android.borisovskiy.todo.utilities.DateTimeSetter;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener,
        TimePickerFragment.OnTimeSelectedListener {

    public static final String EXTRA_DATA_TASK = BuildConfig.APPLICATION_ID + ".ui.EXTRA_DATA_TASK";

    private static final int START_DATE_DIALOG_POSITION_LATER_TODAY = 0;
    private static final int START_DATE_DIALOG_POSITION_TOMORROW = 1;
    private static final int START_DATE_DIALOG_POSITION_NEXT_WEEK = 2;
    private static final int START_DATE_DIALOG_POSITION_PICK = 3;

    private static final String DATE_PICKER_TAG = "datePicker";
    private static final String TIME_PICKER_TAG = "timePicker";

    private Calendar cal;
    private DateTimeSetter dateTimeSetter;
    private DateFormatter dateFormatter;

    private NotificationManager notificationManager;

    private TaskViewModel taskViewModel;

    private EditText taskTitleEditText;
    private TextView startDateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        cal = Calendar.getInstance();
        dateTimeSetter = new DateTimeSetter();
        dateFormatter = new DateFormatter();
        dateTimeSetter.presetTime(cal);

        bindViews();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        taskViewModel = ViewModelProviders.of(NewTaskActivity.this).get(TaskViewModel.class);
    }

    private void bindViews() {
        taskTitleEditText = findViewById(R.id.task_title_et);
        startDateTextView = findViewById(R.id.start_date_tv_task);
    }

    public void setStartDate(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setItems(R.array.start_dates_array, (dialogInterface, position) -> {
            switch (position) {
                case START_DATE_DIALOG_POSITION_LATER_TODAY:
                    setLaterTodayStartTime();
                    break;
                case START_DATE_DIALOG_POSITION_TOMORROW:
                    setTomorrowStartTime();
                    break;
                case START_DATE_DIALOG_POSITION_NEXT_WEEK:
                    setNextWeekStartDateTime();
                    break;
                case START_DATE_DIALOG_POSITION_PICK:
                    setCustomStartDateTime();
                    break;
                default:
                    break;
            }
            dialogInterface.dismiss();
        });

        alertBuilder.create().show();
    }

    public void setLaterTodayStartTime() {
        dateTimeSetter.setLaterTodayStartTime(cal);
        startDateTextView.setText(dateFormatter.formatStartDate(cal));
    }

    public void setTomorrowStartTime() {
        dateTimeSetter.setTomorrowStartTime(cal);
        startDateTextView.setText(dateFormatter.formatStartDate(cal));
    }

    public void setNextWeekStartDateTime() {
        dateTimeSetter.setNextWeekStartDateTime(cal);
        startDateTextView.setText(dateFormatter.formatStartDate(cal));
    }

    public void setCustomStartDateTime() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
        FragmentTransaction dateFragmentTransaction = fragmentManager.beginTransaction();
        datePickerFragment.show(dateFragmentTransaction, DATE_PICKER_TAG);

        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
        FragmentTransaction timeFragmentTransaction = fragmentManager.beginTransaction();
        timePickerFragment.show(timeFragmentTransaction, TIME_PICKER_TAG);
    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        cal.set(year, month, dayOfMonth);
        startDateTextView.setText(dateFormatter.formatStartDate(cal));
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        startDateTextView.setText(dateFormatter.formatStartDate(cal));
    }

    public void resetStartDateTime(View view) {
        startDateTextView.setText(getResources().getString(R.string.date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_task) {
            saveTask();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTask() {
        Task task = new Task(taskTitleEditText.getText().toString(), startDateTextView.getText().toString());
        Bundle bundle = new Bundle();
        AlarmUtil.setAlarmManager(bundle, cal, task, getApplicationContext(), notificationManager);
        taskViewModel.insert(task);
    }
}