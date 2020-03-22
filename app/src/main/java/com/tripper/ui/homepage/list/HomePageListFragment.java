package com.tripper.ui.homepage.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.tripper.R;
import com.tripper.db.entities.Trip;

import java.util.List;

public class HomePageListFragment extends Fragment {

    private HomePageListViewModel homePageListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homePageListViewModel =
                new ViewModelProvider(this).get(HomePageListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_homepage_list_view, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        homePageListViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(@Nullable List<Trip> trips) {
                if (trips.isEmpty()) {
                    textView.setText(R.string.no_trip_data);
                }
                else {
                    textView.setText(trips.get(0).name);
                }

            }
        });
        return root;
    }
}
