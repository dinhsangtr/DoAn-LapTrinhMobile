package com.simple.mcghealth.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.simple.mcghealth.dao.WalkingStepDao;
import com.simple.mcghealth.entities.WalkingStep;

@Database(entities = {WalkingStep.class}, version = 1)
public abstract class WalkingStepDB extends RoomDatabase {
    private static WalkingStepDB db;
    private static final String DATABASE_NAME = "database.db";

    public static synchronized WalkingStepDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), WalkingStepDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract WalkingStepDao mainDAO();
}
