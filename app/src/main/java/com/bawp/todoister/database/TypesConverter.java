package com.bawp.todoister.database;

import android.provider.ContactsContract;

import androidx.room.TypeConverter;

import com.bawp.todoister.model.Priority;

import java.util.Date;

//  Klasa użyta w celu konwersji typów które są nieobsługiwane przez Room Database
// (np. Date, Priority) na typy które baza danych identyfikuje (Long, String)
public class TypesConverter {

    // Konwersja z typu bazodanowego do typu daty - wyciągamy z bazy danych
    @TypeConverter
    public static Date timestampToDate(Long value){
        return value == null ? null : new Date(value);
    }

    // Konwersja z daty do typu bazodanowego - wrzucamy do bazy danych
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    // Konwersja z typu bazodanowego do typu Priority - wyciągamy z bazy danych
    @TypeConverter
    public static Priority stringToPriority(String value){
        return value == null ? null : Priority.valueOf(value);
    }

    @TypeConverter
    // Konwersja z typu Priority do typu bazodanowego - wrzucamy do bazy canych
    public static String priorityToString(Priority priority){
        // Każda klasa ENUM posiada metodę name(), która zwraca nazwę pola jako string
        return priority == null ? null : priority.name();
    }



}
