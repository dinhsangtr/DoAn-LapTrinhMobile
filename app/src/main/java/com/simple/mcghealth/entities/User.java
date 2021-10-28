package com.simple.mcghealth.entities;

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
    public String Gender;
    public String DiseaseInfo;
    public String DiseaseInfoHistory;

    public User(String fullName, String birthDay, String gender) {
        FullName = fullName;
        BirthDay = birthDay;
        Gender = gender;
    }

    public User(int id, String diseaseInfo, String diseaseInfoHistory) {
        Id = id;
        DiseaseInfo = diseaseInfo;
        DiseaseInfoHistory = diseaseInfoHistory;
    }
}
