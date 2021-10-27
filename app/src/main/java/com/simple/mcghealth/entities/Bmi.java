package com.simple.mcghealth.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "bmi")
public class Bmi {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public float height;
    public float weight;
    public String date;
    public int idUser;

    public Bmi(int id, float height, float weight, String date, int idUser) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.date = date;
        this.idUser = idUser;
    }
}
