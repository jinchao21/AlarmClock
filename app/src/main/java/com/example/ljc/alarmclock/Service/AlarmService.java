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
import android.widget.Toast;

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
        bundle = intent.getExtras();
        int id = bundle.getInt("_id");
        if (id != 0) {
//            Log.d("asd", "alarm service id = " + Integer.toString(id));
            Intent intentRing = new Intent(this, RingActivity.class);
            intentRing.putExtras(bundle);
            intentRing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRing);
        }


        int rId = getReAlarm();
//        Log.d("asd", "getReAlarm  =  ok" + rId);
        setOnceAlarm(rId);

        return super.onStartCommand(intent, flags, startId);
    }

    public int getReAlarm() {

        Map<Integer, Long> map = new HashMap<Integer, Long>();
        int alarmId = 0;
        long time;
        long atime = System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000; //最短闹钟时间
        long ctime = System.currentTimeMillis();  //现在时间

        Log.d("asd", "ctime = " + Long.toString(ctime));
//        Log.d("asd", "getReAlarm  =  ok");
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

//                Log.d("asd", "getReAlarm id = " + Integer.toString(id) + " " + Integer.toString(hour) + " " + Integer.toString(minutes) + " " + Integer.toString(daysofweek) + " " + Integer.toString(state));
                Calendar calendar = Calendar.getInstance();
                int dk = calendar.get(Calendar.DAY_OF_WEEK); //Calendar.DAY_OF_WEEK需要根据本地化设置的不同而确定是否需要 “-1”
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                time = calendar.getTimeInMillis();

                Log.d("asd", "time = " + Long.toString(time));
                if (state == 1) {  //状态是否为开
                    if (daysofweek == 0) {    //是否重复闹钟
                        if (time <= ctime) {  //不是重复闹钟，并且时间小于当前时间
                            time = time + 7 * 24 * 60 * 60 * 1000;
                            if (time < atime) {  //时间是否小于目标时间
                                atime = time;
                                alarmId = id;
                            }
                        } else {
                            if (time < atime) {    //不是重复闹钟，时间大于当前时间且时间小于目标时间
                                atime = time;
                                alarmId = id;
                            }
                        }
                        Log.d("asd", "atime  = " + Long.toString(atime) + " daysofweek = " + daysofweek + " id = " + id);
                    } else {    //是重复闹钟
                        for (int i = 1; i < 8; i++) {   //用1111111 表示相应星期几闹钟状态，1表示当天有闹钟，0则表示无
                            if (daysofweek % 10 == 1) {  //取余判断当天是否有闹钟
                                long time1 = time;  //设置time1 为临时变量，防止time时间改变
                                time1 = time1 + (8 - i - dk + 1) * 24 * 60 * 60 * 1000;  //8-i 表示星期几 当i=5时，表示星期三，dk表示当前星期几
                                if (time1 < ctime) { //如果时间小于当前时间则为下星期提醒
                                    time1 = time1 + 7 * 24 * 60 * 60 * 1000;
                                    if (time1 < atime) { //并且时间小于目标时间
                                        atime = time1;
                                        alarmId = id;
                                    }
                                } else {//时间大于当前时间，并且时间小于目标时间
                                    if (time1 < atime) {
                                        atime = time1;
                                        alarmId = id;
                                    }
                                }
                            }

                            Log.d("asd", "atime  = " + Long.toString(atime) + " daysofweek = " + daysofweek + " id = " + id + " i = " + i);
                            daysofweek = daysofweek / 10;//获取前一天是否有闹钟
                        }
                    }
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d("asd", "alarmId  = " + Long.toString(alarmId));

        return alarmId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setOnceAlarm(int id) {
        Log.d("asd", "setOnceAlarm  =  ok" + id);
        if (id > 0) {
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("alarms", null, "_id = " + id, null, null, null, null);

            if (null == cursor) {
                Toast.makeText(this, "闹钟出错", Toast.LENGTH_SHORT).show();
            }
            if (cursor.moveToFirst()) {
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
                int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
                int ring = cursor.getInt(cursor.getColumnIndex("ring"));
                int state = cursor.getInt(cursor.getColumnIndex("state"));
                Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
                intent.setAction("com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver");

                Bundle bundle = new Bundle();
                bundle.putInt("_id", id);
//                Log.d("asd", "new alarm id = " + Integer.toString(id));
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

                long atime = System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000;
                long time = calendar.getTimeInMillis();
                long ctime = System.currentTimeMillis();
                int dk = calendar.get(Calendar.DAY_OF_WEEK);
                if (daysofweek == 0) {
                    if (time <= ctime)
                        atime = time + 7 * 24 * 60 * 60 * 1000;
                    else
                        atime = time;
                } else {
                    for (int i = 1; i < 8; i++) {
                        if (daysofweek % 10 == 1) {
                            long time1 = time;
                            time1 = time1 + (8 - i - dk + 1) * 24 * 60 * 60 * 1000;
                            if (time1 <= ctime) {
                                time1 = time1 + 7 * 24 * 60 * 60 * 1000;
                                if (time1 <= atime)
                                    atime = time1;
                            } else if (time1 <= atime)
                                atime = time1;
                        }
                        daysofweek = daysofweek / 10;
                    }
                }
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, atime, pi);
            }
            cursor.close();
            db.close();
        }
    }
}