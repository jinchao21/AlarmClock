package com.example.ljc.alarmclock;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ljc.alarmclock.adapter.AlarmAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private TextView ymdw;
    private Button button;
    private ListView listView;


    private AlarmAdapter alarmAdapter;

    private String mYear;
    private String mMonth;
    private String mDay;
    private String mWay;


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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAlarm.class);
                startActivity(intent);

            }
        });


        listView.setAdapter(alarmAdapter);
    }

}
