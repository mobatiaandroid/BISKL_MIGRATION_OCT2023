package com.example.bskl_kotlin.activity.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.activity.calender.adapter.CustomList;
import com.example.bskl_kotlin.activity.calender.utils.CalendarListener;
import com.example.bskl_kotlin.activity.calender.utils.CalendarUtils;
import com.example.bskl_kotlin.activity.calender.utils.DayDecorator;
import com.example.bskl_kotlin.activity.calender.utils.DayView;
import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalendarView extends LinearLayout {
    private Context mContext;
    private View view;
    private ImageView previousMonthButton;
    private ImageView nextMonthButton;
    private CalendarListener calendarListener;
    public static Calendar currentCalendar;
    private Locale locale;
    private Date lastSelectedDay;
    private Typeface customTypeface;

    private int firstDayOfWeek = Calendar.SUNDAY;
    private List<DayDecorator> decorators = null;

    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_MONTH_TEXT = "dayOfMonthText";
    private static final String DAY_OF_MONTH_CONTAINER = "dayOfMonthContainer";

    private int disabledDayBackgroundColor;
    private int disabledDayTextColor;
    private int calendarBackgroundColor;
    private int calendarBackgroundColorHoliday;
    private Drawable selectedDayBackground;
    private Drawable currentDayOfMonthBackground;
    private int selectedDayBackgroundColor;
    private int weekLayoutBackgroundColor;
    private int calendarTitleBackgroundColor;
    private int selectedDayTextColor;
    private int calendarTitleTextColor;
    private int dayOfWeekTextColor;
    private int dayOfMonthTextColor;
    private int currentDayOfMonth;
    private int currentMonthIndex = 0;
    private boolean isOverflowDateVisible = true;
    TextView btnYearView;
    TextView btnMonthView;
    TextView btnWeekView;

    public CustomCalendarView(Context mContext) {
        this(mContext, null);
    }

    public CustomCalendarView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        ListViewCalendar.dateArrayList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }

        getAttributes(attrs);

        initializeCalendar();
    }

    private void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomCalendarView, 0, 0);
        calendarBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_calendarBackgroundColor, getResources().getColor(R.color.white));
        calendarBackgroundColorHoliday = typedArray.getColor(R.styleable.CustomCalendarView_calendarBackgroundColorHoliday, getResources().getColor(R.color.calendarbg));
        calendarTitleBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_titleLayoutBackgroundColor, getResources().getColor(R.color.white));
        calendarTitleTextColor = typedArray.getColor(R.styleable.CustomCalendarView_calendarTitleTextColor, getResources().getColor(R.color.black));
        weekLayoutBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_weekLayoutBackgroundColor, getResources().getColor(R.color.white));
        dayOfWeekTextColor = typedArray.getColor(R.styleable.CustomCalendarView_dayOfWeekTextColor, getResources().getColor(R.color.white));
        dayOfMonthTextColor = typedArray.getColor(R.styleable.CustomCalendarView_dayOfMonthTextColor, getResources().getColor(R.color.white));
        disabledDayBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_disabledDayBackgroundColor, getResources().getColor(R.color.white));
        disabledDayTextColor = typedArray.getColor(R.styleable.CustomCalendarView_disabledDayTextColor, getResources().getColor(R.color.white));


        selectedDayBackground = typedArray.getDrawable(R.styleable.CustomCalendarView_selectedDayBackground);
        currentDayOfMonthBackground = typedArray.getDrawable(R.styleable.CustomCalendarView_currentDayOfMonthBackground);
        selectedDayTextColor = typedArray.getColor(R.styleable.CustomCalendarView_selectedDayTextColor, getResources().getColor(R.color.white));
        currentDayOfMonth = typedArray.getColor(R.styleable.CustomCalendarView_currentDayOfMonthColor, getResources().getColor(R.color.white));
        typedArray.recycle();
    }


    private void initializeCalendar() {
        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.custom_calendar_layout, this, true);
        previousMonthButton = view.findViewById(R.id.leftButton);
        nextMonthButton = view.findViewById(R.id.rightButton);
        btnYearView = view.findViewById(R.id.btnYearView);
        btnMonthView = view.findViewById(R.id.btnMonthView);
        btnWeekView = view.findViewById(R.id.btnWeekView);

        btnWeekView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarListener.onWeekView(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);

            }
        });
        btnMonthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarListener.onMonthView(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);

            }
        });
        btnYearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarListener.onYearView(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);

            }
        });
        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex--;
                currentCalendar = Calendar.getInstance(Locale.getDefault());
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                refreshCalendar(currentCalendar);

                SimpleDateFormat month_date = new SimpleDateFormat("MM");
                SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
                Log.e("cmonth",month_date.format(currentCalendar.getTime()));
                String curryear=String.valueOf(year_date.format(currentCalendar.getTime()));
                String calCurrDate ="01-"+month_date.format(currentCalendar.getTime())+"-"+curryear;
                Log.e("currdate",calCurrDate);

                ListViewCalendar.callcalendarnewmonthskip(calCurrDate);

                if (calendarListener != null) {
                    calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
                }
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex++;
                currentCalendar = Calendar.getInstance(Locale.getDefault());

                // System.out.println("currentMonthIndex::" + currentMonthIndex);
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                refreshCalendar(currentCalendar);

                SimpleDateFormat month_date = new SimpleDateFormat("MM");
                SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
                Log.e("cmonth",month_date.format(currentCalendar.getTime()));
                String curryear=String.valueOf(year_date.format(currentCalendar.getTime()));
                String calCurrDate ="01-"+month_date.format(currentCalendar.getTime())+"-"+curryear;
                Log.e("currdate",calCurrDate);

                ListViewCalendar.callcalendarnewmonthskip(calCurrDate);
                if (calendarListener != null) {
                    calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
                }
            }
        });

        // Initialize calendar for current month
        Locale locale = mContext.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);
        setFirstDayOfWeek(Calendar.SUNDAY);
        refreshCalendar(currentCalendar);
    }

    public void calendarRefreshing(Context mContext) {
        currentMonthIndex++;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        // System.out.println("currentMonthIndex::" + currentMonthIndex);
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        refreshCalendar(currentCalendar);

        if (calendarListener != null) {
            calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
        }
        currentMonthIndex--;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);

        refreshCalendar(currentCalendar);
        if (calendarListener != null) {
            calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
        }

        Log.e("curretmonthskip",currentCalendar.getTime().toString());
        ListViewCalendar.mEventArrayListFilterList.clear();
        for (int j = 0; j < ListViewCalendar.eventArrayList.size(); j++) {
            if (ListViewCalendar.currentMonth(currentCalendar.getTime().getMonth()) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getMonthNumber()) && (1900 + currentCalendar.getTime().getYear()) == Integer.valueOf(ListViewCalendar.eventArrayList.get(j).getYear())) {
                ListViewCalendar.mEventArrayListFilterList.add(ListViewCalendar.eventArrayList.get(j));
            }
        }

        if(ListViewCalendar.mEventArrayListFilterList.size()==0)
        {
            ListViewCalendar.alertRelative.setVisibility(View.VISIBLE);
        }
        else
        {
            ListViewCalendar.alertRelative.setVisibility(View.GONE);

        }

        CustomList mCustomListAdapter = new CustomList(mContext, ListViewCalendar.mEventArrayListFilterList);
        ListViewCalendar.list.setAdapter(mCustomListAdapter);

    }
    public   void next(int murrentMonthIndex)
    {
        currentMonthIndex= murrentMonthIndex ;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);

        refreshCalendar(currentCalendar);
        if (calendarListener != null) {
            calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
        }
    }
    public   void nextView(int murrentMonthIndex,int mYear)
    {
        currentMonthIndex= murrentMonthIndex ;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        currentCalendar.add(Calendar.YEAR, mYear);

        refreshCalendar(currentCalendar);

        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        Log.e("cmonth",month_date.format(currentCalendar.getTime()));
        String curryear=String.valueOf(year_date.format(currentCalendar.getTime()));
        String calCurrDate ="01-"+month_date.format(currentCalendar.getTime())+"-"+curryear;
        Log.e("currdate",calCurrDate);

        ListViewCalendar.callcalendarnewmonthskip(calCurrDate);

        if (calendarListener != null) {
            calendarListener.onMonthChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
        }
    }
    public   void nexWeek(int murrentMonthIndex)
    {
        currentMonthIndex= murrentMonthIndex ;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);

        refreshCalendar(currentCalendar);
        if (calendarListener != null) {
            calendarListener.onWeekChanged(currentCalendar.getTime(),btnWeekView,btnMonthView,btnYearView);
        }
    }



    private void initializeTitleLayout() {
        View titleLayout = view.findViewById(R.id.titleLayout);
        titleLayout.setBackgroundColor(calendarTitleBackgroundColor);

        String dateText = new DateFormatSymbols(locale).getMonths()[currentCalendar.get(Calendar.MONTH)].toString();

        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());
        int year = currentCalendar.get(Calendar.YEAR);
        TextView dateTitle = view.findViewById(R.id.dateTitle);
        dateTitle.setTextColor(calendarTitleTextColor);
        dateTitle.setText(dateText + " " + year);
        dateTitle.setTextColor(calendarTitleTextColor);
        if (null != getCustomTypeface()) {
            dateTitle.setTypeface(getCustomTypeface(), Typeface.BOLD);
        }

    }



    @SuppressLint("DefaultLocale")
    private void initializeWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        //Setting background color white
        View titleLayout = view.findViewById(R.id.weekLayout);
        titleLayout.setBackgroundColor(weekLayoutBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols(locale).getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            if (dayOfTheWeekString.length() > 3) {
                dayOfTheWeekString = dayOfTheWeekString.substring(0, 3).toUpperCase();
            }

            dayOfWeek = view.findViewWithTag(DAY_OF_WEEK + getWeekIndex(i, currentCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            dayOfWeek.setTextColor(dayOfWeekTextColor);

            if (null != getCustomTypeface()) {
                dayOfWeek.setTypeface(getCustomTypeface());
            }
        }
    }

    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, calendar);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        final Calendar startCalendar = (Calendar) calendar.clone();
        //Add required number of days
        startCalendar.add(Calendar.DATE, -(dayOfMonthIndex - 1));
        int monthEndIndex = 42 - (actualMaximum + dayOfMonthIndex - 1);

        DayView dayView;
        ViewGroup dayOfMonthContainer;
        for (int i = 1; i < 43; i++) {
            dayOfMonthContainer = view.findViewWithTag(DAY_OF_MONTH_CONTAINER + i);
            dayView = view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            //  dayView.setClickable(false);
            //dayView.setOnClickListener(null);

            if (dayView == null)
                continue;

            //Apply the default styles
            dayOfMonthContainer.setOnClickListener(null);
            dayView.bind(startCalendar.getTime(), getDecorators());
            dayView.setVisibility(View.VISIBLE);

            if (null != getCustomTypeface()) {
                dayView.setTypeface(getCustomTypeface());
            }

            if (CalendarUtils.isSameMonth(calendar, startCalendar)) {
                // dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
//                dayView.setBackgroundColor(calendarBackgroundColor);
                dayView.setTextColor(dayOfWeekTextColor);
                if (ListViewCalendar.dateArrayList.size() > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date eventDate = null;
                    try {
                        for (int j = 0; j < ListViewCalendar.dateArrayList.size(); j++) {
                            eventDate = sdf.parse(ListViewCalendar.dateArrayList.get(j));
                            Calendar eventCalendar = Calendar.getInstance();
                            eventCalendar.setTime(eventDate);
                            if (CalendarUtils.isSameDay(startCalendar, eventCalendar)) {
                                if (ListViewCalendar.holidayArrayList.contains(ListViewCalendar.dateArrayList.get(j)))
                                {
                                    dayView.setBackgroundColor(calendarBackgroundColorHoliday);
                                    dayOfMonthContainer.setBackgroundColor(calendarBackgroundColorHoliday);
                                    dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);

                                }
                                else
                                {
                                    dayView.setBackgroundColor(calendarBackgroundColor);
                                    dayOfMonthContainer.setBackgroundColor(calendarBackgroundColor);
                                    dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.shape_circle));

                                }


                                break;

                            } else {

                                dayView.setBackgroundColor(calendarBackgroundColor);
                                dayOfMonthContainer.setBackgroundColor(calendarBackgroundColor);
                                dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.calendar_circle));

//                                dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.calendar_circle));

                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
//                    dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.calendar_circle));

                }
                //Set the current day color
                markDayAsCurrentDay(startCalendar);
            } else {
                dayView.setBackgroundColor(disabledDayBackgroundColor);
                dayView.setTextColor(disabledDayTextColor);
                dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.calendar_circle));
                dayOfMonthContainer.setBackgroundColor(calendarBackgroundColor);

                if (!isOverflowDateVisible())
                    dayView.setVisibility(View.GONE);
                else if (i >= 36 && ((float) monthEndIndex / 7.0f) >= 1) {
                    dayView.setVisibility(View.GONE);
                }
            }
            dayView.decorate();


            startCalendar.add(Calendar.DATE, 1);
            dayOfMonthIndex++;
        }

        // If the last week row has no visible days, hide it or show it in case
        ViewGroup weekRow = view.findViewWithTag("weekRow6");
        dayView = view.findViewWithTag("dayOfMonthText36");
        if (dayView.getVisibility() != VISIBLE) {
            weekRow.setVisibility(GONE);
        } else {
            weekRow.setVisibility(VISIBLE);
        }
    }

    private void clearDayOfTheMonthStyle(Date currentDate) {
        if (currentDate != null) {
            final Calendar calendar = getTodaysCalendar();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentDate);

            final DayView dayView = getDayOfMonthText(calendar);
            dayView.setBackgroundColor(calendarBackgroundColor);
            dayView.setTextColor(dayOfWeekTextColor);
            dayView.decorate();
        }
    }

    private DayView getDayOfMonthText(Calendar currentCalendar) {
        return (DayView) getView(DAY_OF_MONTH_TEXT, currentCalendar);
    }

    private int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int index = currentDay + monthOffset;
        return index;
    }

    private int getMonthOffset(Calendar currentCalendar) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {
            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();
        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {

            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        View childView = view.findViewWithTag(key + index);
        return childView;
    }

    private Calendar getTodaysCalendar() {
        Calendar currentCalendar = Calendar.getInstance(mContext.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        return currentCalendar;
    }

    @SuppressLint("DefaultLocale")
    public void refreshCalendar(Calendar currentCalendar) {
        CustomCalendarView.currentCalendar = currentCalendar;
        CustomCalendarView.currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        locale = mContext.getResources().getConfiguration().locale;

        // Set date title
        initializeTitleLayout();

        // Set weeks days titles
        initializeWeekLayout();

        // Initialize and set days in calendar
        setDaysInCalendar();

    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public void markDayAsCurrentDay(Calendar calendar) {
        if (calendar != null && CalendarUtils.isToday(calendar)) {
            DayView dayOfMonth = getDayOfMonthText(calendar);
            dayOfMonth.setTextColor(currentDayOfMonth);
            dayOfMonth.setBackground(currentDayOfMonthBackground);
        }
    }

    public void markDayAsSelectedDay(Date currentDate) {
        final Calendar currentCalendar = getTodaysCalendar();
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        currentCalendar.setTime(currentDate);

        // Clear previous marks
        clearDayOfTheMonthStyle(lastSelectedDay);


        // Store current values as last values
        storeLastValues(currentDate);

        // Mark current day as selected
        DayView view = getDayOfMonthText(currentCalendar);
//        view.setBackgroundColor(selectedDayBackground);
        view.setBackground(selectedDayBackground);
        view.setTextColor(selectedDayTextColor);
    }

    private void storeLastValues(Date currentDate) {
        lastSelectedDay = currentDate;
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    private View.OnClickListener onDayOfMonthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_MONTH_CONTAINER.length(), tagId.length());
            final TextView dayOfMonthText = view.findViewWithTag(DAY_OF_MONTH_TEXT + tagId);

            // Fire event
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));
            markDayAsSelectedDay(calendar.getTime());

            //Set the current day color
            markDayAsCurrentDay(currentCalendar);

            /*if (calendarListener != null) {
                CalendarListener.onDateSelected(calendar.getTime());
            }*/
        }
    };

    public List<DayDecorator> getDecorators() {
        return decorators;
    }

    public void setDecorators(List<DayDecorator> decorators) {
        this.decorators = decorators;
    }

    public boolean isOverflowDateVisible() {
        return isOverflowDateVisible;
    }

    public void setShowOverflowDate(boolean isOverFlowEnabled) {
        isOverflowDateVisible = isOverFlowEnabled;
    }

    public void setCustomTypeface(Typeface customTypeface) {
        this.customTypeface = customTypeface;
    }

    public Typeface getCustomTypeface() {
        return customTypeface;
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }


}
