package com.simple.mcghealth.entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.simple.mcghealth.utils.TimeStampConverter;

import java.sql.Date;

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

    @Nullable
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    @Nullable
    @ColumnInfo(name = "note")
    public String note;

    @Nullable
    @ColumnInfo(name = "from_date")
    @TypeConverters({TimeStampConverter.class})
    public Date fromDate;

    @Nullable
    @ColumnInfo(name = "to_date")
    @TypeConverters({TimeStampConverter.class})
    public Date toDate;

}
