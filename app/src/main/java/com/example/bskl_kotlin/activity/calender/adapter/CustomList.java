package com.example.bskl_kotlin.activity.calender.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel;

import java.util.ArrayList;

public class CustomList extends RecyclerView.Adapter<CustomList.MyViewHolder> {

    private Context mContext;
    String[] event;
    private ArrayList<CalendarModel> eventArrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event;
        TextView eventDate;
        LinearLayout relSub;


        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.event);
            eventDate= view.findViewById(R.id.eventTxt);
            relSub= view.findViewById(R.id.relSub);

        }
    }


    public CustomList(Context mContext, ArrayList eventArrayList) {
        this.mContext = mContext;
        this.eventArrayList=eventArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if(eventArrayList.get(position).getPublic_holiday()==1)
        {
            holder.relSub.setBackgroundResource(R.color.calendarbg);
        }
        if(eventArrayList.get(position).getDate().equalsIgnoreCase(eventArrayList.get(position).getEndDateList()))
        {
            holder.event.setText(eventArrayList.get(position).getDate());

        }
        else {
            holder.event.setText(eventArrayList.get(position).getStartDateNew()+"-"+eventArrayList.get(position).getEndDateNew());

        }
        holder.eventDate.setText(eventArrayList.get(position).getTittle());
        // System.out.println("title::"+eventArrayList.get(position).getTittle());
    }



    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }
}