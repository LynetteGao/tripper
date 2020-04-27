package com.tripper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.tripper.db.relationships.TripWithTags;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.PredictionViewHolder> {

    private List<AutocompletePrediction> predictions;
    private TripWithTags trip;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(Context context, List<AutocompletePrediction> predictions) {
        this.context = context;
        this.predictions = predictions;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PredictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        PredictionViewHolder vh = new PredictionViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PredictionViewHolder holder, int position) {
        AutocompletePrediction prediction = predictions.get(position);
        holder.locationText.setText(prediction.getFullText(null).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return predictions.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class PredictionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View locationCard;
        ImageView locationImage;
        TextView locationText;

        public PredictionViewHolder(View v) {
            super(v);
            locationText = v.findViewById(R.id.locationText);
            locationImage = v.findViewById(R.id.locationImage);
            locationCard = v.findViewById(R.id.tripCardView);
        }
    }
}
