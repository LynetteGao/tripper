package com.tripper;

public class TimeLineItem {
    public int position;
    public int imageName;
    public String location;
    public String timeOfDay;
    public String month;
    public String day;

    public TimeLineItem(int position, int imageName, String location, String timeOfDay, String month, String day) {
        this.position = position;
        this.imageName = imageName;
        this.location = location;
        this.timeOfDay = timeOfDay;
        this.month = month;
        this.day = day;
    }

    public int getPosition(){
        return position;
    }
}
