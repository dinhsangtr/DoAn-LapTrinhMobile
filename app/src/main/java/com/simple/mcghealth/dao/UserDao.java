package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.simple.mcghealth.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    //
    @Insert
    void insertUser(User user);

    //Update thoong tin cow banr
    @Query("UPDATE user SET FullName = :name, BirthDay = :birth, Gender = :gender WHERE Id = :id")
    void updateUserBasic(int id, String name, String birth, String gender);

    //Update thông tin bệnh
    @Query("UPDATE user SET DiseaseInfo = :diseaseNow, DiseaseInfoHistory = :diseaseHistory WHERE Id = :id")
    void updateUserDisease(int id, String diseaseNow, String diseaseHistory);

    //Xóa 1 BMI theo Id
    @Query("DELETE FROM user WHERE Id = :id")
    void deleteUser(int id);

    //getAll
    @Transaction
    @Query("SELECT * FROM user ORDER BY Id")
    List<User> getAllUser();

    //getUser1
    @Transaction
    @Query("SELECT * FROM user ORDER BY Id DESC LIMIT 1")
    User getUserLast();


    //gettop1 user
    @Transaction
    @Query("SELECT * FROM user Order By id ASC LIMIT 1")
    User getTOP1() ;

    @Transaction
    @Query("DELETE FROM user ")
    void deleteAllUser( );
}
