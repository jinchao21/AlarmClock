package com.example.ljc.alarmclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy年MM月dd日 \nHH时mm分ss秒 \nEEEE");
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("选项")
                        .setMessage("删除闹钟")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = alarmList.get(position).id;
                                deleteAlarm(id);
                                initListData();
                                Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;
            }
        });


    }


    public void deleteAlarm(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("alarms", "_id =" + id, null);
    }

    public void initListData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("alarms", null, null, null, null, null, null);

        alarmList.removeAll(alarmList);
        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                int hour = cursor.getInt(cursor.getColumnIndex("hour"));
                int minutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                int daysofweek = cursor.getInt(cursor.getColumnIndex("daysofweek"));
                int vibrate = cursor.getInt(cursor.getColumnIndex("vibrate"));
                int ring = cursor.getInt(cursor.getColumnIndex("ring"));
                int state = cursor.getInt(cursor.getColumnIndex("state"));
                Alarm alarm = new Alarm(id, hour, minutes, daysofweek, vibrate == 1 ? true : false, ring == 1 ? true : false, state == 1 ? true : false);
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        alarmAdapter = new AlarmAdapter(this, alarmList);
        listView.setAdapter(alarmAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListData();
    }
}
