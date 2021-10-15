package com.simple.mcghealth.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {
    public static String getKeyToday() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getStepNumber() {
        return SharedPreferencesUtils.getInstance().get(getKeyToday(), Integer.class);
    }
}
