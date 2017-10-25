package com.example.ljc.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver;
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
        setContentView(R.layout.activity_newalarm);

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

        /*
        * 使用七位数表示闹钟重复状态从1至7位分别对应星期一至星期天
        * 如1111111表示每天都响
        * 0表示只响一次
        * 1000000表示只周一重复
        * */
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

        //点击取消返回主界面
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击保存，先将数据写入数据库，而后设置闹钟，返回主界面
        btSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
                setOnceAlarm();
                finish();
            }
        });
    }


    //设置一次闹钟，重复闹钟也只设置一次，取最近一次闹钟设置，之后其他闹钟会在service中进行设置
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setOnceAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null, null, null);
        cursor.moveToLast();
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        db.close();

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.setAction("com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver");

        //使用intent传递bundle数据
        Bundle bundle = new Bundle();
        bundle.putInt("_id", id);
        Log.d("asd", "new alarm id = " + Integer.toString(id));
        bundle.putInt("hour", hour);
        bundle.putInt("minutes", minutes);
        bundle.putInt("daysofweek", daysofweek);
        bundle.putInt("vibrate", vibrate);
        bundle.putInt("ring", ring);
        bundle.putInt("state", state);

        intent.putExtras(bundle);

        //延时广播
        PendingIntent pi = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d("asd", "calendar1 = " + calendar.getTimeInMillis());
        Log.d("asd", "calendar2 = " + System.currentTimeMillis());

        long atime = System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000;
        long time = calendar.getTimeInMillis();
        long ctime = System.currentTimeMillis();
        int dk = calendar.get(Calendar.DAY_OF_WEEK);
        if (dk > 2)
            dk = dk - 1;
        else
            dk = 7;
        if (daysofweek == 0) {
            if (time <= ctime)
                atime = time + 7 * 24 * 60 * 60 * 1000;
            else
                atime = time;
        } else {
            for (int i = 1; i < 8; i++) {
                if (daysofweek % 10 == 1) {
                    long time1 = time;
                    time1 = time1 + (8 - i - dk) * 24 * 60 * 60 * 1000;
                    if (time1 <= ctime) {
                        time1 = time1 + 7 * 24 * 60 * 60 * 1000;
                        if (time1 <= atime)
                            atime = time1;
                    } else if (time1 <= atime)
                        atime = time1;
                }
                daysofweek = daysofweek / 10;
                Log.d("asd", "daysofweek1 = " + daysofweek);
            }
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, atime, pi);
        Toast.makeText(this, "闹钟添加成功", Toast.LENGTH_SHORT).show();
    }
}
