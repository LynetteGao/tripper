package com.tripper;

public class TimeLineItem {
    public int imageName;
    public String location;
    public String timeOfDay;
    public String month;
    public String day;

    public TimeLineItem(int imageName, String location, String timeOfDay, String month, String day) {
        this.imageName = imageName;
        this.location = location;
        this.timeOfDay = timeOfDay;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString(){
        return location + " " + timeOfDay + " " + month + " " + day;
    }
}
