package com.tripper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.ui.homepage.list.HomePageListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripOverviewAdapter extends RecyclerView.Adapter<TripOverviewAdapter.TripHolder> {

    //private List<DayWithSegmentsAndEvents> day_item;

    private List<DayWithSegmentsAndEvents> day_item;
    private Context context;
    //private LayoutInflater layoutInflater;
    //private HomePageListViewModel homePageListViewModel;
    private TripOverviewViewModel tripOverviewViewModel;


    public TripOverviewAdapter(Context context, TripOverviewViewModel tripOverviewViewModel, TripWithDaysAndDaySegments trip_with_days) {
        this.context = context;
        //this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tripOverviewViewModel = tripOverviewViewModel;
        this.day_item = trip_with_days.days;
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
        //Trip currentTrip = day_item.get(position);//TODO
        String date = String.format("%1$tb %1$te, %1$tY", currentDay.date);

        int dayCount  = position+1;
        holder.textViewAddress.setText("Day "+ dayCount);

        holder.textViewDay.setText(date);//TODO
        holder.textViewMorning.setText("Morning");
        holder.textViewAfternoon.setText("Afternoon");
        holder.textViewEvening.setText("Evening");
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
