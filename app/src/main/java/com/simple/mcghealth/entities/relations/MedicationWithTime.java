package com.simple.mcghealth.entities.relations;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.Medicine;

import java.util.List;

public class MedicationWithTime {
    @Embedded public Medicine medicine;
    @Relation(
            parentColumn = "id",
            entityColumn = "medicineId",
            entity = MedicationTime.class
    )
    public List<MedicationTime> medicationTimeList;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
