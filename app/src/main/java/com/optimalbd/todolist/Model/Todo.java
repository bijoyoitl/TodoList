package com.optimalbd.todolist.Model;

/**
 * Created by ripon on 12/1/2016.
 */

public class Todo {
    private String id;
    private String title;
    private String details;
    private String date;
    private String time;
    private long selectedTime;
    private String type;

    public Todo(String id, String title, String date, String type) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
    }

    public Todo(String id, String title, String details, String date, String time, String type) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public Todo(String title, String details, String date, String time, long selectedTime, String type) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
        this.selectedTime = selectedTime;
        this.type = type;
    }

    public long getSelectedTime() {
        return selectedTime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
