package com.bawp.todoister.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

// Utility class with help methods
public class Utils {

    // Method to format Date which is coming from database to String format -
    // to deploy string to recyclerview row
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        // Sun, 13.04.2021
        simpleDateFormat.applyPattern("EEE, dd.MM.yyyy");
        return  simpleDateFormat.format(date);
    }

    // Method to hide keyboard
    public static void hideSoftKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Check the priority of the task and create color depends on the priority value
    public static int priorityColor(Task task) {
        int color = 0;
        switch (task.getPriority()){
            case HIGH:
                color = Color.argb(200, 201, 21, 23);
                break;
            case MEDIUM:
                color = Color.argb(200, 200, 154, 0);
                break;
            case LOW:
                color = Color.argb(200, 51, 160, 95);
                break;
            default:
                break;
        }
        return color;
    }
}
