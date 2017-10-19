package com.example.ljc.alarmclock.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ljc.alarmclock.R;
import com.example.ljc.alarmclock.model.Alarm;

import java.util.List;

/**
 * Created by ljc on 17-10-17.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Alarm> alarmList;
    private Alarm alarm;

    public AlarmAdapter(Context context, List<Alarm> alarmList){
        this.alarmList = alarmList;
        this.mInflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
//        String[] days = {"一", "二", "三", "四", "五", "六", "天"};
//        for (int i=0;i<7;i++){
//            if (alarm.daysofweek % 10 == 1)
//                s5 = days[6-i] + " " + s5;
//
//            alarm.daysofweek = alarm.daysofweek/10;
//        }
        holder.repeat.setText(alarm.getDaysofweek());
        holder.alarmState.setOnCheckedChangeListener(null);
        holder.alarmState.setChecked(alarm.isState());
        return convertView;
    }

    class ViewHolder{
        TextView alarmTime;
        TextView ringOrvibrate;
        TextView repeat;
        Switch alarmState;
    }
}
