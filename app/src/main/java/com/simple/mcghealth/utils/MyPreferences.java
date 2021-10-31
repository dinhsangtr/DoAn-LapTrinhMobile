package com.simple.mcghealth.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    static SharedPreferences pref;
    static SharedPreferences.Editor editor;

    //Name prefs
    private static final String SHARED_PREFERENCES_NAME = "my_pref";

    //Key
    private static final String IS_FIRST_TIME_LAUNCH = "is_first_launch";
    private static final String WALKING_STEP = "walking_step";


    public MyPreferences(Context context) {
        pref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    //Intro
    public static void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public static boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    //Walking Step



}
