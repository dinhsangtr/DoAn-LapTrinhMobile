package com.simple.mcghealth.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String SHARED_PREFERENCES_NAME = "first_launch";

    //
    public static boolean isFirst(Context context) {
        final SharedPreferences reader = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if (first) {
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.apply();
        }
        return first;
    }

}
