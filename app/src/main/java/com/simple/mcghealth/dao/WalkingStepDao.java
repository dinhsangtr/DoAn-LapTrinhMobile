package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.simple.mcghealth.entities.WalkingStep;

import java.util.List;

@Dao
public interface WalkingStepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WalkingStep walkingStep);

    @Delete
    void delete(WalkingStep walkingStep);

    @Query("DELETE FROM WALKING_STEP ")
    void deleteAllWalking_Steps();

    @Transaction
    @Query("SELECT * FROM walking_step ORDER BY date DESC")
    List<WalkingStep> getAll();
}
