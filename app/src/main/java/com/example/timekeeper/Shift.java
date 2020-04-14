package com.example.timekeeper;

import java.util.Date;

public class Shift {
    public Date date;
    public int base;
    public int dayNight;
    public double overtime;
    public double nightHours;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDayNight(int dayNight) {
        this.dayNight = dayNight;
    }

    public void setOvertime(double overtime) {

        this.overtime = overtime;
    }

    public void setNightHours(double nightHours) {
        this.nightHours = nightHours;
    }

    public Date getDate() {
        return date;
    }

    public int getDayNight() {
        return dayNight;
    }

    public double getOvertime() {
        return overtime;
    }

    public double getNightHours() {
        return nightHours;
    }

    public Shift(Date date, int dayNight, double overtime, double nightHours) {
        this.base = 8;
        this.date = date;
        this.dayNight = dayNight;
        this.overtime = overtime;
        this.nightHours = nightHours;
    }
}
