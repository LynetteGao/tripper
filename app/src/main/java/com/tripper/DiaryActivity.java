package com.tripper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryActivity extends AppCompatActivity {
    RecyclerView rv;
    TimeLineAdapter timeLineAdapter;
    ArrayList<TimeLineItem> rvList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initLayout();
        initData();
    }

    private void initData() {
        TimeLineItem test1 = new TimeLineItem(1,R.drawable.img_test,"Central Park", "Morning","JAN","1");
        rvList.add(test1);
        TimeLineItem test2 = new TimeLineItem(2,R.drawable.img_test,"Brooklyn Bridge", "Afternoon","JAN","2");
        rvList.add(test2);
        TimeLineItem test3 = new TimeLineItem(2,R.drawable.img_test,"Yankee Stadium", "Evening", "JAN","3");
        rvList.add(test3);
        TimeLineItem test4 = new TimeLineItem(2,R.drawable.img_test,"Theater District", "Morning","JAN","4");
        rvList.add(test4);
        TimeLineItem test5 = new TimeLineItem(2,R.drawable.img_test,"Time Square", "Afternoon","JAN","5");
        rvList.add(test5);
        TimeLineItem test6 = new TimeLineItem(2,R.drawable.img_test,"Chelsea Market", "Evening","JAN","6");
        rvList.add(test6);
        TimeLineItem test7 = new TimeLineItem(3,R.drawable.img_test,"United Nations", "Morning","JAN","7");
        rvList.add(test7);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initLayout() {
        rv = findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        timeLineAdapter = new TimeLineAdapter(this,rvList);
        rv.setAdapter(timeLineAdapter);
    }

    public class TimeLineAdapter extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_DEAL_FIRST_ITEM = 1;
        private static final int VIEW_TYPE_DEAL_MIDDLE_ITEM = 2;
        private static final int VIEW_TYPE_DEAL_LAST_ITEM = 3;

        private Context mContext;
        private ArrayList<TimeLineItem> mDealsList;
        public TimeLineAdapter(Context context, ArrayList<TimeLineItem> mDealsListData) {
            mContext = context;
            mDealsList = mDealsListData;
        }
        @Override
        public int getItemCount() {
            return mDealsList.size();
        }
        @Override
        public int getItemViewType(int position) {
            TimeLineItem item = mDealsList.get(position);
            if (item.getPosition() == 1) {
                return VIEW_TYPE_DEAL_FIRST_ITEM;
            } else if(item.getPosition() == 2) {
                return VIEW_TYPE_DEAL_MIDDLE_ITEM;
            } else {
                return VIEW_TYPE_DEAL_LAST_ITEM;
            }
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_DEAL_FIRST_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_type_start, parent, false);
                return new FirstDealHolder(view);
            } else if (viewType == VIEW_TYPE_DEAL_MIDDLE_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_type_middle, parent, false);
                return new MiddleDealHolder(view);
            } else if(viewType == VIEW_TYPE_DEAL_LAST_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_end, parent, false);
                return new LastDealHolder(view);
            }
            return null;
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TimeLineItem items = mDealsList.get(position);
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_DEAL_FIRST_ITEM:
                    ((FirstDealHolder) holder).bind(items);
                    break;
                case VIEW_TYPE_DEAL_MIDDLE_ITEM:
                    ((MiddleDealHolder) holder).bind(items);
                    break;
                case VIEW_TYPE_DEAL_LAST_ITEM:
                    ((LastDealHolder) holder).bind(items);
                    break;
            }
        }
        private class FirstDealHolder extends RecyclerView.ViewHolder {
            TextView month, day, timeOfDay, location;
            CircleImageView image;
            FirstDealHolder(View itemView) {
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
                timeOfDay.setText(item.timeOfDay);
                location.setText(item.location);
            }
        }
        private class MiddleDealHolder extends RecyclerView.ViewHolder {
            TextView month, day, timeOfDay, location;
            CircleImageView image;
            MiddleDealHolder(View itemView) {
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
                timeOfDay.setText(item.timeOfDay);
                location.setText(item.location);
            }
        }

        private class LastDealHolder extends RecyclerView.ViewHolder {
            TextView month, day, timeOfDay, location;
            CircleImageView image;
            LastDealHolder(View itemView) {
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
                timeOfDay.setText(item.timeOfDay);
                location.setText(item.location);
            }
        }
    }
}
