package com.example.android.borisovskiy.todo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    static AppDatabase getDatabase(Context context) {
        AppDatabase localInstance = instance;
        if (localInstance == null) {
            synchronized (AppDatabase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "tasks_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return localInstance;
    }

    public abstract TaskDao getTaskDao();
}