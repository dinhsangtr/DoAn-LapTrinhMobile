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

    @Query("UPDATE medication_time SET count= :count, time_drink = :time, status = :status WHERE id= :medicationTimeID AND session = :session")
    void updateTime(int medicationTimeID, String session, int count, String time, int status);

    @Query("SELECT * FROM medication_time WHERE id = :id")
    MedicationTime getTime(int id);

    @Query("SELECT * FROM medication_time WHERE medicineId = :id")
    List<MedicationTime> getAllTime(int id);

    @Query("SELECT * FROM medication_time WHERE medicineId = :id AND status == 1")
    List<MedicationTime> getAllTimeOn(int id);


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

