package com.tripper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeLineAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_FIRST_ITEM = 1;
    private static final int VIEW_TYPE_MIDDLE_ITEM = 2;
    private static final int VIEW_TYPE_LAST_ITEM = 3;
    private int middleItemCounter = 0;

    private ArrayList<TimeLineItem> mItemsList;
    TimeLineAdapter(Context context, ArrayList<TimeLineItem> mItemsListData) {
        mItemsList = mItemsListData;
    }
    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
    @Override
    public int getItemViewType(int index) {
        return VIEW_TYPE_MIDDLE_ITEM;
//            if (item.getPosition() == 1) {
//                return VIEW_TYPE_FIRST_ITEM;
//            } else if(item.getPosition() == 2) {
//                return VIEW_TYPE_MIDDLE_ITEM;
//            } else {
//                return VIEW_TYPE_LAST_ITEM;
//            }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(middleItemCounter == 0){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_type_middle_left, parent, false);
            middleItemCounter = 1;
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_type_middle_right, parent, false);
            middleItemCounter = 0;
        }
        return new TimeLineAdapter.MiddleItemHolder(view);
//            if (viewType == VIEW_TYPE_FIRST_ITEM) {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_card_type_start, parent, false);
//                return new OverviewDiaryFragment.TimeLineAdapter.FirstItemHolder(view);
//            } else if (viewType == VIEW_TYPE_MIDDLE_ITEM) {
//                if(middleItemCounter == 0){
//                    view = LayoutInflater.from(parent.getContext())
//                            .inflate(R.layout.item_card_type_middle_left, parent, false);
//                    middleItemCounter = 1;
//                }else{
//                    view = LayoutInflater.from(parent.getContext())
//                            .inflate(R.layout.item_card_type_middle_right, parent, false);
//                    middleItemCounter = 0;
//                }
//                return new OverviewDiaryFragment.TimeLineAdapter.MiddleItemHolder(view);
//            } else if(viewType == VIEW_TYPE_LAST_ITEM) {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_card_end, parent, false);
//                return new OverviewDiaryFragment.TimeLineAdapter.LastItemHolder(view);
//            }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int index) {
        TimeLineItem items = mItemsList.get(index);
        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_FIRST_ITEM:
//                ((TimeLineAdapter.FirstItemHolder) holder).bind(items);
//                break;
            case VIEW_TYPE_MIDDLE_ITEM:
                ((TimeLineAdapter.MiddleItemHolder) holder).bind(items);
                break;
//            case VIEW_TYPE_LAST_ITEM:
//                ((TimeLineAdapter.LastItemHolder) holder).bind(items);
//                break;
        }
    }
//    private static class FirstItemHolder extends RecyclerView.ViewHolder {
//        TextView month, day, timeOfDay, location;
//        CircleImageView image;
//        FirstItemHolder(View itemView) {
//            super(itemView);
//            month =  itemView.findViewById(R.id.month);
//            day =  itemView.findViewById(R.id.day);
//            image = itemView.findViewById(R.id.location_snapshot);
//            timeOfDay = itemView.findViewById(R.id.timeOfDay);
//            location = itemView.findViewById(R.id.location);
//        }
//        void bind(TimeLineItem item) {
//            month.setText(item.month);
//            day.setText(item.day);
//            image.setImageResource(item.imageName);
//            timeOfDay.setText(item.timeOfDay);
//            location.setText(item.location);
//        }
//    }
    private static class MiddleItemHolder extends RecyclerView.ViewHolder {
        TextView month, day, timeOfDay, location ;
        CircleImageView image;
        MiddleItemHolder(View itemView) {
            super(itemView);
            month =  itemView.findViewById(R.id.month);
            day =  itemView.findViewById(R.id.day);
            image = itemView.findViewById(R.id.location_snapshot);
            timeOfDay = itemView.findViewById(R.id.timeOfDay);
            location = itemView.findViewById(R.id.location);
        }
        void bind(TimeLineItem item) {
            month.setText(item.month);
            day.setText(item.day);
            image.setImageResource(item.imageName);
            timeOfDay.setText(item.timeOfDay);
            location.setText(item.location);
        }
    }

//    private static class LastItemHolder extends RecyclerView.ViewHolder {
//        TextView month, day, timeOfDay, location;
//        CircleImageView image;
//        LastItemHolder(View itemView) {
//            super(itemView);
//            month =  itemView.findViewById(R.id.month);
//            day =  itemView.findViewById(R.id.day);
//            image = itemView.findViewById(R.id.location_snapshot);
//            timeOfDay = itemView.findViewById(R.id.timeOfDay);
//            location = itemView.findViewById(R.id.location);
//        }
//        void bind(TimeLineItem item) {
//            month.setText(item.month);
//            day.setText(item.day);
//            image.setImageResource(item.imageName);
//            timeOfDay.setText(item.timeOfDay);
//            location.setText(item.location);
//        }
//    }
}