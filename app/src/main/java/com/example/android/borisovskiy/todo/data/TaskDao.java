package com.example.android.borisovskiy.todo.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * from task_table ORDER BY title ASC")
    LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);
}