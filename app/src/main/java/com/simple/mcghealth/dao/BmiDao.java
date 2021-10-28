package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.simple.mcghealth.entities.Bmi;

@Dao
public interface BmiDao {

    //
    @Insert
    void insertBMI(Bmi bmi);

    //Xóa 1 BMI theo Id
    @Query("DELETE FROM bmi WHERE id = :id")
    void deleteBMI(int id);

    //Xóa hết danh sách BMI của USER khi Xóa USER
    @Query("DELETE FROM bmi WHERE idUser = :idUser")
    void deleteAllBMIByUser(int idUser);

    //Lấy bmi mới nhất để hiển thị
    @Query("SELECT * FROM bmi WHERE idUser =:idUser ORDER BY id DESC LIMIT 1")
    Bmi getBMILast(int idUser);


    @Transaction
    @Query("DELETE FROM bmi ")
    void deleteAllBmi();


}
