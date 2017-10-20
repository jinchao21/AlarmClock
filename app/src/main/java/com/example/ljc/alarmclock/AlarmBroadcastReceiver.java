package com.example.ljc.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ljc on 17-10-19.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "闹钟添加成功", Toast.LENGTH_SHORT).show();
        if (action.equals("com.example.ljc.alarmclock.AlarmBroadcastReceiver")){
            Bundle bundle = intent.getExtras();
            int id = bundle.getInt("_id");
//            int hour = bundle.getInt("hour");
//            int minutes = bundle.getInt("minutes");
            Log.d("asd", "broadcast id = " + Integer.toString(id));
            Intent intents = new Intent(context, AlarmService.class);
            intents.putExtras(bundle);
            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intents);
        }
    }
}