package com.bawp.todoister.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bawp.todoister.model.Task;

// View Model class that holds the data which comes from MainActivity and shares them to the
// BottomSheetFregment. The aim is to later edit the "task" data in the fragment.
public class SharedViewModel extends ViewModel {

    // Mutable Live Data is a kind of a LiveData bundle whose value can be changed
    private MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    public void setSelectedTask(Task task)
    {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask()
    {
        return selectedTask;
    }
}
