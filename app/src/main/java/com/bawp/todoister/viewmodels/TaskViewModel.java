package com.bawp.todoister.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.todoister.database.Repository;
import com.bawp.todoister.model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private static Repository repo;

    public TaskViewModel(@NonNull Application application)
    {
        super(application);
        repo = new Repository(application);
    }

    public LiveData<List<Task>> getAllTasks()
    {
        return repo.getAllTasks();
    }

    public LiveData<Task> getTask(long id)
    {
        return repo.getTask(id);
    }

    public static void insertTask(Task task)
    {
        repo.insertTask(task);
    }

    public static void deleteAll()
    {
        repo.deleteAllTasks();
    }

    public static void deleteTask(Task task)
    {
        repo.deleteTask(task);
    }

    public static void updateTask(Task task)
    {
        repo.updateTask(task);
    }


}
