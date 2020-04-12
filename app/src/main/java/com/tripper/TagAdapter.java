package com.tripper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private ArrayList<TagItem> tagItemArrayList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public TagAdapter(ArrayList<TagItem> tagList) {
        tagItemArrayList = tagList;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View tagCard;
        ImageView tagImage;
        TextView tagText;
        public MyViewHolder(View v, OnItemClickListener listener) {
            super(v);
            tagText = v.findViewById(R.id.tagText);
            tagImage = v.findViewById(R.id.tagImage);
            tagCard = v.findViewById(R.id.cardView);

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
    public TagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TagItem currentItem = tagItemArrayList.get(position);
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        holder.tagText.setText(currentItem.getTagText());
        holder.tagImage.setImageResource(currentItem.getTagImage());
        holder.tagCard.setBackgroundColor(currentItem.isSelected() ? Color.GREEN : Color.WHITE);
        holder.tagCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem.setSelected(!currentItem.isSelected());
                holder.tagCard.setBackgroundColor(currentItem.isSelected() ? Color.GREEN : Color.WHITE);
            }
        });


    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tagItemArrayList.size();
    }


}
