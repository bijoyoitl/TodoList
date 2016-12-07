package com.optimalbd.todolist.Model;

/**
 * Created by ripon on 12/7/2016.
 */

public class DateTime {
    private int id;
    private int todoId;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public DateTime(int todoId, int year, int month, int day, int hour, int minute, int second) {
        this.todoId = todoId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public DateTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getId() {
        return id;
    }

    public int getTodoId() {
        return todoId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
