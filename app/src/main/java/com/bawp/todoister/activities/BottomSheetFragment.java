package com.bawp.todoister.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bawp.todoister.R;
import com.bawp.todoister.databinding.BottomSheetBinding;
import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.Utils;
import com.bawp.todoister.viewmodels.SharedViewModel;
import com.bawp.todoister.viewmodels.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.util.StringUtil;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    BottomSheetBinding binding;
    Date taskEndDate;
    Priority priority;
    Calendar calendar = Calendar.getInstance();         // Getting Java calendar class instance
    SharedViewModel sharedViewModel;

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment with binding
        binding = BottomSheetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Clear and set variables to default values when fragment is closed
        sharedViewModel.setSelectedTask(null);
        binding.enterTodoEt.setText("");
        calendar.add(Calendar.DATE, 0);
        taskEndDate = calendar.getTime();
        priority= Priority.LOW;
        binding.radioButtonLow.setChecked(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if SharedViewModel has a task to edit
        if(sharedViewModel.getSelectedTask().getValue() != null)
        {
            Task task = sharedViewModel.getSelectedTask().getValue();
            binding.enterTodoEt.setText(task.getTask());

            switch (task.getPriority()) {
                case LOW:
                    binding.radioButtonLow.setChecked(true);
                    break;
                case MEDIUM:
                    binding.radioButtonMed.setChecked(true);
                    break;
                case HIGH:
                    binding.radioButtonHigh.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setting up SharedViewModel which will take task from MainActivity
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        binding.radioButtonLow.setChecked(true);
        priority = Priority.LOW;

        // Open calendar on "calendar icon" button click
        binding.todayCalendarButton.setOnClickListener(view1 -> {
            if(binding.calendarGroup.getVisibility()==View.GONE)
                binding.calendarGroup.setVisibility(View.VISIBLE);
            else
                binding.calendarGroup.setVisibility(View.GONE);
            Utils.hideSoftKeyboard(view1);
        });

        // Getting the date from calendar
        binding.calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            Log.d("Calendar_date", "Date: " + year + "." + (month+1) + "." + day); // January in month variable is stored as 0 so it is important to add +1.
            calendar.clear();
            calendar.set(year, month, day);
            taskEndDate = calendar.getTime();
        });

        // Save the task after arrow button click
        binding.saveTodoButton.setOnClickListener(view1 ->{
            String taskText = binding.enterTodoEt.getText().toString().trim();
            if(!TextUtils.isEmpty(taskText) && taskEndDate != null)
            {
                if(sharedViewModel.getSelectedTask().getValue() == null)
                {
                    Task task = new Task(taskText, taskEndDate,
                            priority, Calendar.getInstance().getTime(),false);
                    Log.d("Insert_task", task.toString());
                    TaskViewModel.insertTask(task);
                }
                else
                {
                    Task task = sharedViewModel.getSelectedTask().getValue();
                    task.setTask(taskText);
                    task.setEndDate(taskEndDate);
                    task.setPriority(priority);
                    TaskViewModel.updateTask(task);
                }
                this.dismiss();
            }
            else
                Toast.makeText(getContext(), R.string.empty_field, Toast.LENGTH_LONG).show();

        });

        binding.todayChip.setOnClickListener(view1 -> {
            calendar.add(Calendar.DATE, 0);
            taskEndDate = calendar.getTime();
        });

        binding.tomorrowChip.setOnClickListener(view1 -> {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            taskEndDate = calendar.getTime();
        });

        binding.nextWeekChip.setOnClickListener(view1 -> {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            taskEndDate = calendar.getTime();
        });

        binding.priorityTodoButton.setOnClickListener(view1 -> {

                binding.radioGroupPriority.setVisibility(View.VISIBLE);
        });

        binding.priorityTodoButton.setOnClickListener(view1 -> {
            if(binding.radioGroupPriority.getVisibility()==View.GONE)
                binding.radioGroupPriority.setVisibility(View.VISIBLE);
            else
                binding.radioGroupPriority.setVisibility(View.GONE);

        });

        binding.radioGroupPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==binding.radioButtonHigh.getId())
                    priority = Priority.HIGH;
                else if(i==binding.radioButtonMed.getId())
                    priority = Priority.MEDIUM;
                else
                    priority = Priority.LOW;
            }
        });
    }
}