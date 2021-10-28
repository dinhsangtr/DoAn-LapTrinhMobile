package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.simple.mcghealth.entities.Medicine;

import java.util.List;

@Dao
public interface MedicineDao {
    @Insert
    void insertMedicine(Medicine medicine);

    @Query("UPDATE medicine SET name = :name, note = :note, from_date = :from, to_date = :to WHERE id = :id")
    void updateMedicine(int id, String name, String note, String from, String to);

    @Query("UPDATE medicine SET note = :note, from_date = :from, to_date = :to WHERE id = :id")
    void updateMedicine(int id, String note, String from, String to);

    @Query("UPDATE medicine SET name = :name WHERE id = :id")
    void updateMedicine(int id, String name);

    //Cập nhật trong trang setup - dialog
    @Query("UPDATE medicine SET name = :name, image = :image WHERE id = :id")
    void updateMedicine(int id, String name, byte[] image);

    @Delete
    void deleteMedicine(Medicine medicine);

    //ORDER BY name DESC
    @Transaction
    @Query("SELECT * FROM medicine")
    List<Medicine> getAllMedicines();

    @Query("SELECT * FROM medicine WHERE id = :id")
    Medicine findMedicine(int id);

    @Transaction
    @Query("DELETE FROM medicine")
    void deleteAllMedicine();
}
