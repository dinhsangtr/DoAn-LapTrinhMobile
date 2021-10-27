package com.simple.mcghealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
    @Delete
    void delete(User medicine);

    @Query("SELECT * FROM user ORDER BY Id DESC")
    List<User> getAll();
    @Query("SELECT * FROM user Order By id ASC LIMIT 1")
    public User getTOP1() ;
}
