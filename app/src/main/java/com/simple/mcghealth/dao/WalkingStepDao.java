package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.simple.mcghealth.entities.WalkingStep;

import java.util.List;

@Dao
public interface WalkingStepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WalkingStep walkingStep);

    @Delete
    void delete(WalkingStep walkingStep);

    @Delete
    void reset(WalkingStep walkingStep);

    @Query("SELECT * FROM walking_step ORDER BY date DESC")
    List<WalkingStep> getAllData();
}
