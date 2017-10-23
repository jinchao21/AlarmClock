package com.example.ljc.alarmclock.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver;
import com.example.ljc.alarmclock.RingActivity;
import com.example.ljc.alarmclock.database.AlarmDataHelper;
import com.example.ljc.alarmclock.model.Alarm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("asd", "startcommand id = " + 2222);
        bundle = intent.getExtras();
        int id = bundle.getInt("_id");
        if (id != 0) {
            Log.d("asd", "alarm service id = " + Integer.toString(id));
            Intent intentRing = new Intent(this, RingActivity.class);
            intentRing.putExtras(bundle);
            intentRing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRing);
        }


        int rId = getReAlarm();
        setOnceAlarm(rId);

        return super.onStartCommand(intent, flags, startId);
    }

    public int getReAlarm() {

        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        int alarmId = 0;
        long time;
        long atime;//最短闹钟时间
        long ctime = System.currentTimeMillis();  //现在时间

        Log.d("asd", "ctime = " + Long.toString(ctime));
        atime = ctime;
        Log.d("asd", "getReAlarm  =  ok");
        dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
                int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
                int ring = cursor.getInt(cursor.getColumnIndex("ring"));
                int state = cursor.getInt(cursor.getColumnIndex("state"));

                Log.d("asd", "getReAlarm id = " + Integer.toString(id) + " " + Integer.toString(hour) + " " + Integer.toString(minutes) + " " + Integer.toString(daysofweek) + " " + Integer.toString(state));
                Calendar calendar = Calendar.getInstance();
                int dk = calendar.get(Calendar.DAY_OF_WEEK);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                time = calendar.getTimeInMillis();

                Log.d("asd", "time = " + Long.toString(time));
                if (state == 1) {
                    if (daysofweek == 0) {
                        if (time > ctime)
                            map.put(id, time);
                        else
                            map.put(id, (time + 7 * 24 * 60 * 60 * 1000));
                        Log.d("asd", "atime 2 = " + Long.toString(atime) + "  " + id);
                    } else {
                        for (int i = 1; i < 8; i++) {
                            if (daysofweek % 10 == 1) {
                                time = time + (8 - i - dk) * 24 * 60 * 60 * 1000;
                                if (time > ctime)
                                    map.put(id, time);
                                else
                                    map.put(id, (time + 7 * 24 * 60 * 60 * 1000));
                                Log.d("asd", "atime 1 = " + Long.toString(atime) + "  " + id);
                                daysofweek = daysofweek / 10;
                            }
                        }
                    }
                }
            } while (cursor.moveToNext());
        }

        long values = System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000;
        for (Integer key : map.keySet()) {
            Long value = map.get(key);
            if (value < values) {
                values = value;
                alarmId = key;
            }
        }
        Log.d("asd", "alarmId  = " + Long.toString(alarmId));
        cursor.close();
        db.close();
        return alarmId;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setOnceAlarm(int id) {
        if (id > 0) {
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("alarms", null, "_id = " + id, null, null, null, null);
            int hour = cursor.getInt(cursor.getColumnIndex("hour"));
            int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
            int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
            int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
            int ring = cursor.getInt(cursor.getColumnIndex("ring"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            cursor.close();
            db.close();

            Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
            intent.setAction("com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver");

            Bundle bundle = new Bundle();
            bundle.putInt("_id", id);
            Log.d("asd", "S new alarm id = " + Integer.toString(id));
            bundle.putInt("hour", hour);
            bundle.putInt("minutes", minutes);
            bundle.putInt("daysofweek", daysofweek);
            bundle.putInt("vibrate", vibrate);
            bundle.putInt("ring", ring);
            bundle.putInt("state", state);

            intent.putExtras(bundle);

            PendingIntent pi = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Log.d("asd", "calendar1 = " + calendar.getTimeInMillis());
            Log.d("asd", "calendar2 = " + System.currentTimeMillis());
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }

}