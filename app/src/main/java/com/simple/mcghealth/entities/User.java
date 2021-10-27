package com.simple.mcghealth.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "user")
public class User{
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String FullName;
    public String BirthDay;
    public int Height;
    public int Weight;

    public User(String fullName, String birthDay, int height, int weight) {

        FullName = fullName;
        BirthDay = birthDay;
        Height = height;
        Weight = weight;
    }
}