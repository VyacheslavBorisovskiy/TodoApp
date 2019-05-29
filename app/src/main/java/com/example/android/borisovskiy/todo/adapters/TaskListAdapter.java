package com.example.android.borisovskiy.todo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.borisovskiy.todo.R;
import com.example.android.borisovskiy.todo.data.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Task> tasks;

    public TaskListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.taskTitleTextView.setText(current.getTaskTitle());
        holder.startDateTextView.setText(current.getTaskDate());
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (tasks != null)
            return tasks.size();
        else return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView taskTitleTextView;
        private TextView startDateTextView;

        private TaskViewHolder(View itemView) {
            super(itemView);

            taskTitleTextView = itemView.findViewById(R.id.task_title_tv);
            startDateTextView = itemView.findViewById(R.id.start_date_tv_rec);
        }
    }
}