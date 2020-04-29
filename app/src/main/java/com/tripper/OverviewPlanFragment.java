package com.tripper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.ui.homepage.list.HomePageListViewModel;

import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;



/*This class is the representation of the Plan Fragment on the trip overview*/

public class OverviewPlanFragment extends Fragment {


    private TripOverviewViewModel overviewListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        long tripId = getArguments().getLong("tripId");
        Log.d(TAG, "onCreateView:"+tripId);

        View view = inflater.inflate(R.layout.fragment_overview_plan,container,false);
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setHasFixedSize(true);



        overviewListViewModel = new ViewModelProvider(this).get(TripOverviewViewModel.class);

        TripWithDaysAndDaySegments trip_with_days = overviewListViewModel.getTripWithDaysAndSegment(tripId);

        TripOverviewAdapter adapter = new TripOverviewAdapter(Objects.requireNonNull(getContext()),overviewListViewModel,trip_with_days );
        mRecyclerView.setAdapter(adapter);

        Day i = trip_with_days.days.get(0).day;
        return view;
    }
}
