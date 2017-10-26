package com.example.ljc.alarmclock.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver;
import com.example.ljc.alarmclock.R;
import com.example.ljc.alarmclock.Service.AlarmService;
import com.example.ljc.alarmclock.database.AlarmDataHelper;
import com.example.ljc.alarmclock.model.Alarm;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ljc on 17-10-17.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Alarm> alarmList;
    private Alarm alarm;

    private AlarmDataHelper dbHelper;

    private Context context;
    private static final String TAG = "AlarmAdapter";

    public AlarmAdapter(Context context, List<Alarm> alarmList){
        this.context = context;
        this.alarmList = alarmList;
        this.mInflater = LayoutInflater.from(context);
        dbHelper = new AlarmDataHelper(context, "alarm.db", null, 1);
    }

    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        alarm = alarmList.get(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.alarm_item, parent,false);
            holder = new ViewHolder();
            holder.alarmTime = (TextView)convertView.findViewById(R.id.alarmTime);
            holder.ringOrvibrate = (TextView)convertView.findViewById(R.id.ringOrvibrate);
            holder.repeat = (TextView)convertView.findViewById(R.id.repeat);
            holder.alarmState = (Switch)convertView.findViewById(R.id.alarmState);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        String s1 = alarm.getHour()>9 ? Integer.toString(alarm.getHour()) : "0" + Integer.toString(alarm.getHour());
        String s2 = alarm.getMinutes()>9 ? Integer.toString(alarm.getMinutes()) : "0" + Integer.toString(alarm.getMinutes());
        holder.alarmTime.setText(s1 + ":" + s2);
        String s3 = alarm.isRing() ? "响铃  " : "";
        String s4 = alarm.isVibrate() ? "振动" :  "";
        holder.ringOrvibrate.setText((s3 + s4)==""?"只提醒":(s3+s4));

        holder.repeat.setText(alarm.getDaysofweek());
        holder.alarmState.setOnCheckedChangeListener(null);

        holder.alarmState.setChecked(alarm.isState());
        final int id = alarmList.get(position).id;

        //在闹钟状态的Switch上设置监听，根据Switch状态进行数据库读写以及发送闹钟意图
//        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        holder.alarmState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("state", 1);
                    db.update("alarms", values, "_id ="+id,null);
                    db.close();
                    alarmList.get(position).setState(true);
                    Log.d(TAG, "ischecked id = true");

                    Intent intent = new Intent(context, AlarmService.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("_id", 0);
                    intent.putExtras(bundle);
                    context.startService(intent);
                }else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("state", 0);
                    db.update("alarms", values, "_id ="+id,null);
                    db.close();
                    alarmList.get(position).setState(false);

                    Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
//                    intent.setAction("com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver");
                    PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    pi.cancel();
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, pi);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView alarmTime;
        TextView ringOrvibrate;
        TextView repeat;
        Switch alarmState;
    }
}