package com.example.android.borisovskiy.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "task_table")
public class Task implements Parcelable {

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "title")
    private String taskTitle;

    @ColumnInfo(name = "date")
    private String taskDate;


    public Task(@NonNull String taskTitle, String taskDate) {
        this.taskTitle = taskTitle;
        this.taskDate = taskDate;
    }

    protected Task(Parcel in) {
        taskTitle = Objects.requireNonNull(in.readString());
        taskDate = in.readString();
    }

    @NonNull
    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDate() {
        return taskDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskTitle);
        dest.writeString(taskDate);
    }
}