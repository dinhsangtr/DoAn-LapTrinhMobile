package com.simple.mcghealth.utils;

import androidx.room.TypeConverter;

import java.sql.Date;


public class TimeStampConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}