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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ljc.alarmclock.Service.AlarmService;
import com.example.ljc.alarmclock.adapter.AlarmAdapter;
import com.example.ljc.alarmclock.database.AlarmDataHelper;
import com.example.ljc.alarmclock.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView ymdw;//主界面时间显示
    private Button button;//新建闹钟
    private ListView listView;//闹钟列表


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


        //如应用被关闭，闹钟启动时检查最近闹钟
        Bundle bundle = new Bundle();
        bundle.putInt("_id", 0);
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtras(bundle);
        startService(intent);

        Log.d("asd", "System = " + System.currentTimeMillis());

        //使用Handler更新时间
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

        //闹钟列表长按删除
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


    //数据库执行删除
    public void deleteAlarm(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("alarms", "_id =" + id, null);
    }

    //初始化闹钟列表数据
    //SQLite中不能存储boolean类型，故用Int数据1，0存储对应boolean类型并用双目运算？：判断之
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
//                public Alarm(int id, int hour, int minutes, int daysofweek, boolean vibrate, boolean ring, boolean state)
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        alarmAdapter = new AlarmAdapter(this, alarmList);
        listView.setAdapter(alarmAdapter);
    }

    //闹钟当前界面不可见时，重现则更新，主要针对新建闹钟后返回以及闹钟响铃后状态改变
    @Override
    protected void onResume() {
        super.onResume();
        initListData();
    }
}
