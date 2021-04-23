package com.bawp.todoister.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    public long id;

    public String task;

    @ColumnInfo(name = "end_date")
    public Date endDate;

    public Priority priority;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    public Task(String task, Date endDate, Priority priority, boolean isDone) {
        this.task = task;
        this.endDate = endDate;
        this.priority = priority;
        this.isDone = isDone;
    }


    public long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", endDate=" + endDate +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", isDone=" + isDone +
                '}';
    }
}
