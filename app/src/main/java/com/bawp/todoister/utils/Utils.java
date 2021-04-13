package com.bawp.todoister.utils;


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
}
