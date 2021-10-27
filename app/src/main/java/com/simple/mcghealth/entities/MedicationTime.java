package com.simple.mcghealth.entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "medication_time")
public class MedicationTime {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int medicineId;

    @Nullable
    public String session;

    @Nullable
    public int count;

    @Nullable
    @ColumnInfo(name = "time_drink")
    public String timeDrink;

    @Nullable
    public int status;

    public MedicationTime(int medicineId, @Nullable String session, int count, @Nullable String timeDrink, int status) {
        this.medicineId = medicineId;
        this.session = session;
        this.count = count;
        this.timeDrink = timeDrink;
        this.status = status;
    }
}
