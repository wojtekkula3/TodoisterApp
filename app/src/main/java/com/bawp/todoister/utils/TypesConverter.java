package com.bawp.todoister.utils;

import android.provider.ContactsContract;

import androidx.room.TypeConverter;

import com.bawp.todoister.model.Priority;

import java.util.Date;

//  Class used to convert types that are not supported by Room Database
// (e.g. Date, Priority) on the types that the database identifies (Long, String)
public class TypesConverter {

    // Konwersja z typu bazodanowego do typu daty - wyciągamy z bazy danych
    // Conversion from database Long type to Date type - extract from database
    @TypeConverter
    public static Date timestampToDate(Long value){
        return value == null ? null : new Date(value);
    }

    // Konwersja z daty do typu bazodanowego - wrzucamy do bazy danych
    // Conversion from Date type to database Long type - extract to database
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    // Konwersja z typu bazodanowego do typu Priority - wyciągamy z bazy danych
    // Conversion from database String type to Priority type - extract from database
    @TypeConverter
    public static Priority stringToPriority(String value){
        return value == null ? null : Priority.valueOf(value);
    }

    @TypeConverter
    // Konwersja z typu Priority do typu bazodanowego - wrzucamy do bazy danych
    // Conversion from Priority type to String type - extract to database
    public static String priorityToString(Priority priority){
        // Każda klasa ENUM posiada metodę name(), która zwraca nazwę pola jako string
        return priority == null ? null : priority.name();
    }



}
