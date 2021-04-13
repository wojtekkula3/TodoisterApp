package com.bawp.todoister.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasksList;
    private Context context;

    public TasksRecyclerViewAdapter(List<Task> allTasks, Context context) {
        this.tasksList = allTasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TasksRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksRecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = tasksList.get(position);
        // Formatting the Date type from Database to Sting format
        String formattedDate = Utils.formatDate(task.getEndDate());

        holder.taskText.setText(task.getTask());
        holder.chip.setText(formattedDate);



    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioButton;
        public TextView taskText;
        public Chip chip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            taskText = itemView.findViewById(R.id.todo_row_todo);
            chip = itemView.findViewById(R.id.todo_row_chip);
        }
    }
}
