package com.bawp.todoister.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private final onToDoClickListener toDoClickListener;

    public TasksRecyclerViewAdapter(List<Task> allTasks, Context context, onToDoClickListener toDoClickListener) {
        this.tasksList = allTasks;
        this.context = context;
        this.toDoClickListener = toDoClickListener;
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

        // Object we define in XML that we can apply as a color, but will actually change colors,
        // depending on the state of the View object to which it is applied
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                    new int[] {-android.R.attr.state_enabled},
                    new int[] {android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,       // disabled
                        Utils.priorityColor(task)
                });

        // Setting the color of date chip and radio button
        holder.chip.setTextColor(Utils.priorityColor(task));
        holder.chip.setChipIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RadioButton radioButton;
        public RadioGroup radioGroup;
        public TextView taskText;
        public Chip chip;
//        onToDoClickListener onToDoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            taskText = itemView.findViewById(R.id.todo_row_todo);
            chip = itemView.findViewById(R.id.todo_row_chip);
            radioGroup = itemView.findViewById(R.id.todo_radio_group);
//            this.onToDoClickListener = toDoClickListener;

            itemView.setOnClickListener(this::onClick);
            radioButton.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Task currentTask = tasksList.get(getAdapterPosition());
            if(id == R.id.todo_row_layout)
                toDoClickListener.onTaskClickListener(getAdapterPosition(), currentTask);
            else if(id == R.id.todo_radio_button)
            {
                if (!radioButton.isSelected()) {
                    radioButton.setChecked(true);
                    radioButton.setSelected(true);
                } else {
                    radioButton.setChecked(false);
                    radioButton.setSelected(false);
                    radioGroup.clearCheck();
                }
                toDoClickListener.onRadioClickListener(currentTask, radioButton.isChecked());
            }
        }
    }
}

interface onToDoClickListener
{
    void onTaskClickListener(int adapterPosition, Task task);

    void onRadioClickListener(Task task, boolean isRadioChecked);

}

