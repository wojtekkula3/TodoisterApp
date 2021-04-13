package com.bawp.todoister.activities;

import android.os.Bundle;

import com.bawp.todoister.R;
import com.bawp.todoister.databinding.ActivityMainBinding;
import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.viewmodels.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TaskViewModel taskViewModel;
    LiveData<List<Task>> allTasks;
    TasksRecyclerViewAdapter tasksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        // Setting up recycler widget
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Setting up ViewModel
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(TaskViewModel.class);


        // Observe the objects in tasks_table for change
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {

                tasksAdapter = new TasksRecyclerViewAdapter(tasks, MainActivity.this);
                binding.recyclerView.setAdapter(tasksAdapter);

            }
        });

        // Functionality on "+" button click
        binding.fab.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.set(2021, 11, 11, 10, 10);
            Task task = new Task("Clean the room", cal.getTime(),
                    Priority.MEDIUM, false);

            TaskViewModel.insertTask(task);

        });
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
}