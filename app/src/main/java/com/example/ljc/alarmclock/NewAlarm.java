package com.example.ljc.alarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ljc.alarmclock.database.AlarmDataHelper;

import java.util.Calendar;

/**
 * Created by ljc on 17-10-16.
 */

public class NewAlarm extends AppCompatActivity {

    private TimePicker timePicker;
    private CheckBox CbMonday;
    private CheckBox CbTuesday;
    private CheckBox CbWednesday;
    private CheckBox CbThursday;
    private CheckBox CbFriday;
    private CheckBox CbSaturday;
    private CheckBox CbSunday;
    private Switch SwVibrate;
    private Switch SwRing;
    private Button btCancel;
    private Button btSave;

    private int hour;
    private int minutes;
    private int daysofweek = 0;
    private int vibrate;
    private int ring;
    private int state = 1;

    private AlarmDataHelper dbHelper;

    public NewAlarm() {
    }

//    Monday 星期一 Tuesday 星期二 Wednesday 星期三 Thursday 星期四 Friday 星期五 Saturday 星期六 Sunday 星期日

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newalarm);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        CbMonday = (CheckBox) findViewById(R.id.monday);
        CbTuesday = (CheckBox) findViewById(R.id.tuesday);
        CbWednesday = (CheckBox) findViewById(R.id.wednesday);
        CbThursday = (CheckBox) findViewById(R.id.thursday);
        CbFriday = (CheckBox) findViewById(R.id.friday);
        CbSaturday = (CheckBox) findViewById(R.id.saturday);
        CbSunday = (CheckBox) findViewById(R.id.sunday);
        SwVibrate = (Switch) findViewById(R.id.vibrate);
        SwRing = (Switch) findViewById(R.id.ring);
        btCancel = (Button) findViewById(R.id.cancel);
        btSave = (Button) findViewById(R.id.save);

        dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                NewAlarm.this.hour = hourOfDay;
                NewAlarm.this.minutes = minute;
                Log.d("asd", "time  " + Integer.toString(NewAlarm.this.hour) + ":" + Integer.toString(NewAlarm.this.minutes));
            }
        });


        CbMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 1000000;
                else
                    daysofweek = daysofweek - 1000000;
                Log.d("asd", "1  " + Integer.toString(daysofweek));
            }
        });
        CbTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 100000;
                else
                    daysofweek = daysofweek - 100000;
                Log.d("asd", "2  " + Integer.toString(daysofweek));
            }
        });
        CbWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 10000;
                else
                    daysofweek = daysofweek - 10000;
                Log.d("asd", "3  " + Integer.toString(daysofweek));
            }
        });
        CbThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 1000;
                else
                    daysofweek = daysofweek - 1000;
                Log.d("asd", "4  " + Integer.toString(daysofweek));
            }
        });
        CbFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 100;
                else
                    daysofweek = daysofweek - 100;
                Log.d("asd", "5  " + Integer.toString(daysofweek));
            }
        });
        CbSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 10;
                else
                    daysofweek = daysofweek - 10;
                Log.d("asd", "6  " + Integer.toString(daysofweek));
            }
        });
        CbSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    daysofweek = daysofweek + 1;
                else
                    daysofweek = daysofweek - 1;
                Log.d("asd", "7  " + Integer.toString(daysofweek));
            }
        });


        SwRing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ring = 1;
                else
                    ring = 0;
                Log.d("asd", "ring  " + Integer.toString(ring));
            }
        });

        SwVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    vibrate = 1;
                else
                    vibrate = 0;
                Log.d("asd", "vibrate  " + Integer.toString(vibrate));
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("hour", hour);
                values.put("minutes", minutes);
                values.put("daysofweek", daysofweek);
                values.put("vibrate", vibrate);
                values.put("ring", ring);
                values.put("state", state);
                db.insert("alarms", null, values);
                Toast.makeText(NewAlarm.this, "闹钟添加成功", Toast.LENGTH_SHORT).show();
                setOnceAlarm();
                finish();
            }
        });
    }


    public void setOnceAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.setAction("com.example.ljc.alarmclock.AlarmBroadcastReceiver");

        Bundle bundle = new Bundle();
        bundle.putInt("hour", hour);
        bundle.putInt("minutes", minutes);
        bundle.putInt("daysofweek", daysofweek);
        bundle.putInt("vibrate", vibrate);
        bundle.putInt("ring", ring);
        bundle.putInt("state", state);

        intent.putExtras(bundle);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

//    Intent alarmIntent = new Intent(context, AlarmClockReceiver.class);
//                alarmIntent.setAction("cn.edu.usts.cardhelper.alarmclock");
//    Bundle bundle = new Bundle();
//                bundle.putInt("alarmClockId", clockInfo.getId());
//                bundle.putString("repeatCycle",clockInfo.getRepeatCycle());
//                bundle.putInt("hour", clockInfo.getHour());
//                bundle.putInt("minute", clockInfo.getMinute());
//                bundle.putString("ringInfo", clockInfo.getRingInfo());
//                bundle.putInt("isShake", clockInfo.getShake());
//                bundle.putString("tag", clockInfo.getTag());
//                alarmIntent.putExtras(bundle);
//    PendingIntent pi = PendingIntent.getBroadcast(context, clockInfo.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    int dTime = NextRingTimeProvider.showRingTimeByRepeatCycle(clockInfo.getRepeatCycle(), clockInfo.getHour(), clockInfo.getMinute());
//                if(clockInfo.getRepeatCycle().equals(Alarm_CLOCK_RING_ONLY_ONCE)){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, clockInfo.getHour());
//        calendar.set(Calendar.MINUTE, clockInfo.getMinute());
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        if(System.currentTimeMillis() < calendar.getTimeInMillis()){
//            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+dTime, pi);
//            Log.i(TAG, "--重启广播发送成功！  只响一次的闹钟时间未到，广播已发送--闹钟ID:"+clockInfo.getId()+"--repeatCycle"+clockInfo.getRepeatCycle()+"--hour:"+clockInfo.getHour()+"---minute:"+clockInfo.getMinute()+"---铃声为："+clockInfo.getRingInfo()+"---振动："+clockInfo.getShake()+"---tag:"+clockInfo.getTag());
//        }
//    }else{
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+dTime, pi);
//        Log.i(TAG, "--重启广播发送成功！  --闹钟ID:"+clockInfo.getId()+"--repeatCycle"+clockInfo.getRepeatCycle()+"--hour:"+clockInfo.getHour()+"---minute:"+clockInfo.getMinute()+"---铃声为："+clockInfo.getRingInfo()+"---振动："+clockInfo.getShake()+"---tag:"+clockInfo.getTag());

    }
