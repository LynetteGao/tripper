package com.tripper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/*This class is the Diary Fragment*/

public class OverviewDiaryFragment extends Fragment {
    RecyclerView rv;
    TimeLineAdapter timeLineAdapter;
    ArrayList<TimeLineItem> rvList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_diary,container,false);
        initLayout(view);
        initData();
        return view;
    }

    private void initData() {
        TimeLineItem test1 = new TimeLineItem(R.drawable.central_park,"Central Park", "Morning","JAN","1");
        rvList.add(test1);
        TimeLineItem test2 = new TimeLineItem(R.drawable.brooklyn_bridge,"Brooklyn Bridge", "Afternoon","JAN","2");
        rvList.add(test2);
        TimeLineItem test3 = new TimeLineItem(R.drawable.yankee_stadium,"Yankee Stadium", "Evening", "JAN","3");
        rvList.add(test3);
        TimeLineItem test4 = new TimeLineItem(R.drawable.theater_district,"Theater District", "Morning","JAN","4");
        rvList.add(test4);
        TimeLineItem test5 = new TimeLineItem(R.drawable.time_square,"Time Square", "Afternoon","JAN","5");
        rvList.add(test5);
//        TimeLineItem test6 = new TimeLineItem(2,R.drawable.chelsea_market,"Chelsea Market", "Evening","JAN","6");
//        rvList.add(test6);
//        TimeLineItem test7 = new TimeLineItem(3,R.drawable.united_nations,"United Nations", "Morning","JAN","7");
//        rvList.add(test7);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initLayout(View view) {
        rv = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(mLayoutManager);
        timeLineAdapter = new TimeLineAdapter(this.getActivity(), rvList);
        rv.setAdapter(timeLineAdapter);
    }
}
