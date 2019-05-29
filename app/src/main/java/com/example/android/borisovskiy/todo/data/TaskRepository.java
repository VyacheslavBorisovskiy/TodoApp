package com.example.android.borisovskiy.todo.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;

    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.getTaskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        new InsertUserTaskAsyncTask(taskDao).execute(task);
    }

    private static class InsertUserTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        InsertUserTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }
}