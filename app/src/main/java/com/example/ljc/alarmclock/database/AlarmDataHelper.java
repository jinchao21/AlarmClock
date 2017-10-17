package com.example.ljc.alarmclock.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ljc on 17-10-17.
 */

public class AlarmDataHelper extends SQLiteOpenHelper {
    public AlarmDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE alarms (" +
                "_id INTEGER PRIMARY KEY," +
                "hour INTEGER, " +
                "minutes INTEGER, " +
                "daysofweek INTEGER, " +
                "alarmtime INTEGER, " +
                "vibrate INTEGER, " +
                "state INTEGER);");
        // @formatter:on
        // insert default alarms
        String insertMe = "INSERT INTO alarms " + "(hour, minutes, daysofweek, alarmtime, vibrate, state) VALUES ";
        db.execSQL(insertMe + "(8, 30, 10001, 0, 1, 0);");
        db.execSQL(insertMe + "(9, 00, 11011, 0, 1, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
