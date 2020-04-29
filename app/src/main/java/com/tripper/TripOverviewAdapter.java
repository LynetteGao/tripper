package com.tripper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.ui.homepage.list.HomePageListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class TripOverviewAdapter extends RecyclerView.Adapter<TripOverviewAdapter.TripHolder> {

    //private List<DayWithSegmentsAndEvents> day_item;
    Geocoder geocoder;
    private List<DayWithSegmentsAndEvents> day_item;
    private Context context;
    //private LayoutInflater layoutInflater;
    //private HomePageListViewModel homePageListViewModel;
    private TripOverviewViewModel tripOverviewViewModel;
    List<Address> addresses;


    public TripOverviewAdapter(Context context, TripOverviewViewModel tripOverviewViewModel, TripWithDaysAndDaySegments trip_with_days) {
        this.context = context;
        //this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tripOverviewViewModel = tripOverviewViewModel;
        this.day_item = trip_with_days.days;
        geocoder = new Geocoder(context, Locale.getDefault());
    }


    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_event_item,parent,false);
        return new TripHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {
        Day currentDay = day_item.get(position).day;

        int dayCount  = position+1;
        String date = String.format("%1$tb %1$te, %1$tY", currentDay.date);
        holder.textViewDay.setText("Day "+ dayCount);


//        Double lat = Double.valueOf(currentDay.locationLat);
//        Double lon = Double.valueOf(currentDay.locationLon);
//        Log.d(TAG, "onBindViewHolder: "+ lat);
//        try {
//            addresses = geocoder.getFromLocation(lat, lon, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String address = addresses.get(0).getAddressLine(0);
        holder.textViewAddress.setText(date);

        holder.textViewMorning.setText("Morning:" );
        holder.textViewAfternoon.setText("Afternoon");
        holder.textViewEvening.setText("Evening");

        List<DaySegmentWithEvents> segmentsAndEvents = day_item.get(position).daySegments;
        Log.d(TAG, "onBindViewHolder: ");
        if( segmentsAndEvents!=null& segmentsAndEvents.size()>=1 && segmentsAndEvents.get(0).events.size()>=1 ) {
            String mEvent = segmentsAndEvents.get(0).events.get(0).name;
            holder.textViewMorning.setText("Morning: " + mEvent);
        }
        if(segmentsAndEvents!=null& segmentsAndEvents.size()>=2 && segmentsAndEvents.get(1).events.size()>=1) {
            String aEvent = segmentsAndEvents.get(1).events.get(0).name;
            holder.textViewMorning.setText("Afternoon: " + aEvent);
        }
        if(segmentsAndEvents!=null& segmentsAndEvents.size()>=3 && segmentsAndEvents.get(2).events.size()>=1) {
            String eEvent = segmentsAndEvents.get(2).events.get(0).name;
            holder.textViewMorning.setText("Evening: " + eEvent);
        }


        //holder.textViewMorning.setText("Morning:" + mEvent);


    }

    @Override
    public int getItemCount() {
        return day_item.size();
    }

//    public void setDay_item(List<Trip> days){
//        this.day_item = days;
//        notifyDataSetChanged();
//    }

    class TripHolder extends RecyclerView.ViewHolder{
        private TextView textViewAddress;
        private TextView textViewDay;
        private TextView textViewMorning;
        private TextView textViewAfternoon;
        private TextView textViewEvening;

        public TripHolder(View itemView) {
            super(itemView);
            textViewAddress = itemView.findViewById(R.id.address);
            textViewDay = itemView.findViewById(R.id.day);
            textViewMorning = itemView.findViewById(R.id.morning_event);
            textViewAfternoon = itemView.findViewById(R.id.afternoon_event);
            textViewEvening = itemView.findViewById(R.id.evening_event);

        }
    }
}
