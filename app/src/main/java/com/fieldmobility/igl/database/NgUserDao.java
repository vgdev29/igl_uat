package com.fieldmobility.igl.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fieldmobility.igl.Model.NguserListModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NgUserDao {
    @Query("SELECT * FROM ng_users_table")
    List<NguserListModel> getAllUsers();

    @Insert
    void insert(NguserListModel user);

    @Delete
    void delete(NguserListModel user);

    @Update
    void update(NguserListModel user);
}
