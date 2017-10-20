package com.example.ljc.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ljc.alarmclock.database.AlarmDataHelper;
import com.example.ljc.alarmclock.model.Alarm;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ljc on 17-10-19.
 */

public class AlarmService extends Service {

    private AlarmManager alarmManager;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Bundle bundle;
    private Intent intent;
    private AlarmDataHelper dbHelper;
    private List<Alarm> alarmList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bundle = intent.getExtras();
        int id = bundle.getInt("_id");
        Log.d("asd", "alarm service id = " + Integer.toString(id));
        Intent intentRing = new Intent(this, RingActivity.class);
        intentRing.putExtras(bundle);
        intentRing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentRing);

        dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null, null, null);
        ContentValues values = new ContentValues();
        values.put("state", 0);
        db.update("alarms", values, "_id =" + id, null);

        getReAlarm();
        cursor.close();
        db.close();

        return super.onStartCommand(intent, flags, startId);
    }

    public void getReAlarm() {

        dbHelper = new AlarmDataHelper(this, "alarms.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null, null, null);

        int alarmId;
        long time;
        long atime;
        long ctime = System.currentTimeMillis();
        atime = ctime;
        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
                int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
                int ring = cursor.getInt(cursor.getColumnIndex("ring"));
                int state = cursor.getInt(cursor.getColumnIndex("state"));

                Calendar calendar = Calendar.getInstance();
                int dk = calendar.get(Calendar.DAY_OF_WEEK);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                time = calendar.getTimeInMillis();

//                int a = daysofweek;
//                String[] days = {"一", "二", "三", "四", "五", "六", "天", "只响一次"};
//                String s5 = "";
//                if (daysofweek==0) {
//                    return days[7] + state;
//                } else {
//
//                    for (int i = 0; i < 7; i++) {
//                        if (a % 10 == 1)
//                            s5 = days[6 - i] + " " + s5;
//                        a = a / 10;
//                    }
//                    return s5 + state;
//                }
//
                if (state == 1) {
                    for (int i = 1; i < 8; i++) {
                        if (daysofweek % 10 == 1 && i >= dk) {
                            if (time + (i - dk) * 24 * 60 * 60 * 1000 > ctime && atime > time)
                                atime = time + (i - dk) * 24 * 60 * 60 * 1000;
                            Log.d("asd", "atime 1 = " + Long.toString(atime));
                            daysofweek = daysofweek / 10;
                        }else if (daysofweek % 10 == 1 && i < dk){
                            if (time + (7-dk+i) * 24 * 60 * 60 * 1000 > ctime && atime > time)
                                atime = time + (7-dk+i) * 24 * 60 * 60 * 1000;
                            Log.d("asd", "atime 2 = " + Long.toString(atime));
                            daysofweek = daysofweek / 10;
                        }
                    }
                }

                Alarm alarm = new Alarm(id, hour, minutes, daysofweek, vibrate == 1 ? true : false, ring == 1 ? true : false, state == 1 ? true : false);
//                public Alarm(int id, int hour, int minutes, int daysofweek, boolean vibrate, boolean ring, boolean state)
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
//    public void setOnceAlarm() {
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query("alarms", null, null, null, null, null, null);
//        cursor.moveToLast();
//        int id = cursor.getInt(cursor.getColumnIndex("_id"));
//        cursor.close();
//        db.close();
//
//        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
//        intent.setAction("com.example.ljc.alarmclock.AlarmBroadcastReceiver");
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("_id", id);
//        Log.d("asd", "new alarm id = " + Integer.toString(id));
//        bundle.putInt("hour", hour);
//        bundle.putInt("minutes", minutes);
//        bundle.putInt("daysofweek", daysofweek);
//        bundle.putInt("vibrate", vibrate);
//        bundle.putInt("ring", ring);
//        bundle.putInt("state", state);
//
//        intent.putExtras(bundle);
//
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minutes);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
////        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1, pi);
////        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000*30, pi);
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, 20*1000, pi);
//
////        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+);
//    }
}