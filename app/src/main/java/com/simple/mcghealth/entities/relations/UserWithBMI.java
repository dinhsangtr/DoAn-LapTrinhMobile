package com.simple.mcghealth.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.simple.mcghealth.entities.Bmi;
import com.simple.mcghealth.entities.User;

import java.util.List;

public class UserWithBMI {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "Id",
            entityColumn = "idUser",
            entity = Bmi.class
    )
    public List<Bmi> bmiList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
