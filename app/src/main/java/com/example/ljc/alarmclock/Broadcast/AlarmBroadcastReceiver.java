package com.example.ljc.alarmclock.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ljc.alarmclock.Service.AlarmService;

/**
 * Created by ljc on 17-10-19.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.example.ljc.alarmclock.Broadcast.AlarmBroadcastReceiver")){
            Bundle bundle = intent.getExtras();
//            int id = bundle.getInt("_id");
//            Log.d("asd", "broadcast id = " + Integer.toString(id));
            Intent intents = new Intent(context, AlarmService.class);
            intents.putExtras(bundle);
            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intents);
        }
    }
}