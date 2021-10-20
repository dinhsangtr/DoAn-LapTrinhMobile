package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.simple.mcghealth.entities.Medicine;

import java.util.List;

@Dao
public interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicine(Medicine medicine);

    @Delete
    void delete(Medicine medicine);

    @Query("SELECT * FROM medicine ORDER BY name DESC")
    List<Medicine> getAll();
}
