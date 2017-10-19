package com.example.ljc.alarmclock;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by ljc on 17-10-19.
 */

public class AlarmService extends Service{

    private AlarmManager alarmManager;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Bundle bundle;
    private Intent intent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}

//public class AlarmClockService extends Service {
//
//    public static final String TAG = "AlarmClockService";
//    public static final String Alarm_CLOCK_RING_ONLY_ONCE = "只响一次";
//    private AlarmManager alarmManager;
//    private MediaPlayer mediaPlayer;
//    private Vibrator vibrator;
//    private Bundle bundle;
//    private Intent toReceiverIntent;
//    @Override
//    public IBinder onBind(Intent intent) {
//        Log.i(TAG, "--------------onBind----------");
//        return new AlarmClockRingServiceProvider();
//    }
//
//    @Override
//    public void onCreate() {
//        Log.i(TAG, "--------------onCreate----------");
//        super.onCreate();
//        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i(TAG, "--------------onStartCommand----------");
//        bundle = intent.getExtras();
//        //在闹铃界面点击 暂停再响 或 关闭 时 发送的意图
//        toReceiverIntent =  new Intent(this , AlarmClockReceiver.class);
//        toReceiverIntent.setAction("cn.edu.usts.cardhelper.alarmclock");
//        toReceiverIntent.putExtras(bundle);
//
//        //发送到 闹铃界面的意图
//        Intent alarmClockRingintent = new Intent(this, AlarmClockRingActivity.class);
//        alarmClockRingintent.putExtras(bundle);
//        alarmClockRingintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(alarmClockRingintent);
//
//        String ringInfo = bundle.getString("ringInfo");
//        Uri audioUri = Uri.parse(ringInfo.split("##")[1]);
//        if( ! audioUri.equals("无")){
//            try {
//                mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.setDataSource(this, audioUri);
//                mediaPlayer.prepareAsync();
//                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
//
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mediaPlayer.start();
//                    }
//                });
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        int isShake = bundle.getInt("isShake");
//        if(isShake == 1){
//            vibrator.vibrate(new long[] { 0, 1000, 1000, 1000, 1000 }, 0);
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        Log.i(TAG, "--------------onUnbind----------");
//        return super.onUnbind(intent);
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.i(TAG, "--------------onDestroy----------");
//        super.onDestroy();
//    }
//
//    public void stopMusic(){
//        if(mediaPlayer != null && mediaPlayer.isPlaying()){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//
//    public void stopVibrator(){
//        if(vibrator != null ){
//            vibrator.cancel();
//            vibrator = null;
//        }
//    }
//
//    //服务要暴露方法 必须要有一个中间人
//    private class AlarmClockRingServiceProvider extends Binder implements IAlarmClockRingServiceProvider{
//
//        @Override
//        public void pause5MinRing() {
//            stopMusic();
//            stopVibrator();
//            int alarmClockId = bundle.getInt("alarmClockId");
//            PendingIntent pi = PendingIntent.getBroadcast(AlarmClockService.this, alarmClockId, toReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*60*1000, pi);
//            Log.i(TAG, "----------5分钟后响铃的广播发送完毕！-----------");
//        }
//
//        @Override
//        public void close() {
//            stopMusic();
//            stopVibrator();
//            String repeatCycle = bundle.getString("repeatCycle");
//            int alarmClockId = bundle.getInt("alarmClockId");
//            int hour = bundle.getInt("hour");
//            int minute = bundle.getInt("minute");
//            if(repeatCycle.equals(Alarm_CLOCK_RING_ONLY_ONCE)){     //只响一次的闹钟  --> 取消该广播，更新数据库
//                alarmManager.cancel(PendingIntent.getBroadcast(AlarmClockService.this, alarmClockId, toReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//                AlarmClockDao dao = new AlarmClockDao(AlarmClockService.this);
//                dao.updataState(alarmClockId, 0);
//                Log.i(TAG, "--只响一次的闹钟广播已取消，数据库更新完毕！！-----");
//            }else{          //  --> 发送下一次闹钟的广播
//                int dTime = NextRingTimeProvider.showRingTimeByRepeatCycle(repeatCycle, hour, minute);
//                PendingIntent pi = PendingIntent.getBroadcast(AlarmClockService.this, alarmClockId, toReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+dTime, pi);
//                Log.i(TAG, "--距离下一次闹钟的剩余时间为"+RingTextUtil.showDTimeText(dTime)+"-----");
//            }
//        }
//
//        @Override
//        public void oneMinClose() {
//            pause5MinRing();
//        }
//
//    }
//}
