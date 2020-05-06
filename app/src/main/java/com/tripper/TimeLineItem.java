package com.tripper;

import com.tripper.db.entities.DiaryEntry;
import com.tripper.db.relationships.DiaryWithEntries;

import java.util.ArrayList;

public class TimeLineItem {
    public int imageName;
    public String location;
    public String timeOfDay;
    public String month;
    public String day;
    public ArrayList<String> events;
    public long diaryId;

    public TimeLineItem(int imageName, String location, String timeOfDay, String month, String day, ArrayList<String> events, long diaryId) {
        this.imageName = imageName;
        this.location = location;
        this.timeOfDay = timeOfDay;
        this.month = month;
        this.day = day;
        this.events = events;
        this.diaryId = diaryId;
    }

    @Override
    public String toString(){
        return location + " " + timeOfDay + " " + month + " " + day;
    }
}
