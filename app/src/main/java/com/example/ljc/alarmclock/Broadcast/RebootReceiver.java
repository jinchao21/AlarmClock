package com.example.ljc.alarmclock.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ljc.alarmclock.Service.AlarmService;

/**
 * Created by ljc on 17-10-23.
 */

public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intents = new Intent(context, AlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("_id", 0);
        intents.putExtras(bundle);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intents);
        Toast.makeText(context, "BOOT_COMPLETED AlarmClock", Toast.LENGTH_SHORT).show();
    }
}
