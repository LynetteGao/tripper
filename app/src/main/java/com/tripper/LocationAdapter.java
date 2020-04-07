package com.tripper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private String[] data;
    int[] locImages;
    Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    //public TagAdapter( String[] data){
    public LocationAdapter(Context ct, String[] data, int[] images) {
        this.data = data;
        this.context = ct;
        this.locImages = images;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView locImage;
        TextView locText;
        public MyViewHolder(View v) {
            super(v);
            locText = v.findViewById(R.id.locText);
            locImage = v.findViewById(R.id.locImage);
        }
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        holder.locText.setText(data[position]);
        holder.locImage.setImageResource(locImages[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.length;
    }
}
