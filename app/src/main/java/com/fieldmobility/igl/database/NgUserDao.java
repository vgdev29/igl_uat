package com.fieldmobility.igl.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fieldmobility.igl.Model.NguserListModel;
import java.util.List;

@Dao
public interface NgUserDao {
    @Query("SELECT * FROM ng_users_table")
    List<NguserListModel> getAllUsers();

    @Query("SELECT * FROM ng_users_table WHERE status LIKE :DP")
    List<NguserListModel> getDPUsers(String DP);

    @Query("SELECT * FROM ng_users_table WHERE status LIKE :OP")
    List<NguserListModel> getOPUsers(String OP);

    @Insert
    void insert(NguserListModel user);

    @Delete
    void delete(NguserListModel user);

    @Query("DELETE FROM ng_users_table")
    public void deleteAll();

    @Query("DELETE FROM ng_users_table WHERE jmr_no IN (:jmr_nos)")
    public void deleteUserByJmrNos( List<String> jmr_nos);

    @Update
    void update(NguserListModel user);
}
