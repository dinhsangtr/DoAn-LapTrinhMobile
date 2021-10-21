package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.relations.MedicationWithTime;

import java.util.List;

@Dao
public interface MedicationTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTime(MedicationTime medicationTime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiTime(List<MedicationTime> medicationTimeList);

    @Query("DELETE FROM medication_time WHERE medicineId = :medicineID")
    void deleteTime(int medicineID);

    @Query("SELECT * FROM medication_time WHERE medicineId = :id")
    List<MedicationTime> getAllTime(int id);





    @Query("SELECT * FROM medicine")
    List<MedicationWithTime> getAllData();

    @Query("SELECT * FROM medicine WHERE id = :medicineID")
    MedicationWithTime getDataById(int medicineID);


/*
    @Transaction
    @Query("SELECT * FROM medicine")
    List<MedicationWithTime> getMedicineWithTimeList();

    @Transaction
    @Query("SELECT * FROM medicine WHERE id = :id")
    List<MedicationWithTime> getMedicineWithTimeList(int id);
*/
}

