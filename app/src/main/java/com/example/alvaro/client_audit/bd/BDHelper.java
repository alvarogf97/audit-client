package com.example.alvaro.client_audit.bd;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BDHelper {

    //tDevice table
    public static final String DEVICE_TABLE = "tDevice";
    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_IP = "IP";
    public static final String DEVICE_PORT = "PORT";

    private static final String CREATE_tDevice = "create table " + DEVICE_TABLE
            + "(" +  DEVICE_ID + " integer primary key autoincrement not null, "
            +  DEVICE_NAME + " text not null, "
            +  DEVICE_IP + " text not null unique, "
            +  DEVICE_PORT + " integer not null);";

    public static void CREATE_TABLES(SQLiteDatabase bd){
        Log.d("DBHELPER","create");
        bd.execSQL(CREATE_tDevice);
        Log.d("DBhelper","tabla creada");
    }
}