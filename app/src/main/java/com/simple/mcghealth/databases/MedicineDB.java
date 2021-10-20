package com.simple.mcghealth.databases;

/*
@Database(entities = {Medicine.class}, version = 1, exportSchema = true)
public abstract class MedicineDB extends RoomDatabase {

    public abstract MedicineDao medicineDao();

    private static MedicineDB INSTANCE;

    private static final String DATABASE_NAME = "database.db";

    public static MedicineDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MedicineDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
*/