/*
package com.example.bskl_kotlin.fragment.calendar


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.calendar.CalendarInfoActivity
import com.example.bskl_kotlin.activity.calender.adapter.MyRecyclerViewAdapter
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.calendar.adapter.ListViewSpinnerAdapter
import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel
import com.example.bskl_kotlin.fragment.calendar.model.ListViewSpinnerModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

class CalendarFragment (title: String, tabId: String) : Fragment() , View.OnClickListener,
    AdapterView.OnItemSelectedListener{

    lateinit var mContext: Context
    lateinit var list: RecyclerView
    var event = arrayOf(
        "Maths assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "Maths assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm",
        "English assessment will be conducted from: 10 am to 1 pm"
    )
    var dataArrayStrings: ArrayList<String?> = object : ArrayList<String?>() {
        init {
            add("Year View")
            add("Month View")
            add("Week View")
        }
    }
    var calendar: CustomCalendarView? = null
    var arrow: ImageView? = null
    var arrowUp: ImageView? = null
    var listRelative: RelativeLayout? = null
    var listMainRelative: RelativeLayout? = null
    var mainRelative: RelativeLayout? = null

    var dateArrayList: ArrayList<String>? = null
    var holidayArrayList: ArrayList<String>? = null
    var eventArrayList: ArrayList<CalendarModel>? = null
    var holidayArrayListYear=ArrayList<String>()
    var sdfcalender: SimpleDateFormat? = null
    var myFormatCalender = "yyyy-MM-dd"
    var mEventArrayListFilterList: ArrayList<CalendarModel>? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var viewType = 1
    lateinit var startDate: Calendar
    lateinit var currentDate: Calendar
    lateinit var listItem: Array<String>
    lateinit var endDate: Calendar
    lateinit var txtMYW: TextView
    private var spinnnerList: ListView? = null
    lateinit var listSpinner: RelativeLayout
    var txtSpinner: LinearLayout? = null
    var daySpinner: TextView? = null
    var spinner: Spinner? = null
    var alertRelative: RelativeLayout? = null
    lateinit var noDataAlertTxt: TextView
    lateinit var startCalendar: Calendar
    lateinit var endCalendar:Calendar
    private var mListViewArray: ArrayList<ListViewSpinnerModel>? = null
    var data = arrayOf("7", "8", "9", "10", "11", "0", "1", "2", "3", "4", "5", "6")
    var adapter: MyRecyclerViewAdapter? = null
    var diffMonth = 0
    var diffYear:Int = 0
    var diffInc:Int = 0
    private var daySpinSelect = true
    var startMonth = 7
    var startYear = 2018
    var endYear = 2019
    var endMonth = 6
    var isShowCalendar = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Calendar"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()
        //holidayapi();
        if (AppUtils().isNetworkConnected(mContext)) {
            clearBadge()
            //holidayapi();
            callCalendarMonthNew()

            //callCalendarListMonth();

            //callCalendarList();
            if (isShowCalendar) {
                calendarVisibleGone()
                println("test1") //hiding
            } else {
                calendarVisible()
                println("test2") //showing
                if (daySpinSelect == false) {
                    setVisibility(
                        View.GONE
                    )
                    daySpinSelect = true
                }
                calendarVisible()
            }
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
        if (PreferenceManager().getIsFirstTimeInCalendar(mContext)) {
            PreferenceManager().setIsFirstTimeInCalendar(
                mContext,
                false
            )
            val mintent: Intent = Intent(
                mContext,
                CalendarInfoActivity::class.java
            )
            mintent.putExtra("type", 1)
            mContext.startActivity(mintent)
        }
        startDate = Calendar.getInstance()
        currentDate = Calendar.getInstance()
        endDate = Calendar.getInstance()
        if (endMonth < currentDate.getTime().month) {
//           diffInc= currentDate.getTime().getYear()+ 1900-startYear;
            startYear = currentDate.getTime().year + 1900
            endYear = currentDate.getTime().year + 1900 + 1
        } else {
            startYear = currentDate.getTime().year + 1900 - 1
            endYear = currentDate.getTime().year + 1900
        }
        startDate!!.set(startYear, startMonth, 1)
        endDate.set(endYear, endMonth, 31) //+1

        startCalendar = GregorianCalendar()
        startCalendar.setTime(startDate.getTime())
        endCalendar = GregorianCalendar()
        endCalendar.setTime(endDate.getTime())
        diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
        diffMonth =
            diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
        list.addOnItemTouchListener(
            RecyclerItemListener(
                mContext,
                list,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (daySpinSelect == false) {
                            listSpinner.setVisibility(
                                View.GONE
                            )
                            daySpinSelect = true
                        }
                        if (isShowCalendar) {
                            calendarVisible()
                            println("test1") //hiding
                        } else {
                            calendarVisibleGone()
                            println("test2") //showing
                        }
                        val mIntent: Intent
                        mIntent = Intent(
                            mContext,
                            CalenderDetailActivity::class.java
                        )
                        mIntent.putExtra("position", position)
                        mIntent.putExtra(
                            "tittle",
                            mEventArrayListFilterList.get(
                                position
                            ).tittle
                        )
                        mIntent.putExtra(
                            "date",
                           mEventArrayListFilterList.get(
                                position
                            ).date
                        )
                        mIntent.putExtra(
                            "PASSING_ARRAY",
                            mEventArrayListFilterList
                        )
                       mContext.startActivity(
                            mIntent
                        )
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )

    }

    private fun initUi() {
        dateArrayList =
            ArrayList<String>()
        holidayArrayList =
            ArrayList<String>()
        eventArrayList =
            ArrayList<CalendarModel>()
        AppController().dateArrayListYear = ArrayList()
        holidayArrayListYear = ArrayList<String>()

        arrow =
            requireView().findViewById<ImageView>(R.id.arrowDwnImg)
        arrowUp =
            requireView().findViewById<ImageView>(R.id.arrowUpImg)
        list =
            requireView().findViewById<RecyclerView>(R.id.mEventList)
        txtMYW =
            requireView().findViewById<TextView>(R.id.txtMYW)
        daySpinner =
            requireView().findViewById<TextView>(R.id.daySpinner)
        spinner =
            requireView().findViewById<Spinner>(R.id.spinner)
        spinnnerList = requireView().findViewById<ListView>(R.id.eventSpinner)
        listSpinner =
            requireView().findViewById<RelativeLayout>(R.id.listSpinner)
        txtSpinner = requireView().findViewById<LinearLayout>(R.id.txtSpinner)
        noDataAlertTxt =
            requireView().findViewById<TextView>(R.id.noDataAlertTxt)
        alertRelative =
            requireView().findViewById<RelativeLayout>(R.id.alertRelative)
        noDataAlertTxt.setText(getString(R.string.no_calendar_data))
        txtMYW.setText("This Month")
        spinner!!.setOnItemSelectedListener(this)

        mListViewArray = ArrayList()
        for (i in dataArrayStrings.indices) {
            val mListViewSpinnerModel = ListViewSpinnerModel()
            mListViewSpinnerModel.id=i.toString()
            mListViewSpinnerModel.filename=""
            mListViewSpinnerModel.title=dataArrayStrings.get(i)
            mListViewArray!!.add(mListViewSpinnerModel)
        }
        spinnnerList!!.setAdapter(ListViewSpinnerAdapter(activity, mListViewArray))

        txtSpinner!!.setOnClickListener {
            println("dayspinner click")
            daySpinSelect = if (daySpinSelect == true) {
                listSpinner.setVisibility(
                    View.VISIBLE
                )
                false
            } else {
                listSpinner.setVisibility(
                    View.GONE
                )
                true
            }
        }
        daySpinner!!.setOnClickListener(View.OnClickListener {
            println("dayspinner click")
            daySpinSelect = if (daySpinSelect == true) {
                listSpinner.setVisibility(View.VISIBLE)
                false
            } else {
                listSpinner.setVisibility(View.GONE)
                true
            }
        })

        spinnnerList!!.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
            Log.e("positiontime1", "positiontime1 = $position")

            // TODO Auto-generated method stub
            if (position == 1) {
                listSpinner.setVisibility(View.GONE)
                daySpinner!!.setText("Month View")

                //calendarVisibleGone();
                mEventArrayListFilterList =
                    ArrayList<CalendarModel>()
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                for (j in eventArrayList!!.indices) {
                    if (currentMonth(calendar[Calendar.MONTH]) == Integer.valueOf(
                            eventArrayList.get(j)
                                .monthNumber
                        ) && 1900 + CustomCalendarView.currentCalendar.getTime()
                            .getYear() === Integer.valueOf(
                            eventArrayList.get(
                                j
                            ).year
                        )
                    ) {
                        mEventArrayListFilterList!!.add(
                            eventArrayList!!.get(j)
                        )
                    }
                }
                viewType = 1
                val mCustomListAdapter = CustomList(
                    mContext,
                    mEventArrayListFilterList
                )
                list.setAdapter(
                    mCustomListAdapter
                )
                txtMYW.setText("This Month")
                calendar.next(
                    currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ) + currentMonth(calendar.time.month) - 1 - (calendar[Calendar.MONTH] + currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ))
                )
            } else if (position == 2) {
                listSpinner.setVisibility(View.GONE)
                daySpinner!!.setText("Week View")
                txtMYW.setText("This Week")

                //calendarVisibleGone();
                viewType = 2
                val formatEEE = SimpleDateFormat("EEE", Locale.ENGLISH)
                val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
                val formatDD = SimpleDateFormat("dd", Locale.ENGLISH)
                val formatyyyy = SimpleDateFormat("yyyy", Locale.ENGLISH)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val dayOfTheWeek = arrayOfNulls<String>(7)
                val month = arrayOfNulls<String>(7)
                val year = arrayOfNulls<String>(7)
                val dd = arrayOfNulls<String>(7)
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.firstDayOfWeek = Calendar.SUNDAY
                calendar[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
                println("calendar:" + calendar[Calendar.DAY_OF_MONTH] + ":" + calendar[Calendar.MONTH] + ":" + calendar[Calendar.YEAR])
                val days = arrayOfNulls<String>(7)
                for (i in 0..6) {
                    days[i] = sdf.format(calendar.time)
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.firstDayOfWeek = Calendar.SUNDAY
                    calendar[Calendar.DAY_OF_WEEK] = i + 1
                    var msgDate: Date? = Date()
                    try {
                        msgDate = sdf.parse(
                            calendar[Calendar.YEAR].toString() + "-" + currentMonth(
                                calendar[Calendar.MONTH]
                            ) + "-" + calendar[Calendar.DAY_OF_MONTH]
                        )
                    } catch (ex: ParseException) {
                        Log.e("Date", "Parsing error")
                    }
                    dayOfTheWeek[i] = formatEEE.format(msgDate) // Thu
                    year[i] = formatyyyy.format(msgDate) // yyyy
                    month[i] = formatMM.format(msgDate) // 15
                    dd[i] = formatDD.format(msgDate) // 15
                }
                calendar.nexWeek(
                    currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ) + currentMonth(calendar.time.month) - 1 - (calendar[Calendar.MONTH] + currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ))
                )
                callCalendarMonthNewWeek(
                    dayOfTheWeek,
                    dd,
                    month,
                    year
                )

                //Log.e("eventsize", String.valueOf(eventArrayList.size()));
                */
