package com.example.ljc.alarmclock.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
                "vibrate INTEGER, " +
                "ring INTEGER, " +
                "state INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
