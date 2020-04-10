package com.tripper.ui.homepage.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.R;
import com.tripper.db.entities.Trip;

import java.util.List;

public class HomePageListFragment extends Fragment {

    private HomePageListViewModel homePageListViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TripListAdapter adapter;
    private LiveData<List<Trip>> trips;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homePageListViewModel =
                new ViewModelProvider(this).get(HomePageListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_homepage_list_view, container, false);


        layoutManager = new LinearLayoutManager(getContext());



        trips = homePageListViewModel.getTrips();
        adapter = new TripListAdapter(getContext());
        trips.observe(getViewLifecycleOwner(), trips -> adapter.setData(trips));
        recyclerView = root.findViewById(R.id.tripRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
