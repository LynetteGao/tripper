package com.tripper.ui.homepage.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.tripper.R;
import com.tripper.db.entities.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripListViewHolder> {

    private List<Trip> data;
    private Context context;
    private LayoutInflater layoutInflater;


    public TripListAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        CardView cardView;
        public TripListViewHolder(View tripView) {
            super(tripView);
            txtTripName = tripView.findViewById(R.id.txtTripName);
            txtStart = tripView.findViewById(R.id.txtStart);
            txtEnd = tripView.findViewById(R.id.txtEnd);
            txtDuration = tripView.findViewById(R.id.txtDuration);
        }

        void bind(final Trip trip) {
            if (trip != null) {
                String startDate = String.format("Start Date: %1$tb %1$te, %1$tY", trip.startDate);
                String endDate =   String.format("End Date: %1$tb %1$te, %1$tY", trip.endDate);
                txtTripName.setText(trip.name);
                txtStart.setText(startDate);
                txtEnd.setText(endDate);

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