/*  mEventArrayListFilterList = new ArrayList<>();
    
                        for (int i = 0; i < dayOfTheWeek.length; i++) {
                            for (int j = 0; j < eventArrayList.size(); j++) {
                                if (dd[i].equalsIgnoreCase(eventArrayList.get(j).getDay()) && dayOfTheWeek[i].equalsIgnoreCase(eventArrayList.get(j).getDayOfTheWeek()) && month[i].equalsIgnoreCase(eventArrayList.get(j).getMonthNumber()) && (year[i].equalsIgnoreCase(eventArrayList.get(j).getYear()))) {
                                    mEventArrayListFilterList.add(eventArrayList.get(j));
    //                                Collections.reverse(mEventArrayListFilterList);
                                }
                            }
                        }
    
                        Log.e("meventsizeafter", String.valueOf(mEventArrayListFilterList.size()));
                        CustomList mCustomListAdapter = new CustomList(mContext, mEventArrayListFilterList);
                        list.setAdapter(mCustomListAdapter);
    
                        if (mEventArrayListFilterList.size() == 0) {
                            alertRelative.setVisibility(View.VISIBLE);
                        } else {
                            alertRelative.setVisibility(View.GONE);
    
                        }*//*

            } else if (position == 0) {
                listSpinner.setVisibility(View.GONE)
                viewType = 0
                txtMYW.setText("This Year")
                calendarVisibleGone()
                //                    if (isShowCalendar)
                //                    {
                //                        calendarVisible();
                //                    }
                //                    else
                //                    {
                //                        calendarVisibleGone();
                //                    }
                holidayapi()
                */
