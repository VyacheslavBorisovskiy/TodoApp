package com.example.android.borisovskiy.todo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.android.borisovskiy.todo.R;
import com.example.android.borisovskiy.todo.adapters.TaskListAdapter;
import com.example.android.borisovskiy.todo.models.TaskViewModel;
import com.example.android.borisovskiy.todo.utilities.DividerItemDecoration;

public class MainActivity extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        setRecyclerView();

        setViewModel();
    }

    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addTaskButton = findViewById(R.id.fab);
        addTaskButton.setOnClickListener(view -> {
            Intent intentNewTask = new Intent(MainActivity.this, NewTaskActivity.class);
            startActivity(intentNewTask);
        });
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.tasks_list_work);
        taskListAdapter = new TaskListAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskListAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this);
        recyclerView.addItemDecoration(itemDecor);

    }

    private void setViewModel() {
        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> taskListAdapter.setTasks(tasks));
    }
}