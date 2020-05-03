package com.tripper;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;

import java.util.ArrayList;
import java.util.List;


/*This class is the representation of the Map Fragment on the trip overview*/
public class OverviewMapFragment extends Fragment implements OnMapReadyCallback {

    private List<DayWithSegmentsAndEvents> day_item;
    private List<List<List<Double>>> master = new ArrayList<List<List<Double>>>(); // each index is a day
    private long tripId;
    int position = 0;
    MapOverviewViewModel overViewListViewModel;
    List<DaySegmentWithEvents> segmentsAndEvents;
    // for the lat/lons, each segment has them in pairs. index i = lat, index i+1 = lon
    List<String> names = new ArrayList<String>();
    LatLng firstPosition = null;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         tripId = getArguments().getLong("tripId");
        return inflater.inflate(R.layout.fragment_overview_map, container, false);

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        overViewListViewModel = new ViewModelProvider(this).get(MapOverviewViewModel.class);
        TripWithDaysAndDaySegments trip_with_days = overViewListViewModel.getTripWithDaysAndSegment(tripId);
        this.day_item = trip_with_days.days;
        this.tripId = trip_with_days.trip.id;
        SupportMapFragment fragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.gMapFrag);
        fragment.getMapAsync(this);
        // construct db list for a single day
        for (int i = position; i < day_item.size(); i++) {  // for each day in trip
            List<Double> seg0 = new ArrayList<Double>();
            List<Double> seg1 = new ArrayList<Double>();
            List<Double> seg2 = new ArrayList<Double>();
            List<List<Double>> day = new ArrayList<List<Double>>();  // day list, each index is segment
            List<DaySegmentWithEvents> segmentsAndEvents = day_item.get(position).daySegments; // for this day's segments
            for (Event event : segmentsAndEvents.get(0).events) {  // for each event in today's first segment
                seg0.add(Double.parseDouble(event.locationLat));
                seg0.add(Double.parseDouble(event.locationLon));
                names.add(event.name);
            }
            for (Event event : segmentsAndEvents.get(1).events) {  // for each event in today's second segment
                seg1.add(Double.parseDouble(event.locationLat));
                seg1.add(Double.parseDouble(event.locationLon));
                names.add(event.name);
            }
            for (Event event : segmentsAndEvents.get(2).events) {  // for each event in today's third segment
                seg2.add(Double.parseDouble(event.locationLat));
                seg2.add(Double.parseDouble(event.locationLon));
                names.add(event.name);
            }
            // now we have lists of each event in each segment.
            day.add(seg0);
            day.add(seg1);
            day.add(seg2);
            master.add(day);
    }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker,
        // and move the map's camera to the same location.
        // testing area below vv
        int namecount = 0;
        for(int day = 0; day < master.size(); day++) {
            for( int segment = 0; segment < master.get(day).size(); segment++) { // for each segment of that day
                for(int event = 0; event < master.get(day).get(segment).size(); event++) {  // for each event of that segment
                    // remember, event index are pairs of lat/lons
                    // different colors for different days
                    LatLng position = new LatLng(master.get(day).get(segment).get(event), master.get(day).get(segment).get(event+1));
                    if (firstPosition == null) {
                        firstPosition = position;
                    }
                    event++;
                    googleMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(names.get(namecount))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    namecount++;
                }
            }
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 12));
    }
}
