package com.simple.mcghealth.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "medicine")
public class Medicine {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "from_date")
    public String fromDate;

   // @TypeConverters({TimeStampConverter.class})
    @ColumnInfo(name = "to_date")
    public String toDate;

}
