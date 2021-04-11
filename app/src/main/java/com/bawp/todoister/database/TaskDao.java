package com.bawp.todoister.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM tasks_table")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks_table WHERE task_id == :id")
    LiveData<Task> getTask(long id);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM tasks_table")
    void deleteAll();



}