/* Intent intent = new Intent(getActivity(), CalendarYearActivity.class);
                        intent.putExtra("BUNDLE", (Serializable) AppController().holidayArrayListYear);
                        startActivity(intent);*//*


                //intent.putExtra("holidaysArray",holidayArrayList);
                */
/*intent.putExtra("diffYear", diffYear);
                        intent.putExtra("startMonth", startCalendar.getTime().getMonth());
                        intent.putExtra("startYear", startCalendar.getTime().getYear() + 1900);
                        intent.putExtra("endYear", endCalendar.getTime().getYear() + 1900);
                        intent.putExtra("diffMonth", diffMonth);
                        intent.putExtra("startTime", startCalendar.getTimeInMillis());*//*

                //startActivity(intent);
                // showAttendanceList(holidayArrayList);
            }
            if (daySpinSelect == false) {
                listSpinner.setVisibility(View.GONE)
                daySpinSelect = true
            }
        })

        calendar =
            requireView().findViewById(R.id.calendar_view)
        val displayMetrics = DisplayMetrics()
        val windowmanager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowmanager.defaultDisplay.getMetrics(displayMetrics)
        listRelative =
            requireView().findViewById<RelativeLayout>(R.id.listRelative)
        listMainRelative =
            requireView().findViewById<RelativeLayout>(R.id.listMainRelative)
        mainRelative =
            requireView().findViewById<RelativeLayout>(R.id.mainRelative)
        mainRelative!!.setOnClickListener(this)
        calendar!!.setOnClickListener(this)
        recyclerViewLayoutManager =
            GridLayoutManager(mContext, 1)
        list.setLayoutManager(
            recyclerViewLayoutManager
        )
        list.setHasFixedSize(true)
        if (isShowCalendar) {
            calendarVisible()
        } else {
            calendarVisibleGone()
        }
        arrow!!.setOnClickListener(View.OnClickListener { //
            if (daySpinSelect == false) {
                listSpinner.setVisibility(View.GONE)
                daySpinSelect = true
            }
            isShowCalendar = false
            calendarVisibleGone()
        })


        arrowUp!!.setOnClickListener(View.OnClickListener {
            if (daySpinSelect == false) {
                listSpinner.setVisibility(View.GONE)
                daySpinSelect = true
            }
            isShowCalendar = true
            calendarVisible()
        })


        calendar.setCalendarListener(object :
            CalendarListener() {
            fun onDateSelected(date: Date?) {}
            fun onMonthChanged(
                time: Date,
                btnWeekView: TextView,
                btnMonthView: TextView,
                btnYearView: TextView
            ) {
                println("date time in main::" + time.day + "-" + time.month + "-" + "-" + time.year)
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                mEventArrayListFilterList =
                    ArrayList<CalendarModel>()
                for (j in eventArrayList.indices) {
                    if (currentMonth(time.month) == Integer.valueOf(
                            eventArrayList.get(j)
                                .getMonthNumber()
                        ) && 1900 + time.year == Integer.valueOf(
                            eventArrayList.get(
                                j
                            ).getYear()
                        )
                    ) {
                        mEventArrayListFilterList.add(
                            eventArrayList.get(j)
                        )
                    }
                }
                val mCustomListAdapter = CustomList(
                    mContext,
                    mEventArrayListFilterList
                )
                list.setAdapter(
                    mCustomListAdapter
                )
                if (mEventArrayListFilterList.size == 0) {
                    alertRelative.setVisibility(
                        View.VISIBLE
                    )
                } else {
                    alertRelative.setVisibility(
                        View.GONE
                    )
                }
                val formatMMM = SimpleDateFormat("MMMM", Locale.ENGLISH)
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                if (1900 + time.year == calendar[Calendar.YEAR] && currentMonth(
                        calendar[Calendar.MONTH]
                    ) == currentMonth(time.month)
                ) {
                    txtMYW.setText("This Month")
                } else {
                    txtMYW.setText(
                        formatMMM.format(
                            time
                        ) + " " + (1900 + time.year)
                    )
                }
                daySpinner.setText("Month View")
            }

            fun onWeekChanged(
                time: Date,
                btnWeekView: TextView,
                btnMonthView: TextView,
                btnYearView: TextView
            ) {
                viewType = 2
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                mEventArrayListFilterList =
                    ArrayList<CalendarModel>()
                for (j in eventArrayList.indices) {
                    if (currentMonth(time.month) == Integer.valueOf(
                            eventArrayList.get(j)
                                .getMonthNumber()
                        ) && 1900 + time.year == Integer.valueOf(
                            eventArrayList.get(
                                j
                            ).getYear()
                        )
                    ) {
                        mEventArrayListFilterList.add(
                            eventArrayList.get(j)
                        )
                    }
                }
                val mCustomListAdapter = CustomList(
                    mContext,
                    mEventArrayListFilterList
                )
                list.setAdapter(
                    mCustomListAdapter
                )
                val formatMMM = SimpleDateFormat("MMMM", Locale.ENGLISH)
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                if (1900 + time.year == calendar[Calendar.YEAR] && currentMonth(
                        calendar[Calendar.MONTH]
                    ) == currentMonth(time.month)
                ) {
                    txtMYW.setText("This Week")
                } else {
                    txtMYW.setText(
                        formatMMM.format(
                            time
                        ) + " " + (1900 + time.year)
                    )
                }
                daySpinner.setText("Week View")
                if (mEventArrayListFilterList.size == 0) {
                    alertRelative.setVisibility(
                        View.VISIBLE
                    )
                } else {
                    alertRelative.setVisibility(
                        View.GONE
                    )
                }
            }

            fun onMonthView(
                time: Date,
                btnWeekView: TextView,
                btnMonthView: TextView,
                btnYearView: TextView
            ) {
                calendarVisibleGone()
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                mEventArrayListFilterList =
                    ArrayList<CalendarModel>()
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                for (j in eventArrayList.indices) {
                    if (currentMonth(calendar[Calendar.MONTH]) == Integer.valueOf(
                            eventArrayList.get(j)
                                .getMonthNumber()
                        ) && 1900 + time.year == Integer.valueOf(
                            eventArrayList.get(
                                j
                            ).getYear()
                        )
                    ) {
                        mEventArrayListFilterList.add(
                            eventArrayList.get(j)
                        )
                    }
                }
                val mCustomListAdapter = CustomList(
                    mContext,
                    mEventArrayListFilterList
                )
                list.setAdapter(
                    mCustomListAdapter
                )
                txtMYW.setText("This Month")
                calendar.next(
                    currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ) + currentMonth(calendar.time.month) - 1 - (calendar[Calendar.MONTH] + currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ))
                )
            }

            fun onWeekView(
                time: Date?,
                btnWeekView: TextView,
                btnMonthView: TextView,
                btnYearView: TextView
            ) {
                println("week Clicks : ")
                calendarVisibleGone()
                val formatEEE = SimpleDateFormat("EEE", Locale.ENGLISH)
                val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
                val formatDD = SimpleDateFormat("dd", Locale.ENGLISH)
                val formatyyyy = SimpleDateFormat("yyyy", Locale.ENGLISH)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                val dayOfTheWeek = arrayOfNulls<String>(7)
                val month = arrayOfNulls<String>(7)
                val year = arrayOfNulls<String>(7)
                val dd = arrayOfNulls<String>(7)
                val date = Date() // your date
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.firstDayOfWeek = Calendar.SUNDAY
                calendar[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
                println("calendar:" + calendar[Calendar.DAY_OF_MONTH] + ":" + calendar[Calendar.MONTH] + ":" + calendar[Calendar.YEAR])
                val days = arrayOfNulls<String>(7)
                for (i in 0..6) {
                    days[i] = sdf.format(calendar.time)
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.firstDayOfWeek = Calendar.SUNDAY
                    calendar[Calendar.DAY_OF_WEEK] = i + 1
                    var msgDate: Date? = Date()
                    try {
                        msgDate = sdf.parse(
                            calendar[Calendar.YEAR].toString() + "-" + currentMonth(
                                calendar[Calendar.MONTH]
                            ) + "-" + calendar[Calendar.DAY_OF_MONTH]
                        )
                    } catch (ex: ParseException) {
                        Log.e("Date", "Parsing error")
                    }
                    dayOfTheWeek[i] = formatEEE.format(msgDate) // Thu
                    year[i] = formatyyyy.format(msgDate) // yyyy
                    month[i] = formatMM.format(msgDate) // 15
                    dd[i] = formatDD.format(msgDate) // 15
                }
                calendar.nexWeek(
                    currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ) + currentMonth(calendar.time.month) - 1 - (calendar[Calendar.MONTH] + currentMonth(
                        currentMonth(calendar.time.month) - 1
                    ))
                )
                mEventArrayListFilterList =
                    ArrayList<CalendarModel>()
                for (i in dayOfTheWeek.indices) {
                    println("testing")
                    println("week:**" + dayOfTheWeek[i] + "_year:" + year[i] + "_month:" + month[i] + "_dd:" + dd[i])
                    for (j in eventArrayList.indices) {
                        if (dd[i].equals(
                                eventArrayList.get(
                                    j
                                ).getDay(), ignoreCase = true
                            ) && dayOfTheWeek[i].equals(
                                eventArrayList.get(
                                    j
                                ).getDayOfTheWeek(), ignoreCase = true
                            ) && month[i].equals(
                                eventArrayList.get(
                                    j
                                ).getMonthNumber(), ignoreCase = true
                            ) && year[i].equals(
                                eventArrayList.get(
                                    j
                                ).getYear(), ignoreCase = true
                            )
                        ) {
                            mEventArrayListFilterList.add(
                                eventArrayList.get(
                                    j
                                )
                            )
                        }
                    }
                }
                val mCustomListAdapter = CustomList(
                    mContext,
                    mEventArrayListFilterList
                )
                list.setAdapter(
                    mCustomListAdapter
                )
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                txtMYW.setText("This Week")
                if (mEventArrayListFilterList.size == 0) {
                    alertRelative.setVisibility(
                        View.VISIBLE
                    )
                } else {
                    alertRelative.setVisibility(
                        View.GONE
                    )
                }
            }

            fun onYearView(
                time: Date?,
                btnWeekView: TextView,
                btnMonthView: TextView,
                btnYearView: TextView
            ) {
                viewType = 0
                btnYearView.setBackgroundResource(R.drawable.rounded_rec_slected_calendar)
                btnMonthView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                btnWeekView.setBackgroundResource(R.drawable.rounded_rec_calendar)
                calendarVisibleGone()
                alertRelative.setVisibility(View.GONE)
                val intent = Intent(
                    activity,
                    CalendarYearRecyclerActivity::class.java
                )
                intent.putExtra("diffYear", diffYear)
                intent.putExtra("startMonth", startCalendar.time.month)
                intent.putExtra("startYear", startCalendar.time.year + 1900)
                intent.putExtra("endYear", endCalendar.time.year + 1900)
                intent.putExtra("diffMonth", diffMonth)
                intent.putExtra("startTime", startCalendar.timeInMillis)
                startActivity(intent)
            }
        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
*/
