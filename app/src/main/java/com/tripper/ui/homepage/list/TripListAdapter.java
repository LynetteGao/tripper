package com.tripper.ui.homepage.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.tripper.CreateNewTrip;
import com.tripper.R;
import com.tripper.TripOverview;
import com.tripper.db.entities.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripListViewHolder> {

    private List<Trip> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private HomePageListViewModel homePageListViewModel;


    public TripListAdapter(Context context, HomePageListViewModel homePageListViewModel) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.homePageListViewModel = homePageListViewModel;
    }

    @Override
    public TripListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tripView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_trip_list_view, parent, false);

        TripListViewHolder vh = new TripListViewHolder(tripView);
        return vh;

    }

    @Override
    public void onBindViewHolder(TripListViewHolder viewHolder, int position) {
        viewHolder.bind(data.get(position));
        viewHolder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuDeleteTrip:
                        homePageListViewModel.deleteTrip(data.get(position));
                        data.remove(position);
                        notifyDataSetChanged();
                        break;
                    case R.id.menuEditTrip:
                        Intent intent = new Intent(context, CreateNewTrip.class);
                        intent.putExtra("tripId", data.get(position).id);
                        context.startActivity(intent);
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Trip> newData) {
        if (data != null) {
            PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
        else {
            data = newData;
        }
    }

    public static class TripListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTripName, txtStart, txtEnd, txtDuration;
        private Toolbar toolbar;
        CardView cardView;
        public TripListViewHolder(View tripView) {
            super(tripView);
            txtTripName = tripView.findViewById(R.id.txtTripName);
            txtStart = tripView.findViewById(R.id.txtStart);
            txtEnd = tripView.findViewById(R.id.txtEnd);
            txtDuration = tripView.findViewById(R.id.txtDuration);
            toolbar = tripView.findViewById(R.id.trip_toolbar);
            toolbar.inflateMenu(R.menu.trip_list_menu);
            cardView = tripView.findViewById(R.id.tripCardView);
        }

        void bind(final Trip trip) {
            if (trip != null) {
                String startDate = String.format("Start Date: %1$tb %1$te, %1$tY", trip.startDate);
                String endDate =   String.format("End Date: %1$tb %1$te, %1$tY", trip.endDate);
                txtTripName.setText(trip.name);
                txtStart.setText(startDate);
                txtEnd.setText(endDate);

                long dateDiff = trip.endDate.getTimeInMillis() - trip.startDate.getTimeInMillis();
                int dayCount = (int) dateDiff / (24 * 60 * 60 * 1000) + 1;
                String duration = String.format("Duration: %d days", dayCount);
                txtDuration.setText(duration);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), TripOverview.class);
                        intent.putExtra("tripId", trip.id);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    static class PostDiffCallback extends DiffUtil.Callback {
        private final List<Trip> oldTrips, newTrips;

        public PostDiffCallback(List<Trip> oldTrips, List<Trip> newTrips) {
            this.oldTrips = oldTrips;
            this.newTrips = newTrips;
        }

        @Override
        public int getOldListSize() { return oldTrips.size(); }

        @Override
        public int getNewListSize() { return newTrips.size(); }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldTrips.get(oldItemPosition).id == newTrips.get(newItemPosition).id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldTrips.get(oldItemPosition).equals(newTrips.get(newItemPosition));
        }
    }
}
