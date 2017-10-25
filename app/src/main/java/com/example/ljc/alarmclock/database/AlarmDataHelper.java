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

    /*id主键
    * hour小时 （24小时制）
    * minutes 分钟
    * daysofweek 每周重复
    * vibrate 振动状态
    * ring 响铃状态
    * state 闹钟状态
    *
    * 因SQLite中不能存储boolean类型，故用Int类型代替之，并用双目运算？：进行转换
    * */
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
