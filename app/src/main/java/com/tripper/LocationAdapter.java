package com.tripper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private ArrayList<LocationItem> locationItemArrayList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(ArrayList<LocationItem> locationList) {
        locationItemArrayList = locationList;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View locationCard;
        ImageView locationImage;
        TextView locationText;
        public MyViewHolder(View v, OnItemClickListener listener) {
            super(v);
            locationText = v.findViewById(R.id.locationText);
            locationImage = v.findViewById(R.id.locationImage);
            locationCard = v.findViewById(R.id.tripCardView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        LocationItem currentItem = locationItemArrayList.get(position);
        holder.locationText.setText(currentItem.getLocationText());
        holder.locationImage.setImageResource(currentItem.getLocationImage());
        holder.locationCard.setBackgroundColor(currentItem.isSelected() ? Color.GREEN : Color.WHITE);
        holder.locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem.setSelected(!currentItem.isSelected());
                holder.locationCard.setBackgroundColor(currentItem.isSelected() ? Color.GREEN : Color.WHITE);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return locationItemArrayList.size();
    }

}
