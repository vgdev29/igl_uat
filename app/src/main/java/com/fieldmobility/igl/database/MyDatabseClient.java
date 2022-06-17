package com.fieldmobility.igl.database;

import android.content.Context;

import androidx.room.Room;

public class MyDatabseClient {
    private Context mCtx;
    private static MyDatabseClient mInstance;

    //our app database object
    private MyDatabse myDatabase;

    private MyDatabseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        myDatabase = Room.databaseBuilder(mCtx, MyDatabse.class, "igl_users").build();
    }

    public static synchronized MyDatabseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new MyDatabseClient(mCtx);
        }
        return mInstance;
    }

    public MyDatabse getAppDatabase() {
        return myDatabase;
    }

}
