package com.example.bskl_kotlin.fragment.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private String[] monthNames = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    private String[] dayNames = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
    private ArrayList<String> mHolidaysArray;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, String[] data, ArrayList<String> holidaysArray) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.mHolidaysArray = holidaysArray;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int monthNumber = Integer.parseInt(mData[position].toString());
        String monthName = monthNames[monthNumber];


        Calendar cal=Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String yearName = year_date.format(cal.getTime());
        int actualYearNumber = Integer.parseInt(yearName);
        int yearNumber = Integer.parseInt(yearName);

        SimpleDateFormat month_date = new SimpleDateFormat("MM", Locale.ENGLISH);
        String currentMonthName = month_date.format(cal.getTime());
        int currentMonthNumber = Integer.parseInt(currentMonthName); // Device current month


        if (currentMonthNumber<7) { // JAN - JUL
            if (monthNumber>=7) {
                yearNumber = actualYearNumber-1;
            }else {
                yearNumber = actualYearNumber;
            }
        }else {                     // AUG - DEC
            if (monthNumber>=7) {
                yearNumber = actualYearNumber;
            }else {
                yearNumber = actualYearNumber+1;
            }
        }

        String input_date = "01/"+(monthNumber+1)+"/"+yearNumber; // Since monthNumber=0 => Jan

        //Log.d("startDay","startDay===="+input_date);

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE",Locale.ENGLISH);
        String startDay = format2.format(dt1).toUpperCase();

        int dayNumber = 1;
        for (int i=0;i<dayNames.length;i++) {
            if (dayNames[i].equals(startDay)) {
                dayNumber = i+1;
                break;
            }
        }
        //Log.d("dayNumber","dayNumber===="+dayNumber);
        holder.titleLabel.setText(monthName);


        for (int i=1;i<=49;i++) {
            holder.dateTextView[i].setText("");
        }

        int lastDayOfThisMonth = 30;
        Calendar c = Calendar.getInstance();
        c.set(yearNumber,monthNumber,1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat sdf = new SimpleDateFormat("dd",Locale.ENGLISH);
        lastDayOfThisMonth = Integer.parseInt(sdf.format(c.getTime()));


        int days = 0;
        for (int i=dayNumber;i<=49;i++) {
            if (days>=lastDayOfThisMonth) { break; }
            days = days +1;
            holder.dateTextView[i].setText(""+days);

            DecimalFormat formatter = new DecimalFormat("00");
            String monthIn2Digits = formatter.format(monthNumber+1);
            String dayIn2Digits = formatter.format(days);

            String currentDayString = yearNumber+"-"+monthIn2Digits+"-"+dayIn2Digits;
            if (mHolidaysArray.contains(currentDayString)) {
                System.out.println("if case working holiday");
                holder.dateTextView[i].setBackgroundResource(R.color.calendarbg);
                holder.dateTextView[i].setTextColor(mContext.getResources().getColor(R.color.white));
            }
            else
            {
                System.out.println("else case working holiday");
                holder.dateTextView[i].setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleLabel;
        TextView[] dateTextView = new TextView[100];

        ViewHolder(View itemView) {
            super(itemView);
            titleLabel = itemView.findViewById(R.id.titleLabel);
            itemView.setOnClickListener(this);

            for (int i=1;i<=49;i++) {
                int resID = mContext.getResources().getIdentifier("label_"+i, "id", mContext.getPackageName());
                dateTextView[i] = (TextView) itemView.findViewById(resID);
            }

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}