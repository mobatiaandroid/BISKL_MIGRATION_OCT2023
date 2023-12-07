package com.example.bskl_kotlin.activity.calender.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.activity.calender.utils.CustomCalendarViewLibrary;

import java.util.ArrayList;

public class CalendarCustomAdapter extends RecyclerView.Adapter<CalendarCustomAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> monthArrayList;
    int startMonth;
    long mStartTime;
    RelativeLayout mProgressRelLayouts;

    class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        CustomCalendarViewLibrary calendarview;

        MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            calendarview = itemView.findViewById(R.id.calendar_view_second);
        }
    }


    public CalendarCustomAdapter(Context context, ArrayList<String> mMonthArrayList, int startMonth, long startTime, RelativeLayout mProgressRelLayout) {
        this.context = context;
        this.monthArrayList = new ArrayList<>();
        this.monthArrayList = mMonthArrayList;
        this.startMonth = startMonth;
        this.mStartTime = startTime;
        this.mProgressRelLayouts = mProgressRelLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        if (position >=9) {
//
//            mProgressRelLayouts.clearAnimation();
//            mProgressRelLayouts.setVisibility(View.GONE);
//        }
        holder.calendarview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.calendarview.next(position, mStartTime);


    }


    @Override
    public int getItemCount() {
        return monthArrayList.size();
    }

}