package com.simple.mcghealth.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "walking_step")
public class WalkingStep {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int step;
    private int target;
    private String date;

    public WalkingStep(int step, int target, String date) {
        this.step = step;
        this.target = target;
        this.date = date;
    }


}
