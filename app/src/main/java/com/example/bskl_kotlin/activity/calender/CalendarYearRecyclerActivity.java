package com.example.bskl_kotlin.activity.calender;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.activity.calender.adapter.CalendarCustomAdapter;
import com.example.bskl_kotlin.activity.calender.adapter.CustomList;
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr;
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarYearRecyclerActivity extends Activity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Context mContext;
    Bundle extras;
    int mdiff;
    int ydiff;
    int startMonth = 6;
    int startYear = 2018;
    int endYear = 2019;
    ArrayList<String> monthArrayList;
    ArrayList<String> yearArrayList;
    long startTime;
    RotateAnimation anim;
    Activity mActivity;
    ImageView closeCalendarYear;
    CalendarCustomAdapter customAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.recycle_main_activity);
        mContext = this;
        mActivity = this;
        monthArrayList = new ArrayList<>();
        yearArrayList = new ArrayList<>();
        extras = getIntent().getExtras();
        if (extras != null) {
            mdiff = extras.getInt("diffMonth");
            ydiff = extras.getInt("diffYear");
            startMonth = extras.getInt("startMonth");
            startYear = extras.getInt("startYear");
            endYear = extras.getInt("endYear");
            startTime = extras.getLong("startTime");
        }
        startMonth = startMonth - 1;
        TextView titleTxt = findViewById(R.id.titleTxt);

        if (startYear==endYear)
        {
            titleTxt.setText("" + startYear);

        }
        else
        {
            titleTxt.setText("" + startYear+" - "+endYear);

        }
        for (int i = 0; i <= mdiff; i++) {
            startMonth++;
            if (startMonth >= 12) {
                startMonth = 0;
                startYear++;
            }
            monthArrayList.add(String.valueOf(startMonth));
            yearArrayList.add(String.valueOf(startYear));

        }

        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.rvNumbers);
        closeCalendarYear = findViewById(R.id.closeCalendarYear);


        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                setData data=new setData();
                data.execute();


            }
        }, 100);





        recyclerView.addOnItemTouchListener(new RecyclerItemListener(mContext, recyclerView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {

                        final Date date = new Date(); // your date
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
//                        Date msgDate = new Date();
//                        try {
//
//                            msgDate = sdf.parse(calendar.get(Calendar.YEAR) + "-" + ListViewCalendar.currentMonth(position) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
//                        } catch (ParseException ex) {
//                            Log.e("Date", "Parsing error");
//
//                        }

//                        ListViewCalendar.calendar.next((ListViewCalendar.currentMonth(position) + position) - (calendar.get(Calendar.MONTH) + ListViewCalendar.currentMonth(position)));//january 2018-2019
                        if (position>=0 && position<=4) {
                            ListViewCalendar.calendar.nextView(ListViewCalendar.currentMonths(position), -1);
                        }else
                        {
                            ListViewCalendar.calendar.nextView(ListViewCalendar.currentMonths(position),0);

                        }


                        finish();
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));

        closeCalendarYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ListViewCalendar.listSpinner.setVisibility(View.GONE);
        ListViewCalendar.daySpinner.setText("Month View");
        ListViewCalendar.viewType = 1;

        ListViewCalendar.calendarVisibleGone();
        ListViewCalendar.mEventArrayListFilterList = new ArrayList<>();
        final Date date = new Date(); // your date

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (int j = 0; j < ListViewCalendar.eventArrayList.size(); j++) {
            if ((ListViewCalendar.currentMonth(calendar.get(Calendar.MONTH))) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getMonthNumber()) && (1900 + CustomCalendarView.currentCalendar.getTime().getYear()) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getYear())) {
                ListViewCalendar.mEventArrayListFilterList.add(ListViewCalendar.eventArrayList.get(j));
            }
        }

        CustomList mCustomListAdapter = new CustomList(mContext, ListViewCalendar.mEventArrayListFilterList);
        ListViewCalendar.list.setAdapter(mCustomListAdapter);
        ListViewCalendar.txtMYW.setText("This Month");
        ListViewCalendar.calendar.next((ListViewCalendar.currentMonth(ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1) + ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1) - (calendar.get(Calendar.MONTH) + ListViewCalendar.currentMonth(ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1)));

        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!PreferenceManagerr.getUserId(mContext).equalsIgnoreCase(""))
        {
          /*  AppControllerr().getInstance().getGoogleAnalyticsTracker().set("&uid", PreferenceManager().getUserId(mContext));
            AppController.getInstance().getGoogleAnalyticsTracker().set("&cid", PreferenceManager().getUserId(mContext));
            AppController.getInstance().trackScreenView("Calendar Year Screen" +" "+ PreferenceManager().getUserEmail(mContext)+Calendar.getInstance().getTime());
*/
        }


    }


    private class setData extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            customAdapter = new CalendarCustomAdapter(CalendarYearRecyclerActivity.this, monthArrayList, (Integer.valueOf(monthArrayList.get(0).toString())), startTime, null);


            return null;
        }
        @Override
        protected void onPostExecute(Void Void) {
           /* mProgressRelLayout.clearAnimation();
            mProgressRelLayout.setVisibility(View.GONE);*/
            //recyclerView.setAdapter(customAdapter);
            //
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    recyclerView.setAdapter(customAdapter);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            progressDialog.dismiss();


                        }
                    }, 7000);

                }
            });

            // progressDialog.dismiss();
        }
    }

}