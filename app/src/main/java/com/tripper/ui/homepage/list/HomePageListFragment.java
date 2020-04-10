package com.tripper.ui.homepage.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.R;
import com.tripper.db.entities.Trip;
import com.tripper.EmptyRecyclerView;

import java.util.List;
import java.util.Objects;

public class HomePageListFragment extends Fragment {

    private HomePageListViewModel homePageListViewModel;
    private EmptyRecyclerView recyclerView;
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
        adapter = new TripListAdapter(Objects.requireNonNull(getContext()));
        trips.observe(getViewLifecycleOwner(), trips -> adapter.setData(trips));

        recyclerView = root.findViewById(R.id.tripRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        View emptyView = root.findViewById(R.id.txtNoTrips);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
