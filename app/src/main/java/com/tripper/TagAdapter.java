package com.tripper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private String[] data;
    int[] images;
    Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    //public TagAdapter( String[] data){
    public TagAdapter(Context ct, String[] data, int[] images) {
        this.data = data;
        this.context = ct;
        this.images = images;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imageView1;
        TextView textView1;
        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.textView1);
            imageView1 = v.findViewById(R.id.imageView1);
        }
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem1, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        holder.textView1.setText(data[position]);
        holder.imageView1.setImageResource(images[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.length;
    }
}
