package com.bawp.todoister.activities;

import android.os.Bundle;

import com.bawp.todoister.R;
import com.bawp.todoister.databinding.ActivityMainBinding;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.viewmodels.SharedViewModel;
import com.bawp.todoister.viewmodels.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onToDoClickListener{

    ActivityMainBinding binding;        // view binding
    TaskViewModel taskViewModel;        // view model for getting and setting tasks
    SharedViewModel sharedViewModel;    // view model to share the task data to fragment

    TasksRecyclerViewAdapter tasksAdapter;
    BottomSheetFragment bottomSheetFragment;

    List<Task> tasksToDelete = new ArrayList<Task>(){};
    boolean isDeleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        // Setting up recycler widget
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Setting up ViewModel for managing tasks
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(TaskViewModel.class);

        // Setting up SharedViewModel which will share task to fragment
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Setting up Fragment
        bottomSheetFragment = new BottomSheetFragment();

        // Setting Bottom sheet behaviour with constraint layout that is in XML file
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN); // button sheet always be hidden

        // Observe the objects in tasks_table for change
        taskViewModel.getAllTasks().observe(this, tasks -> {
                tasksAdapter = new TasksRecyclerViewAdapter(tasks, MainActivity.this, this);
                binding.recyclerView.setAdapter(tasksAdapter);
            }
        );

        // Functionality on "+" button click - open fragment
        binding.fab.setOnClickListener(view -> {
            showBottomSheetDialog();
        });

        // Functionality on "trash" button click - delete selected tasks
        binding.fabDelete.setOnClickListener(view -> {
            TaskViewModel.deleteSelectedTasks(tasksToDelete);
            isDeleted= true;
            binding.fabDelete.setVisibility(View.GONE);
        });
    }

    public void showBottomSheetDialog()
    {
        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskClickListener(int adapterPosition, Task task) {
        Log.d("task_click", adapterPosition + " " + task.toString());
        sharedViewModel.setSelectedTask(task);
        showBottomSheetDialog();
    }

    // Adding the task that user want to delete to the tasksToDelete list that consists of tasks to delete
    @Override
    public void onRadioClickListener(Task task, boolean isRadioChecked) {

        if(isDeleted==true)
        {
            tasksToDelete.clear();
            isDeleted= false;
        }

        if(isRadioChecked)
            tasksToDelete.add(task);
        else if(!isRadioChecked)
            tasksToDelete.remove(task);

        if(tasksToDelete.size() != 0)
            binding.fabDelete.setVisibility(View.VISIBLE);
        else
            binding.fabDelete.setVisibility(View.GONE);

//        TaskViewModel.deleteTask(task);
//        tasksAdapter.notifyDataSetChanged();
    }
}