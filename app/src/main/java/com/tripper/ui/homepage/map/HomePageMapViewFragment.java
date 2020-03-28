package com.tripper.ui.homepage.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tripper.R;

public class HomePageMapViewFragment extends Fragment {

    private HomePageMapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(HomePageMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_homepage_map_view, container, false);
        return root;
    }
}
