package com.example.ljc.alarmclock.model;

/**
 * Created by ljc on 17-10-17.
 */

public class Alarm {

    public int id;
    public int hour;
    public int minutes;
    public int daysofweek;
    public boolean vibrate;
    public boolean ring;
    public boolean state;
//    public String s5 = "";


    public Alarm(int id, int hour, int minutes, int daysofweek, boolean vibrate, boolean ring, boolean state) {
        this.id = id;
        this.hour = hour;
        this.minutes = minutes;
        this.daysofweek = daysofweek;
        this.vibrate = vibrate;
        this.ring = ring;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /*
    * 此处将闹钟列表显示的一，二，三，，天 与数据库中1,2,3，，7对应
    * 使用7位数如1111111存储每周重复状态
    * 使用取余操作判断每周中星期×是否重复
    * */
    public String getDaysofweek() {
        int a = daysofweek;
        String[] days = {"一", "二", "三", "四", "五", "六", "天", "只响一次"};
        String s5 = "";
        if (daysofweek==0) {
            return days[7];
        } else {

            for (int i = 0; i < 7; i++) {
                if (a % 10 == 1)
                    s5 = days[6 - i] + " " + s5;
                a = a / 10;
            }
            return s5;
        }
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
