package com.simple.mcghealth.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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

    private String date;
    private int step;
    private int idUser;


}
