package com.example.ljc.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ljc on 17-10-19.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.example.ljc.alarmclock.AlarmBroadcastReceiver")){
            Bundle bundle = intent.getExtras();
//            int id = bundle.getInt("_id");
//            int hour = bundle.getInt("hour");
//            int minutes = bundle.getInt("minutes");
            Intent intents = new Intent(context, AlarmService.class);
            intents.putExtras(bundle);
            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intents);
        }
    }
}
//public class AlarmClockReceiver extends BroadcastReceiver {
//    private static final String TAG = "AlarmClockReceiver";
//    public static final String Alarm_CLOCK_RING_ONLY_ONCE = "只响一次";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        String action = intent.getAction();
//        if(action.equals("cn.edu.usts.cardhelper.alarmclock")){
//            Log.i(TAG, "--------------------闹钟的广播接受者-----------------------");
//            Bundle bundle = intent.getExtras();
//            int alarmClockId = bundle.getInt("alarmClockId");
//            int hour = bundle.getInt("hour");
//            int minute = bundle.getInt("minute");
//            String repeatCycle = bundle.getString("repeatCycle");
//            String ringInfo = bundle.getString("ringInfo");
//            int isShake = bundle.getInt("isShake");
//            String tag = bundle.getString("tag");
//            Log.i(TAG, "---接收到的闹钟id为："+alarmClockId+"---重复周期为："+repeatCycle+"---铃声信息："+ringInfo+"---振动："+isShake+"---hour："+hour+"---minute："+minute+"---tag:"+tag);
//
//            //开启一个服务
//            Intent ringIntent = new Intent(context, AlarmClockService.class);
//            ringIntent.putExtras(bundle);
//            ringIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startService(ringIntent);
//
//        }
//
//    }
//}