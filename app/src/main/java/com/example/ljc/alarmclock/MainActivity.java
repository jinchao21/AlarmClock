package com.example.ljc.alarmclock;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ljc.alarmclock.adapter.AlarmAdapter;
import com.example.ljc.alarmclock.database.AlarmDataHelper;
import com.example.ljc.alarmclock.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView ymdw;
    private Button button;
    private ListView listView;


    private AlarmAdapter alarmAdapter;
    private AlarmDataHelper dbHelper;
    private List<Alarm> alarmList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ymdw = (TextView) findViewById(R.id.ymdw);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);


        Handler mTimeHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    long time=System.currentTimeMillis();
                    Date date=new Date(time);
                    SimpleDateFormat sdformat=new SimpleDateFormat("yyyy年MM月dd日 \nHH时mm分ss秒 \nEEEE");
                    ymdw.setText(sdformat.format(date));
                    sendEmptyMessageDelayed(0, 0);
                }
            }
        };

        mTimeHandler.sendEmptyMessageDelayed(0, 0);


        dbHelper = new AlarmDataHelper(this, "alarm.db", null, 1);
        dbHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAlarm.class);
                startActivity(intent);

            }
        });

        initListData();
    }

    public void initListData(){
//
//        public Alarm(int hour, int minutes, int daysofweek, boolean vibrate, boolean ring, boolean state) {
//            this.hour = hour;
//            this.minutes = minutes;
//            this.daysofweek = daysofweek;
//            this.vibrate = vibrate;
//            this.ring = ring;
//            this.state = state;
//        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null,null, null);

//        db.execSQL("CREATE TABLE alarms (" +
//                "_id INTEGER PRIMARY KEY," +
//                "hour INTEGER, " +
//                "minutes INTEGER, " +
//                "daysofweek INTEGER, " +
//                "vibrate INTEGER, " +
//                "ring INTEGER, " +
//                "state INTEGER);");

        if (cursor.moveToNext()){
            do {
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
                int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
                int ring = cursor.getInt(cursor.getColumnIndex("ring"));
                int state = cursor.getInt(cursor.getColumnIndex("state"));
                Alarm alarm = new Alarm(hour, minutes, daysofweek, vibrate==1?true:false, ring==1?true:false, state==1?true:false);
                alarmList.add(alarm);
            }while (cursor.moveToNext());
        }
        cursor.close();
        alarmAdapter = new AlarmAdapter(this, alarmList);
        listView.setAdapter(alarmAdapter);
//        Alarm alarm1 = new Alarm(1, 2, 1111011, true, false, false);
//        alarmList.add(alarm1);
//        Alarm alarm2 = new Alarm(11, 32, 1001011, true, true, false);
//        alarmList.add(alarm2);
//        Alarm alarm3 = new Alarm(1, 2, 11011, true, false, false);
//        alarmList.add(alarm3);
//        Alarm alarm4 = new Alarm(1, 2, 11, false, true, true);
//        alarmList.add(alarm4);
//        Alarm alarm5 = new Alarm(1, 2, 1111011, true, false, false);
//        alarmList.add(alarm5);
//        Alarm alarm6 = new Alarm(1, 2, 1010011, false, true, false);
//        alarmList.add(alarm6);
//        Alarm alarm7 = new Alarm(1, 2, 1011011, false, false, true);
//        alarmList.add(alarm7);
//        Alarm alarm11 = new Alarm(1, 2, 1111011, true, false, false);
//        alarmList.add(alarm11);
//        Alarm alarm12 = new Alarm(11, 32, 1001011, true, true, false);
//        alarmList.add(alarm12);
//        Alarm alarm13 = new Alarm(1, 2, 11011, true, false, false);
//        alarmList.add(alarm13);
//        Alarm alarm14 = new Alarm(4, 2, 11, false, true, true);
//        alarmList.add(alarm14);
//        Alarm alarm15 = new Alarm(5, 2, 1111011, true, false, true);
//        alarmList.add(alarm15);
//        Alarm alarm16 = new Alarm(9, 2, 1010011, false, true, false);
//        alarmList.add(alarm16);
//        Alarm alarm17 = new Alarm(3, 2, 1011011, false, false, false);
//        alarmList.add(alarm17);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListData();
    }
}
