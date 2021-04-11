package com.bawp.todoister.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.todoister.model.Task;

import java.util.List;

public class Repository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public Repository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Task> getTask(long id)
    {
        return taskDao.getTask(id);
    }

    public void insertTask(Task task)
    {
        TaskRoomDatabase.databaseWriterExecutor.execute(()->{
            taskDao.insertTask(task);
        });
    }

    public void deleteAllTasks()
    {
        TaskRoomDatabase.databaseWriterExecutor.execute(()->{
            taskDao.deleteAll();
        });
    }

    public void deleteTask(Task task)
    {
        TaskRoomDatabase.databaseWriterExecutor.execute(()->{
            taskDao.deleteTask(task);
        });
    }

    public void updateTask(Task task)
    {
        TaskRoomDatabase.databaseWriterExecutor.execute(()->{
            taskDao.updateTask(task);
        });
    }
}
