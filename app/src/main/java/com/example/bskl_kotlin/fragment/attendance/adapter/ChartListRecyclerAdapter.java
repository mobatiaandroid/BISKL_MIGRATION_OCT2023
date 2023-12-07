package com.example.bskl_kotlin.fragment.attendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceChartListModel;

import java.util.ArrayList;

public class ChartListRecyclerAdapter extends RecyclerView.Adapter<ChartListRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AttendanceChartListModel> mYoutubeArraylist;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView titleTextView;
        TextView countTextView;

        public MyViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            countTextView = view.findViewById(R.id.countTextView);


        }
    }


    public ChartListRecyclerAdapter(Context mContext, ArrayList<AttendanceChartListModel> youtubeArraylist) {
        this.mContext = mContext;
        this.mYoutubeArraylist = youtubeArraylist;

    }

    @Override
    public ChartListRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_attednancechart_recycleritem, parent, false);

        return new ChartListRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChartListRecyclerAdapter.MyViewHolder holder, int position) {
//
        holder.countTextView.setText(mYoutubeArraylist.get(position).getCount());
        holder.titleTextView.setText(mYoutubeArraylist.get(position).getName());
//        Glide.with(mContext).load(AppUtils.replace(mYoutubeArraylist.get(position).getThumbnailsHigh())).centerCrop().into( holder.itemImage);

        switch (position)
        {
            case 0:
                holder.countTextView.setBackgroundResource(R.color.grey);
                holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.grey));
                break;
            case 1:
                holder.countTextView.setBackgroundResource(R.color.present);
                holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.present));

                break;
            case 2:
                holder.countTextView.setBackgroundResource(R.color.late);
                holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.late));

                break;
//            case 3:
//                holder.countTextView.setBackgroundResource(R.color.authorisedabsence);
//                holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.authorisedabsence));
//
//                break;
            case 3:
                holder.countTextView.setBackgroundResource(R.color.unauthorisedabsence);
                holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.unauthorisedabsence));

                break;
        }
    }


    @Override
    public int getItemCount() {
        return mYoutubeArraylist.size();
    }

}
