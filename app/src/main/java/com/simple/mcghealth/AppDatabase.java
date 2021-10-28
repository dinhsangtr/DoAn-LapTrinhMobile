package com.simple.mcghealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.simple.mcghealth.dao.BmiDao;
import com.simple.mcghealth.dao.MedicationTimeDao;
import com.simple.mcghealth.dao.MedicineDao;
import com.simple.mcghealth.dao.UserDao;
import com.simple.mcghealth.dao.WalkingStepDao;
import com.simple.mcghealth.entities.Bmi;
import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.entities.WalkingStep;

@Database(entities = {Medicine.class, MedicationTime.class, WalkingStep.class, User.class, Bmi.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "database.db";

    public abstract MedicineDao medicineDao();
    public abstract MedicationTimeDao medicationTimeDao();
    //public abstract WalkingStep walkingStepDao();
    public abstract UserDao userDao();
    public abstract BmiDao bmiDao();
    //public abstract WalkingStepDao walkingStepDao();
    //


    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
