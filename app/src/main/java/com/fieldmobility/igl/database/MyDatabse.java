package com.fieldmobility.igl.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fieldmobility.igl.Model.NguserListModel;

@Database(entities = {NguserListModel.class},version = 1)
public abstract class MyDatabse extends RoomDatabase {
    public abstract NgUserDao ngUserDao();
}
