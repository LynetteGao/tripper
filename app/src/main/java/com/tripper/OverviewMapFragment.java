package com.tripper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


/*This class is the representation of the Map Fragment on the trip overview*/
public class OverviewMapFragment extends Fragment implements OnMapReadyCallback {
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker,
        // and move the map's camera to the same location.
        // testing area below vv
        LatLng a = new LatLng(-35.016, 143.321);
        LatLng b = new LatLng(-34.747, 145.592);
        LatLng c =new LatLng(-34.364, 147.891);
        LatLng d = new LatLng(-33.501, 150.217);
        LatLng e = new LatLng(-32.306, 149.248);
        LatLng f   = new LatLng(-32.491, 147.309);
        LatLng list[] = {a,b,c,d,e,f};
        int i = 0;
        while( i < list.length) {
            // can use to create multiple markers based on reading from DB
            googleMap.addMarker(new MarkerOptions()
            .position(list[i])
            .title("Tester " + i));
            i++;
        }
        Polygon polyline1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        a,
                       b,
                        c,
                        d,
                        e,
                       f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(a));
    }
}
