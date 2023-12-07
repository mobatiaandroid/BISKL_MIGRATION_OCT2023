package com.example.bskl_kotlin.fragment.attendance.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceChartListModel;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SelectedListRecyclerAdapter extends RecyclerView.Adapter<SelectedListRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AttendanceModel> mYoutubeArraylist;
    private ArrayList<AttendanceChartListModel> mAttendanceChartListModel;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView typeTextView;
        TextView topDivider;
        TextView bottomDivider;

        public MyViewHolder(View view) {
            super(view);
            topDivider = view.findViewById(R.id.topDivider);
            bottomDivider = view.findViewById(R.id.bottomDivider);
            typeTextView = view.findViewById(R.id.typeTextView);
            dateTextView = view.findViewById(R.id.dateTextView);


        }
    }


    public SelectedListRecyclerAdapter(Context mContext, ArrayList<AttendanceModel> youtubeArraylist, ArrayList<AttendanceChartListModel> mAttendanceChartModel) {
        this.mContext = mContext;
        this.mYoutubeArraylist = youtubeArraylist;
        this.mAttendanceChartListModel = mAttendanceChartModel;

    }

    @Override
    public SelectedListRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selected_attendance_recycleritem, parent, false);

        return new SelectedListRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectedListRecyclerAdapter.MyViewHolder holder, int position) {
//

        //    String legendArray[]={"Total days","Present","Late","Authorised absence","Unauthorised absence"};
        if (position == 0) {
            holder.topDivider.setVisibility(View.VISIBLE);
        } else {
            holder.topDivider.setVisibility(View.GONE);

        }
        holder.dateTextView.setText(dateConversionMMM(mYoutubeArraylist.get(position).getDate()));
        if (mYoutubeArraylist.get(position).getLate().equalsIgnoreCase("1")) {
            holder.typeTextView.setText(mAttendanceChartListModel.get(2).getName());

        } else if (mYoutubeArraylist.get(position).getPresent().equalsIgnoreCase("1"))
        {
            holder.typeTextView.setText("Present");

        } else if (mYoutubeArraylist.get(position).getAuthorised_absence().equalsIgnoreCase("1")) {
            holder.typeTextView.setText("Authorised absence");

        } else if (mYoutubeArraylist.get(position).getUnauthorised_absence().equalsIgnoreCase("1")) {
            holder.typeTextView.setText(mAttendanceChartListModel.get(3).getName());

        }

    }


    @Override
    public int getItemCount() {
        return mYoutubeArraylist.size();
    }
    public static String dateConversionMMM(String inputDate) {
        String mDate = "";

        try {
            Date date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = formatter.parse(inputDate);
            //Subtracting 6 hours from selected time
            long time = date.getTime();

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

            mDate = formatterFullDate.format(time);

        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
        return mDate;
    }
}
