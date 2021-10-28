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

    @Query("UPDATE walking_step SET step =:step WHERE id= :id")
    void updateStep(int id, int step);

    @Query("UPDATE walking_step SET target =:target WHERE id= :id")
    void updateTarget(int id, int target);

    @Delete
    void delete(WalkingStep walkingStep);


    @Query("DELETE FROM WALKING_STEP ")
    void deleteAllWalking_Steps();

    //@Transaction
    @Query("SELECT * FROM walking_step ORDER BY id DESC")
    List<WalkingStep> getAll();

    @Query("SELECT * FROM walking_step ORDER BY id DESC LIMIT 1")
    WalkingStep getStep();
}
