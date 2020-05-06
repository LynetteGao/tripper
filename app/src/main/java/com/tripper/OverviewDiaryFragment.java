package com.tripper;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;
import com.tripper.db.entities.Event;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.DiaryWithEntries;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.viewmodels.DiaryEditViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*This class is the Diary Fragment*/

public class OverviewDiaryFragment extends Fragment {
    RecyclerView rv;
    TimeLineAdapter timeLineAdapter;
    ArrayList<TimeLineItem> rvList = new ArrayList<>();
    TripOverviewViewModel overviewListViewModel;
    DiaryEditViewModel diaryEditViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_diary, container, false);
        long tripId = getArguments().getLong("tripId");
        initLayout(view);
        initData(tripId);
        return view;
    }

    private void initData(long tripId) {
        overviewListViewModel = new ViewModelProvider(this).get(TripOverviewViewModel.class);
        TripWithDaysAndDaySegments trip_with_days = overviewListViewModel.getTripWithDaysAndSegment(tripId);
        List<DayWithSegmentsAndEvents> items = trip_with_days.days;
        ArrayList<TimeLineItem> timeline = new ArrayList<TimeLineItem>();
        diaryEditViewModel = new ViewModelProvider(this).get(DiaryEditViewModel.class);

        for (DayWithSegmentsAndEvents item : items) {
            String location, timeOfDay, month, day;
            List<DaySegmentWithEvents> segmentsAndEvents = item.daySegments;

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
            month = sdf.format(item.day.date.getTime());
            sdf = new SimpleDateFormat("dd");
            day = sdf.format(item.day.date.getTime());

            if (segmentsAndEvents != null && segmentsAndEvents.size() >= 1) {
                if (segmentsAndEvents.get(0).events != null && segmentsAndEvents.get(0).events.size() > 0) {
                    location = segmentsAndEvents.get(0).events.size() + " event(s)";
                    timeOfDay = "Morning";
                    ArrayList<String> events = new ArrayList<String>();
                    long diaryId;
                    if(segmentsAndEvents.get(0).diaryWithEntries == null){

                        Diary diary = new Diary();
                        long segmentId = segmentsAndEvents.get(0).daySegment.id;
                        diary.segmentId = segmentId;
                        diaryEditViewModel.insertDiary(diary);

                        DiaryEntry diaryEntry = new DiaryEntry();
                        diaryEntry.diaryId = diaryEditViewModel.getDiaryById(segmentId).id;
                        diaryEntry.diaryText = "none";
                        diaryEditViewModel.insertDiaryEntry(diaryEntry);

                        diaryId = diaryEntry.diaryId;
                    }else{
                        diaryId = segmentsAndEvents.get(0).diaryWithEntries.diary.id;
                    }

                    Log.i("DIARY-ID (1): ", String.valueOf(diaryId));

                    for (Event event : segmentsAndEvents.get(0).events) {
                        events.add(event.name);
                    }

                    timeline.add(new TimeLineItem(R.drawable.central_park, location, timeOfDay, month, day, events, diaryId));
                }
            }
            if (segmentsAndEvents != null && segmentsAndEvents.size() >= 2) {
                if (segmentsAndEvents.get(1).events != null && segmentsAndEvents.get(1).events.size() > 0) {
                    location = segmentsAndEvents.get(1).events.size() + " event(s)";
                    timeOfDay = "Afternoon";
                    ArrayList<String> events = new ArrayList<String>();
                    long diaryId;

                    if(segmentsAndEvents.get(1).diaryWithEntries == null){
                        Diary diary = new Diary();
                        long segmentId = segmentsAndEvents.get(1).daySegment.id;
                        diary.segmentId = segmentId;
                        diaryEditViewModel.insertDiary(diary);


                        DiaryEntry diaryEntry = new DiaryEntry();
                        diaryEntry.diaryId = diaryEditViewModel.getDiaryById(segmentId).id;
                        diaryEntry.diaryText = "none";
                        diaryEditViewModel.insertDiaryEntry(diaryEntry);

                        diaryId = diaryEntry.diaryId;
                    }else{
                        diaryId = segmentsAndEvents.get(1).diaryWithEntries.diary.id;
                    }

                    Log.i("DIARY-ID (2): ", String.valueOf(diaryId));

                    for (Event event : segmentsAndEvents.get(1).events) {
                        events.add(event.name);
                    }

                    timeline.add(new TimeLineItem(R.drawable.brooklyn_bridge, location, timeOfDay, month, day, events, diaryId));
                }
            }
            if (segmentsAndEvents != null && segmentsAndEvents.size() >= 3) {
                if (segmentsAndEvents.get(2).events != null && segmentsAndEvents.get(2).events.size() > 0) {
                    location = segmentsAndEvents.get(2).events.size() + " event(s)";
                    timeOfDay = "Evening";
                    ArrayList<String> events = new ArrayList<String>();
                    long diaryId;

                    if(segmentsAndEvents.get(2).diaryWithEntries == null){
                        Diary diary = new Diary();
                        long segmentId = segmentsAndEvents.get(2).daySegment.id;
                        diary.segmentId = segmentId;
                        diaryEditViewModel.insertDiary(diary);

                        DiaryEntry diaryEntry = new DiaryEntry();
                        diaryEntry.diaryId = diaryEditViewModel.getDiaryById(segmentId).id;
                        diaryEntry.diaryText = "none";
                        diaryEditViewModel.insertDiaryEntry(diaryEntry);

                        diaryId = diaryEntry.diaryId;
                    }else{
                        diaryId = segmentsAndEvents.get(2).diaryWithEntries.diary.id;
                    }

                    Log.i("DIARY-ID (3): ", String.valueOf(diaryId));

                    for (Event event : segmentsAndEvents.get(2).events) {
                        events.add(event.name);
                    }

                    timeline.add(new TimeLineItem(R.drawable.theater_district, location, timeOfDay, month, day, events, diaryId));
                }
            }
        }

        rvList.addAll(timeline);

//        for(TimeLineItem item: timeline){
//            Log.d("INPUT-ID: ", String.valueOf(item.diaryId));
//    }

//        TimeLineItem test1 = new TimeLineItem(R.drawable.central_park,"Central Park", "Morning","JAN","1");
//        rvList.add(test1);
//        TimeLineItem test2 = new TimeLineItem(R.drawable.brooklyn_bridge,"Brooklyn Bridge", "Afternoon","JAN","2");
//        rvList.add(test2);
//        TimeLineItem test3 = new TimeLineItem(R.drawable.yankee_stadium,"Yankee Stadium", "Evening", "JAN","3");
//        rvList.add(test3);
//        TimeLineItem test4 = new TimeLineItem(R.drawable.theater_district,"Theater District", "Morning","JAN","4");
//        rvList.add(test4);
//        TimeLineItem test5 = new TimeLineItem(R.drawable.time_square,"Time Square", "Afternoon","JAN","5");
//        rvList.add(test5);
//        TimeLineItem test6 = new TimeLineItem(2,R.drawable.chelsea_market,"Chelsea Market", "Evening","JAN","6");
//        rvList.add(test6);
//        TimeLineItem test7 = new TimeLineItem(3,R.drawable.united_nations,"United Nations", "Morning","JAN","7");
//        rvList.add(test7);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initLayout(View view) {
        rv = view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(mLayoutManager);
        timeLineAdapter = new TimeLineAdapter(this.getActivity(), rvList, getArguments().getLong("tripId"));
        rv.setAdapter(timeLineAdapter);
    }
}
