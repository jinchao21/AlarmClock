package com.example.ljc.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by ljc on 17-10-19.
 */

public class RingActivity extends Activity{
    private Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(this, notification);
        if (ringtone != null && !ringtone.isPlaying()) {
            ringtone.play();
        }

        new AlertDialog.Builder(RingActivity.this).setTitle("提示")
                .setMessage("闹钟响了～")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ringtone.stop();
                        RingActivity.this.finish();
                    }
                }).show();
    }
}