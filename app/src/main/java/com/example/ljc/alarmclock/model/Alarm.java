package com.example.ljc.alarmclock.model;

/**
 * Created by ljc on 17-10-17.
 */

public class Alarm {

    public int hour;
    public int minutes;
    public int daysofweek;
    public boolean vibrate;
    public boolean ring;
    public boolean state;
    public String s5 = "";

    public Alarm(int hour, int minutes, int daysofweek, boolean vibrate, boolean ring, boolean state) {
        this.hour = hour;
        this.minutes = minutes;
        this.daysofweek = daysofweek;
        this.vibrate = vibrate;
        this.ring = ring;
        this.state = state;
    }

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

    public String getDaysofweek() {
        String[] days = {"一", "二", "三", "四", "五", "六", "天"};

        for (int i=0;i<7;i++){
            if (daysofweek % 10 == 1)
                s5 = days[6-i] + " " + s5;

            daysofweek = daysofweek/10;
        }
        return s5;
    }

    public void setDaysofweek(int daysofweek) {
        this.daysofweek = daysofweek;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isRing() {
        return ring;
    }

    public void setRing(boolean ring) {
        this.ring = ring;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
