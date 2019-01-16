package com.minami_m.project.android.wakemeapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable{
    private boolean alarmIsOn;
    private boolean notificationIsOn;
    private boolean repeatIsOn;
    private boolean mon, tue, wed, thu, fri, sat, sun;
    private int hourOfDay, minute;

    protected Alarm(Parcel in) {
        alarmIsOn = in.readByte() != 0;
        notificationIsOn = in.readByte() != 0;
        repeatIsOn = in.readByte() != 0;
        mon = in.readByte() != 0;
        tue = in.readByte() != 0;
        wed = in.readByte() != 0;
        thu = in.readByte() != 0;
        fri = in.readByte() != 0;
        sat = in.readByte() != 0;
        sun = in.readByte() != 0;
        hourOfDay = in.readInt();
        minute = in.readInt();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (alarmIsOn ? 1 : 0));
        dest.writeByte((byte) (notificationIsOn ? 1 : 0));
        dest.writeByte((byte) (repeatIsOn ? 1 : 0));
        dest.writeByte((byte) (mon ? 1 : 0));
        dest.writeByte((byte) (tue ? 1 : 0));
        dest.writeByte((byte) (wed ? 1 : 0));
        dest.writeByte((byte) (thu ? 1 : 0));
        dest.writeByte((byte) (fri ? 1 : 0));
        dest.writeByte((byte) (sat ? 1 : 0));
        dest.writeByte((byte) (sun ? 1 : 0));
        dest.writeInt(hourOfDay);
        dest.writeInt(minute);
    }



    public Alarm() {
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getAlarmIsOn() {
        return alarmIsOn;
    }

    public void setAlarmIsOn(boolean alarmIsOn) {
        this.alarmIsOn = alarmIsOn;
    }

    public boolean getNotificationIsOn() {
        return notificationIsOn;
    }

    public void setNotificationIsOn(boolean notificationIsOn) {
        this.notificationIsOn = notificationIsOn;
    }

    public boolean getRepeatIsOn() {
        return repeatIsOn;
    }

    public void setRepeatIsOn(boolean repeatIsOn) {
        this.repeatIsOn = repeatIsOn;
    }

    public boolean getMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean getTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean getWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean getThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean getFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean getSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean getSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }
}