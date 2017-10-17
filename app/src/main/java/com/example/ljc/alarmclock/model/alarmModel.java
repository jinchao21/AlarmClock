package com.example.ljc.alarmclock.model;

/**
 * Created by ljc on 17-10-17.
 */

public class alarmModel {

    public int hour;
    public int minutes;
    public int daysofweek;
    public int alarmtime;
    public int vibrate;
    public int state;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDaysofweek() {
        return daysofweek;
    }

    public void setDaysofweek(int daysofweek) {
        this.daysofweek = daysofweek;
    }

    public int getAlarmtime() {
        return alarmtime;
    }

    public void setAlarmtime(int alarmtime) {
        this.alarmtime = alarmtime;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
