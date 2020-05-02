package com.tripper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;

import java.util.List;


/*This class is the representation of the Map Fragment on the trip overview*/
public class OverviewMapFragment extends Fragment implements OnMapReadyCallback {

    private List<DayWithSegmentsAndEvents> day_item;
    private List<List<Double>> day;  // day list, each index is segment
    private List<List<List<Double>>> master; // each index is a day
    private long tripId;
    int position = 0;
    List<DaySegmentWithEvents> segmentsAndEvents;
    // for the lat/lons, each segment has them in pairs. index i = lat, index i+1 = lon
    List<Double> seg0;
    List<Double> seg1;
    List<Double> seg2;



    // construct db lists for days
    public OverviewMapFragment() {
        long tripId = getArguments().getLong("tripId");
        TripOverviewViewModel overViewListViewModel = new ViewModelProvider(this).get(TripOverviewViewModel.class);
        TripWithDaysAndDaySegments trip_with_days = overviewListViewModel.getTripWithDaysAndSegment(tripId);
        this.day_item = trip_with_days.days;
        this.tripId = trip_with_days.trip.id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_map, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.gMapFrag);
        fragment.getMapAsync(this);
        // construct db list for a single day
        for (int i = position; i < day_item.size(); i++) {  // for each day in trip
            List<DaySegmentWithEvents> segmentsAndEvents = day_item.get(position).daySegments; // for this day's segments
            for (Event event : segmentsAndEvents.get(0).events) {  // for each event in today's first segment
                seg0.add(Double.parseDouble(event.locationLat));
                seg0.add(Double.parseDouble(event.locationLon));
            }
            for (Event event : segmentsAndEvents.get(1).events) {  // for each event in today's second segment
                seg1.add(Double.parseDouble(event.locationLat));
                seg1.add(Double.parseDouble(event.locationLon));
            }
            for (Event event : segmentsAndEvents.get(2).events) {  // for each event in today's first segment
                seg2.add(Double.parseDouble(event.locationLat));
                seg2.add(Double.parseDouble(event.locationLon));
            }
            // now we have lists of each event in each segment.
            day.add(seg0);
            day.add(seg1);
            day.add(seg2);
            master.add(day);
            seg0.clear();
            seg1.clear();
            seg2.clear();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker,
        // and move the map's camera to the same location.
        // testing area below vv

//        int i = 0;
//        while( i < list.length) {
//            // can use to create multiple markers based on reading from DB
//            googleMap.addMarker(new MarkerOptions()
//            .position(list[i])
//            .title("Tester " + i));
//            i++;
//        }
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(a.latitude, a.longitude), 4));
    }
}
