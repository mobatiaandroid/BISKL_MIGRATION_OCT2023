package com.example.bskl_kotlin.activity.calender;

import static com.example.bskl_kotlin.activity.calender.ListViewCalendar.calendarVisibleGone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.activity.calender.adapter.CustomList;
import com.example.bskl_kotlin.activity.calender.model.CalenderHolidayResponseModel;
import com.example.bskl_kotlin.fragment.attendance.APIClient;
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr;
import com.example.bskl_kotlin.fragment.calendar.adapter.MyRecyclerViewAdapter;
import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel;
import com.example.bskl_kotlin.fragment.calendar.model.StudentDetailModelJava;
import com.example.bskl_kotlin.manager.countrypicker.AppControllerr;
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener;
import com.example.bskl_kotlin.rest_api.ApiInterface;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarYearActivity extends Activity {

    Context ncontext;
    MyRecyclerViewAdapter adapter;
    Boolean isCalendarVisible = false;
    TextView titleTxt;
    ArrayList<CalendarModel> holidayarraylist;
    ImageView closeCalendarYear;
    private String[] dayNames = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
    private String[] monthNames = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_main_activity);
        ncontext=this;
        Log.e("calen","cal year");
        titleTxt=(TextView)findViewById(R.id.titleTxt);
        closeCalendarYear=(ImageView)findViewById(R.id.closeCalendarYear);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String yearName = year_date.format(cal.getTime());
        final int currentYearNumber = Integer.parseInt(yearName);

        SimpleDateFormat month_date = new SimpleDateFormat("MM", Locale.ENGLISH);
        String currentMonthName = month_date.format(cal.getTime());
        int currentMonthNumber = Integer.parseInt(currentMonthName); // Device current month
        closeCalendarYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        String titleString = "";
        if (currentMonthNumber<7) { // JAN - JUL
            titleString = (currentYearNumber-1)+" - "+currentYearNumber;
            titleTxt.setText(titleString);
        }else {                     // AUG - DEC
            titleString = currentYearNumber+" - "+(currentYearNumber+1);
            titleTxt.setText(titleString);

        }
        //  Log.d("LOG11","titleString= "+titleString);

        final String[] data = {"7", "8", "9", "10", "11", "0", "1", "2", "3", "4", "5", "6",};
        //Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> holidaysArray = (ArrayList<String>) getIntent().getSerializableExtra("BUNDLE");
        // holidayapi(ncontext);
        //ListViewCalendar.holidayapi();
        //holidaysArray.addAll(ListViewCalendar.holidayArrayListYear);

        //holidaysArray.addAll(AppControllerr.holidayArrayListYear);
        System.out.println("holiday arraylist"+holidaysArray.size());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        //recyclerView.setAlpha(0.0f);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data, holidaysArray);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemListener(ncontext, recyclerView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        int monthNumber = Integer.parseInt(data[position].toString());
                        System.out.println("printed sucessfully");
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
/*
                        System.out.println("****monthnumber ::"+monthNumber);
                        System.out.println("****monthname ::"+monthName);
                        System.out.println("****yearname ::"+yearNumber);
                        System.out.println("****current ::"+currentYearNumber);
                        System.out.println("****currentmonth ::"+currentMonthName);*/


                        int monthToBePassed = (monthNumber - currentMonthNumber)+1;
                        int yearToBePassed  = (yearNumber-currentYearNumber);
                        ListViewCalendar.calendar.nextView(monthToBePassed,yearToBePassed);






                        finish();
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }
    /*public static void holidayapi(Context ncontext){
        ApiInterface service =
                APIClient.getRetrofitInstance().create(ApiInterface.class);
        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("user_ids", PreferenceManagerr.getUserId(ncontext)
        );
        Call<CalenderHolidayResponseModel> call = service.publichoilday_calender_view("Bearer "+PreferenceManagerr.getAccessToken(ncontext),paramObject);

        call.enqueue(new Callback<CalenderHolidayResponseModel>()
        {
            @Override
            public void onResponse(Call<CalenderHolidayResponseModel> call, Response<CalenderHolidayResponseModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CalenderHolidayResponseModel apiResponse = response.body();
                        //JSONObject obj = new JSONObject(String.valueOf(response));
                        //Log.e("response", String.valueOf(apiResponse));
                        System.out.println("response" + apiResponse);

                        //JSONObject secobj = obj.getJSONObject(String.valueOf(response));
                        // obj = new JSONObject(String.valueOf(response));

                        String respObject = response.body().getStatuscode();
                        String statuscode = apiResponse.getStatuscode();
                        //Log.e("response", statuscode);

                        if (statuscode.equals("303")) {
                            ArrayList<CalendarModel> dataArray = response.body().getData();
                            if (dataArray.size() > 0) {
                               // dateArrayList = new ArrayList<>();
                                //holidayArrayList = new ArrayList<>();

                                //eventArrayList = new ArrayList<CalendarModel>();
                                AppControllerr.dateArrayListYear = new ArrayList<>();
                                for (int i = 0; i < dataArray.size(); i++) {
                                    CalendarModel eventObject = dataArray.get(i);
                                    CalendarModel model = new CalendarModel();
                                    model.setId(eventObject.getId());
                                    model.setTittle(eventObject.getTittle());
                                    model.setPublic_holiday(eventObject.getPublic_holiday());
                                    model.setDescription(eventObject.getDescription());
                                    model.setVenue(eventObject.getVenue());
                                    model.setDaterange(eventObject.getDaterange());
                                    model.setSelectweek(eventObject.getSelectweek());
                                    model.setEnddate(eventObject.getEnddate());
                                    model.setEndDateList(eventObject.getEnddate());
                                    model.setEndDateNew(eventObject.getEnddate());
                                    model.setDate(eventObject.getDate());
                                    model.setStartDateNew(eventObject.getDate());
                                    model.setDateCalendar(eventObject.getDate());
                                    if (eventObject.getPublic_holiday()==1) {

                                        //holidayArrayList.add(eventObject.getDate());
                                        //holidayArrayListYear.add(eventObject.getDate());
                                    }
                                    model.setStarttime(eventObject.getStarttime());
                                    model.setEndtime(eventObject.getEndtime());
                                    model.setStartDatetime(eventObject.getDate() + " " + eventObject.getStarttime());
                                    model.setEndDatetime(eventObject.getEnddate() + " " + eventObject.getEndtime());
                                    model.setIsAllday(eventObject.getIsAllday());
                                    String mDate = model.getDate();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                    Date msgDate = new Date();
                                    try {

                                        msgDate = sdf.parse(mDate);
                                    } catch (ParseException ex) {
                                        Log.e("Date", "Parsing error");

                                    }

                                    //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                    String day = (String) DateFormat.format("dd", msgDate); // 20
                                    if (day.endsWith("1") && !day.endsWith("11")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'st' MMMM");

                                    } else if (day.endsWith("2") && !day.endsWith("12")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMMM");

                                    } else if (day.endsWith("3") && !day.endsWith("13"))
                                        sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMMM");
                                    else
                                        sdfcalender = new SimpleDateFormat("EEEE  d'th' MMMM");

                                    String newday = sdfcalender.format(msgDate);
                                    model.setDate(newday);
                                    *//*********End Date****************//*
                                    String mEndDate = model.getEndDateList();
                                    SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                    Date msgEndDate = new Date();
                                    try {

                                        msgEndDate = sdfEnd.parse(mEndDate);
                                    } catch (ParseException ex) {
                                        Log.e("Date", "Parsing error");

                                    }

                                    //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                    String dayEnd = (String) DateFormat.format("dd", msgEndDate); // 20
                                    if (dayEnd.endsWith("1") && !dayEnd.endsWith("11")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'st' MMMM");

                                    } else if (dayEnd.endsWith("2") && !dayEnd.endsWith("12")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMMM");

                                    } else if (dayEnd.endsWith("3") && !dayEnd.endsWith("13"))
                                        sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMMM");
                                    else
                                        sdfcalender = new SimpleDateFormat("EEEE  d'th' MMMM");

                                    String newEndday = sdfcalender.format(msgEndDate);
                                    model.setEndDateList(newEndday);
                                    *//*********End Date****************//*
                                    *//************** start date new*************//*

                                    String mStartDateNew = model.getStartDateNew();
                                    SimpleDateFormat sdfstartDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                    Date msgStartDate = new Date();
                                    try {

                                        msgStartDate = sdfstartDate.parse(mStartDateNew);//
                                    } catch (ParseException ex) {
                                        Log.e("Date", "Parsing error");

                                    }

                                    //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                    String dayStart = (String) DateFormat.format("dd", msgDate); // 20
                                    if (dayStart.endsWith("1") && !dayStart.endsWith("11")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'st' MMM");

                                    } else if (dayStart.endsWith("2") && !dayStart.endsWith("12")) {
                                        sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMM");

                                    } else if (dayStart.endsWith("3") && !dayStart.endsWith("13"))
                                        sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMM");
                                    else
                                        sdfcalender = new SimpleDateFormat("EEEE  d'th' MMM");

                                    String newdayStart = sdfcalender.format(msgStartDate);
                                    model.setStartDateNew(newdayStart);

                                    *//************************end of start date new*****************//*


                                    *//************** end date new*************//*

                                    String mEndDateNew = model.getEndDateNew();
                                    SimpleDateFormat sdfendDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                    Date msgEndDateNew = new Date();
                                    try {

                                        msgEndDateNew = sdfendDate.parse(mEndDateNew);//
                                    } catch (ParseException ex) {
                                        Log.e("Date", "Parsing error");

                                    }

                                    //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                    String dayEndNew = (String) DateFormat.format("dd", msgEndDateNew); // 20
                                    if (dayEndNew.endsWith("1") && !dayEndNew.endsWith("11")) {
                                        sdfcalender = new SimpleDateFormat("d'st' MMM");

                                    } else if (dayEndNew.endsWith("2") && !dayEndNew.endsWith("12")) {
                                        sdfcalender = new SimpleDateFormat("d'nd' MMM");

                                    } else if (dayEndNew.endsWith("3") && !dayEndNew.endsWith("13"))
                                        sdfcalender = new SimpleDateFormat("d'rd' MMM");
                                    else
                                        sdfcalender = new SimpleDateFormat("d'th' MMM");

                                    String newdayEnd = sdfcalender.format(msgEndDateNew);
                                    model.setEndDateNew(newdayEnd);

                                    *//************************end of end date new*****************//*


                                    SimpleDateFormat formatEEE = new SimpleDateFormat("EEE", Locale.ENGLISH);
                                    SimpleDateFormat formatdd = new SimpleDateFormat("dd", Locale.ENGLISH);
                                    SimpleDateFormat formatMMM = new SimpleDateFormat("MMM", Locale.ENGLISH);
                                    SimpleDateFormat formatMM = new SimpleDateFormat("MM", Locale.ENGLISH);
                                    SimpleDateFormat formatyyyy = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                                    Date date = null;
                                    try {
                                        date = sdf.parse(eventObject.getDate());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String dayOfTheWeek = formatEEE.format(date); // Thu
                                    String days = formatdd.format(date); // 20
                                    String monthString = formatMMM.format(date); // Jun
                                    String monthNumber = formatMM.format(date); // 06
                                    String year = formatyyyy.format(date); // 2

                                    model.setDayOfTheWeek(dayOfTheWeek);
                                    model.setDay(days);
                                    model.setMonthString(monthString);
                                    model.setMonthNumber(monthNumber);
                                    model.setYear(year);
                                    ArrayList<StudentDetailModelJava> studentArray = eventObject.getmStudentModel();
                                    ArrayList<StudentDetailModelJava> mStudentModel = new ArrayList<>();


                                    for (int x = 0; x < studentArray.size(); x++) {
                                        StudentDetailModelJava obj2 = studentArray.get(x);
                                        StudentDetailModelJava xmodel = new StudentDetailModelJava();
                                        xmodel.setStudent_id(obj2.getStudent_id());
                                        xmodel.setName(obj2.getName());
                                        mStudentModel.add(xmodel);
                                    }
                                    model.setmStudentModel(mStudentModel);
//                                            eventArrayList.add(model);
                                    if (eventObject.getDaterange().equalsIgnoreCase("")) {
                                        dateArrayList.add(eventObject.getDate());

                                        AppControllerr.dateArrayListYear.add(eventObject.getDate());

                                    } else {
                                        ArrayList<String> dayRepeatList = new ArrayList<String>();
                                        String[] daterangeListValues = eventObject.getDaterange().split(",");
                                        String[] weekRangeValues = eventObject.getSelectweek().split(",");
                                        dateArrayList.add(eventObject.getDate());

                                        AppControllerr.dateArrayListYear.add(eventObject.getDate());
                                        for (int j = 0; j < daterangeListValues.length; j++) {
                                            dateArrayList.add(daterangeListValues[j]);
                                            if (eventObject.getPublic_holiday()==1) {
                                                holidayArrayList.add(daterangeListValues[j]);
                                                holidayArrayListYear.add(daterangeListValues[j]);
                                            }
                                            AppControllerr.dateArrayListYear.add(daterangeListValues[j]);
//                                                    Date dateRepeat = null;
//                                                    try {
//                                                        dateRepeat = sdf.parse(daterangeListValues[j]);
//                                                    } catch (ParseException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    String dayOfTheWeekRepeat = (String) formatEEE.format(dateRepeat);
//                                                    dayRepeatList.add(dayOfTheWeekRepeat);
                                        }
                                        adapter = new MyRecyclerViewAdapter(mContext, data, dateArrayList);
//                                                Set<String> hs = new HashSet<>();
//                                                hs.addAll(dayRepeatList);
//                                                dayRepeatList.clear();
//                                                dayRepeatList.addAll(hs);
                                        for (int k = 0; k < weekRangeValues.length; k++) {
                                            switch (weekRangeValues[k]) {
                                                case "0":
                                                    dayRepeatList.add("Sun");
                                                    break;
                                                case "1":
                                                    dayRepeatList.add("Mon");
                                                    break;
                                                case "2":
                                                    dayRepeatList.add("Tue");
                                                    break;
                                                case "3":
                                                    dayRepeatList.add("Wed");
                                                    break;
                                                case "4":
                                                    dayRepeatList.add("Thu");
                                                    break;
                                                case "5":
                                                    dayRepeatList.add("Fri");
                                                    break;
                                                case "6":
                                                    dayRepeatList.add("Sat");
                                                    break;

                                            }
                                        }
                                        String dayRepeatString = dayRepeatList.toString().replace("[", "").replace("]", "");

                                        dateArrayList.add(eventObject.getEnddate());
                                        AppControllerr.dateArrayListYear.add(eventObject.getEnddate());
//                                                model.setDayrange(dayRepeatString);

                                        if (dayRepeatList.size() < 7) {
                                            model.setDayrange(dayRepeatString);
                                        } else {
                                            model.setDayrange("Everyday");

                                        }
                                    }

                                    //eventArrayList.add(model);

                                }
                                calendar.calendarRefreshing(mContext);

                            } else {
                                alertRelative.setVisibility(View.VISIBLE);

                            }

                        } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                            Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show();
                        }



                    } catch (Exception ex) {
                        System.out.println("The Exception in attendence" + ex.toString());
                    }
                }
            }



            @Override
            public void onFailure(Call<CalenderHolidayResponseModel> call, Throwable t) {

                Log.e("toadst", String.valueOf(t));
                //  Toast.makeText(mContext, "Successfully sent email to staff"+t, Toast.LENGTH_SHORT);

            }});*/
      /*  try {
            Log.e("token",PreferenceManagerr.getAccessToken(mContext));
            Log.e("us",PreferenceManager().getUserId(mContext));

            final VolleyWrapper manager = new VolleyWrapper(URL_CALENDAR_HOLIDAY);
            String[] name = {JTAG_ACCESSTOKEN, JTAG_USER_IDS};
            String[] value = {PreferenceManager().getAccessToken(mContext), PreferenceManager().getUserId(mContext)};
            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

                @Override
                public void responseSuccess(String successResponse) {
                    String responsCode = "";
                    System.out.println("NofifyRes:" + successResponse);

                    if (successResponse != null) {
                        try {
                            JSONObject obj = new JSONObject(successResponse);
                            String response_code = obj.getString(JTAG_RESPONSECODE);
                            if (response_code.equalsIgnoreCase("200")) {
                                JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                                String status_code = secobj.getString(JTAG_STATUSCODE);
                                if (status_code.equalsIgnoreCase("303")) {
                                    JSONArray dataArray = secobj.optJSONArray("data");
                                    if (dataArray.length() > 0) {
                                        dateArrayList = new ArrayList<>();
                                        holidayArrayList = new ArrayList<>();
                                        //eventArrayList = new ArrayList<CalendarModel>();
                                        AppController().dateArrayListYear = new ArrayList<>();
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject eventObject = dataArray.optJSONObject(i);
                                            CalendarModel model = new CalendarModel();
                                            model.setId(eventObject.getId());
                                            model.setTittle(eventObject.getTittle());
                                            model.setPublic_holiday(eventObject.getPublic_holiday());
                                            model.setDescription(eventObject.getDescription());
                                            model.setVenue(eventObject.getVenue());
                                            model.setDaterange(eventObject.getDaterange());
                                            model.setSelectweek(eventObject.getSelectweek());
                                            model.setEnddate(eventObject.getEnddate());
                                            model.setEndDateList(eventObject.getEnddate());
                                            model.setEndDateNew(eventObject.getEnddate());
                                            model.setDate(eventObject.getDate());
                                            model.setStartDateNew(eventObject.getDate());
                                            model.setDateCalendar(eventObject.getDate());
                                            if (eventObject.getPublic_holiday().equalsIgnoreCase("1")) {
                                                holidayArrayList.add(eventObject.getDate());
                                                holidayArrayListYear.add(eventObject.getDate());
                                            }
                                            model.setStarttime(eventObject.getStarttime());
                                            model.setEndtime(eventObject.getEndtime());
                                            model.setStartDatetime(eventObject.getDate() + " " + eventObject.getStarttime());
                                            model.setEndDatetime(eventObject.getEnddate() + " " + eventObject.getEndtime());
                                            model.setIsAllday(eventObject.getIsAllday());
                                            String mDate = model.getDate();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                            Date msgDate = new Date();
                                            try {

                                                msgDate = sdf.parse(mDate);
                                            } catch (ParseException ex) {
                                                Log.e("Date", "Parsing error");

                                            }

                                            //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                            String day = (String) DateFormat.format("dd", msgDate); // 20
                                            if (day.endsWith("1") && !day.endsWith("11")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'st' MMMM");

                                            } else if (day.endsWith("2") && !day.endsWith("12")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMMM");

                                            } else if (day.endsWith("3") && !day.endsWith("13"))
                                                sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMMM");
                                            else
                                                sdfcalender = new SimpleDateFormat("EEEE  d'th' MMMM");

                                            String newday = sdfcalender.format(msgDate);
                                            model.setDate(newday);
********End Date***************

                                            String mEndDate = model.getEndDateList();
                                            SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                            Date msgEndDate = new Date();
                                            try {

                                                msgEndDate = sdfEnd.parse(mEndDate);
                                            } catch (ParseException ex) {
                                                Log.e("Date", "Parsing error");

                                            }

                                            //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                            String dayEnd = (String) DateFormat.format("dd", msgEndDate); // 20
                                            if (dayEnd.endsWith("1") && !dayEnd.endsWith("11")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'st' MMMM");

                                            } else if (dayEnd.endsWith("2") && !dayEnd.endsWith("12")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMMM");

                                            } else if (dayEnd.endsWith("3") && !dayEnd.endsWith("13"))
                                                sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMMM");
                                            else
                                                sdfcalender = new SimpleDateFormat("EEEE  d'th' MMMM");

                                            String newEndday = sdfcalender.format(msgEndDate);
                                            model.setEndDateList(newEndday);
********End Date***************

************* start date new************


                                            String mStartDateNew = model.getStartDateNew();
                                            SimpleDateFormat sdfstartDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                            Date msgStartDate = new Date();
                                            try {

                                                msgStartDate = sdfstartDate.parse(mStartDateNew);//
                                            } catch (ParseException ex) {
                                                Log.e("Date", "Parsing error");

                                            }

                                            //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                            String dayStart = (String) DateFormat.format("dd", msgDate); // 20
                                            if (dayStart.endsWith("1") && !dayStart.endsWith("11")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'st' MMM");

                                            } else if (dayStart.endsWith("2") && !dayStart.endsWith("12")) {
                                                sdfcalender = new SimpleDateFormat("EEEE  d'nd' MMM");

                                            } else if (dayStart.endsWith("3") && !dayStart.endsWith("13"))
                                                sdfcalender = new SimpleDateFormat("EEEE  d'rd' MMM");
                                            else
                                                sdfcalender = new SimpleDateFormat("EEEE  d'th' MMM");

                                            String newdayStart = sdfcalender.format(msgStartDate);
                                            model.setStartDateNew(newdayStart);

***********************end of start date new****************



************* end date new************


                                            String mEndDateNew = model.getEndDateNew();
                                            SimpleDateFormat sdfendDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                                            Date msgEndDateNew = new Date();
                                            try {

                                                msgEndDateNew = sdfendDate.parse(mEndDateNew);//
                                            } catch (ParseException ex) {
                                                Log.e("Date", "Parsing error");

                                            }

                                            //String dayOfTheWeek = (String) DateFormat.format("EEEE", msgDate); // Thursday
                                            String dayEndNew = (String) DateFormat.format("dd", msgEndDateNew); // 20
                                            if (dayEndNew.endsWith("1") && !dayEndNew.endsWith("11")) {
                                                sdfcalender = new SimpleDateFormat("d'st' MMM");

                                            } else if (dayEndNew.endsWith("2") && !dayEndNew.endsWith("12")) {
                                                sdfcalender = new SimpleDateFormat("d'nd' MMM");

                                            } else if (dayEndNew.endsWith("3") && !dayEndNew.endsWith("13"))
                                                sdfcalender = new SimpleDateFormat("d'rd' MMM");
                                            else
                                                sdfcalender = new SimpleDateFormat("d'th' MMM");

                                            String newdayEnd = sdfcalender.format(msgEndDateNew);
                                            model.setEndDateNew(newdayEnd);

***********************end of end date new****************



                                            SimpleDateFormat formatEEE = new SimpleDateFormat("EEE", Locale.ENGLISH);
                                            SimpleDateFormat formatdd = new SimpleDateFormat("dd", Locale.ENGLISH);
                                            SimpleDateFormat formatMMM = new SimpleDateFormat("MMM", Locale.ENGLISH);
                                            SimpleDateFormat formatMM = new SimpleDateFormat("MM", Locale.ENGLISH);
                                            SimpleDateFormat formatyyyy = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                                            Date date = null;
                                            try {
                                                date = sdf.parse(eventObject.getDate());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            String dayOfTheWeek = formatEEE.format(date); // Thu
                                            String days = formatdd.format(date); // 20
                                            String monthString = formatMMM.format(date); // Jun
                                            String monthNumber = formatMM.format(date); // 06
                                            String year = formatyyyy.format(date); // 2

                                            model.setDayOfTheWeek(dayOfTheWeek);
                                            model.setDay(days);
                                            model.setMonthString(monthString);
                                            model.setMonthNumber(monthNumber);
                                            model.setYear(year);
                                            JSONArray studentArray = eventObject.optJSONArray("students");
                                            ArrayList<StudentDetailModel> mStudentModel = new ArrayList<>();


                                            for (int x = 0; x < studentArray.length(); x++) {
                                                JSONObject obj2 = studentArray.optJSONObject(x);
                                                StudentDetailModel xmodel = new StudentDetailModel();
                                                xmodel.setId(obj2.optString("student_id"));
                                                xmodel.setStudentName(obj2.optString("name"));
                                                mStudentModel.add(xmodel);
                                            }
                                            model.setmStudentModel(mStudentModel);
//                                            eventArrayList.add(model);
                                            if (eventObject.getDaterange().equalsIgnoreCase("")) {
                                                dateArrayList.add(eventObject.getDate());

                                                AppController().dateArrayListYear.add(eventObject.getDate());

                                            } else {
                                                ArrayList<String> dayRepeatList = new ArrayList<String>();
                                                String[] daterangeListValues = eventObject.getDaterange().split(",");
                                                String[] weekRangeValues = eventObject.getSelectweek().split(",");
                                                dateArrayList.add(eventObject.getDate());

                                                AppController().dateArrayListYear.add(eventObject.getDate());
                                                for (int j = 0; j < daterangeListValues.length; j++) {
                                                    dateArrayList.add(daterangeListValues[j]);
                                                    if (eventObject.getPublic_holiday().equalsIgnoreCase("1")) {
                                                        holidayArrayList.add(daterangeListValues[j]);
                                                        holidayArrayListYear.add(daterangeListValues[j]);
                                                    }
                                                    AppController().dateArrayListYear.add(daterangeListValues[j]);
//                                                    Date dateRepeat = null;
//                                                    try {
//                                                        dateRepeat = sdf.parse(daterangeListValues[j]);
//                                                    } catch (ParseException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    String dayOfTheWeekRepeat = (String) formatEEE.format(dateRepeat);
//                                                    dayRepeatList.add(dayOfTheWeekRepeat);
                                                }
                                                adapter = new MyRecyclerViewAdapter(mContext, data, dateArrayList);
//                                                Set<String> hs = new HashSet<>();
//                                                hs.addAll(dayRepeatList);
//                                                dayRepeatList.clear();
//                                                dayRepeatList.addAll(hs);
                                                for (int k = 0; k < weekRangeValues.length; k++) {
                                                    switch (weekRangeValues[k]) {
                                                        case "0":
                                                            dayRepeatList.add("Sun");
                                                            break;
                                                        case "1":
                                                            dayRepeatList.add("Mon");
                                                            break;
                                                        case "2":
                                                            dayRepeatList.add("Tue");
                                                            break;
                                                        case "3":
                                                            dayRepeatList.add("Wed");
                                                            break;
                                                        case "4":
                                                            dayRepeatList.add("Thu");
                                                            break;
                                                        case "5":
                                                            dayRepeatList.add("Fri");
                                                            break;
                                                        case "6":
                                                            dayRepeatList.add("Sat");
                                                            break;

                                                    }
                                                }
                                                String dayRepeatString = dayRepeatList.toString().replace("[", "").replace("]", "");

                                                dateArrayList.add(eventObject.getEnddate());
                                                AppController().dateArrayListYear.add(eventObject.getEnddate());
//                                                model.setDayrange(dayRepeatString);

                                                if (dayRepeatList.size() < 7) {
                                                    model.setDayrange(dayRepeatString);
                                                } else {
                                                    model.setDayrange("Everyday");

                                                }
                                            }

                                            //eventArrayList.add(model);

                                        }
                                        calendar.calendarRefreshing(mContext);

                                    } else {
                                        alertRelative.setVisibility(View.VISIBLE);

                                    }
                                } else if (status_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        status_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    holidayapi();
                                    //callcalendarnewmonthskip(datepassed);
                                    //callCalendarList();

                                }
                            } else if (response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                    response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                    @Override
                                    public void getAccessToken() {
                                    }
                                });
                                holidayapi();
                                //callcalendarnewmonthskip(datepassed);
                                //callCalendarList();

                            } else {
                                Toast.makeText(mContext, "Some Error Occured", Toast.LENGTH_SHORT).show();

                            }

 Intent intent = new Intent(getActivity(), CalendarYearActivity.class);
                            intent.putExtra("BUNDLE", (Serializable) AppController().holidayArrayListYear);
                            startActivity(intent);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Intent intent = new Intent(getActivity(), CalendarYearActivity.class);
                        intent.putExtra("BUNDLE", (Serializable) AppController().holidayArrayListYear);
                        startActivity(intent);
                    }
                }

                @Override
                public void responseFailure(String failureResponse) {
                    // CustomStatusDialog(RESPONSE_FAILURE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/




//    public void displayCalendarInMainView(View v) {
//        Log.d("LOG22","displayCalendarInMainView...");
//        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
//        if (isCalendarVisible == true) {
//            isCalendarVisible = false;
//            recyclerView.setAlpha(0.0f);
//        }else {
//            isCalendarVisible = true;
//            recyclerView.setAlpha(1.0f);
//        }
//    }


    public void onBackPressed() {
        ListViewCalendar.listSpinner.setVisibility(View.GONE);
        ListViewCalendar.daySpinner.setText("Month View");
        ListViewCalendar.viewType = 1;

        calendarVisibleGone();
        ListViewCalendar.mEventArrayListFilterList = new ArrayList<>();
        final Date date = new Date(); // your date

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ListViewCalendar.callCalendarMonthNew();;
        for (int j = 0; j < ListViewCalendar.eventArrayList.size(); j++) {
            if ((ListViewCalendar.currentMonth(calendar.get(Calendar.MONTH))) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getMonthNumber()) && (1900 + CustomCalendarView.currentCalendar.getTime().getYear()) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getYear())) {
                ListViewCalendar.mEventArrayListFilterList.add(ListViewCalendar.eventArrayList.get(j));
            }
        }

        CustomList mCustomListAdapter = new CustomList(ncontext, ListViewCalendar.mEventArrayListFilterList);
        ListViewCalendar.list.setAdapter(mCustomListAdapter);
        ListViewCalendar.txtMYW.setText("This Month");
        ListViewCalendar.calendar.next((ListViewCalendar.currentMonth(ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1) + ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1) - (calendar.get(Calendar.MONTH) + ListViewCalendar.currentMonth(ListViewCalendar.currentMonth(calendar.getTime().getMonth()) - 1)));

        finish();
    }

}