package com.example.bskl_kotlin.fragment.attendance;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bskl_kotlin.R;

import com.example.bskl_kotlin.fragment.attendance.adapter.ChartListRecyclerAdapter;
import com.example.bskl_kotlin.fragment.attendance.adapter.SelectedListRecyclerAdapter;
import com.example.bskl_kotlin.fragment.attendance.adapter.StrudentSpinnerAdapter;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceChartListModel;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceModel;
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceResponseModel;
import com.example.bskl_kotlin.fragment.attendance.model.AttendenceResponse;
import com.example.bskl_kotlin.fragment.attendance.model.ResponseArrayAttendanceModel;
import com.example.bskl_kotlin.fragment.attendance.model.StudentModelJava;
import com.example.bskl_kotlin.fragment.attendance.model.StudentResponseModelJava;
import com.example.bskl_kotlin.recyclerviewmanager.DividerItemDecoration;
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener;
import com.example.bskl_kotlin.rest_api.ApiInterface;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class AttendenceFragment  extends Fragment implements OnChartValueSelectedListener
        {
    private String mTitle;
    private String mTabId;
    Context mContext;
    private View mRootView;

    boolean firstVisit;
    TextView studentName;
    TextView dividerTv;
    TextView selectedTextView;
    TextView attendanceTextView;
    TextView selectedTextViewDays;
    ImageView studImg;
    ImageView upArrowImage;
    LinearLayout mStudentSpinner;
    LinearLayout chartViewLinearClick;
    LinearLayout listViewLinearClick;
    LinearLayout chartLinear;
    RelativeLayout relMain;
    String stud_img = "";
    String stud_id = "";
    String studtemp_id = "";
    String studClass = "";
    String alumini = "";
    String progressreport = "";
    ArrayList<StudentModelJava> studentsModelArrayList = new ArrayList<>();
    ArrayList<String> studentList = new ArrayList<>();
    PieChart pieChart;
    RecyclerView listView;
    RecyclerView listViewGraph;
    String totaldays = "";
    String unauthorised_absence = "";
    String authorised_absence = "";
    String late = "";
    String present = "";
    int presentDays = 0;
    String legendArray[] = {"Total days", "Present", "Late", "Unauthorised absence"};
    String legendArrayNew[] = {"Total days", "Present/Authorised absence", "Late", "Unauthorised absence"};
    ArrayList<AttendanceModel> mAttendanceModelListDialog;
    ArrayList<AttendanceModel> mAttendanceModelList;
    ArrayList<AttendanceModel> mAttendanceModelListPresent;
    ArrayList<AttendanceModel> mAttendanceModelListLate;
    ArrayList<AttendanceModel> mAttendanceModelListAuthorisedAbsence;
    ArrayList<AttendanceModel> mAttendanceModelListUnauthorisedAbsence;
    ArrayList<AttendanceChartListModel> mAttendanceChartListModelArray;
    ArrayList<Integer> colorList;
    ArrayList<Integer> colorValueList;
    LinearLayoutManager mLayoutManagerChartList;
    LinearLayoutManager mLayoutManagerlistViewGraph;
    ChartListRecyclerAdapter mChartListRecyclerAdapter;
    SelectedListRecyclerAdapter mSelectedListRecyclerAdapter;
    PreferenceManagerr preferenceManager;

    public void AttendenceFragment() {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_attendence_layout, container,
                false);
        setHasOptionsMenu(true);
        firstVisit = true;
        mContext = getActivity();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        TextView headerTitle = actionBar.getCustomView().findViewById(R.id.headerTitle);
        ImageView logoClickImgView = actionBar.getCustomView().findViewById(R.id.logoClickImgView);
        ImageView imageButton2 = actionBar.getCustomView().findViewById(R.id.action_bar_forward);
        imageButton2.setImageResource(R.drawable.tutorial_icon);
        headerTitle.setText("ATTENDANCE");
        headerTitle.setVisibility(View.VISIBLE);
        logoClickImgView.setVisibility(View.INVISIBLE);
        relMain = mRootView.findViewById(R.id.relMain);
        studentName = mRootView.findViewById(R.id.studentName);
        studImg = mRootView.findViewById(R.id.studImg);
        upArrowImage = mRootView.findViewById(R.id.upArrowImage);
        dividerTv = mRootView.findViewById(R.id.dividerTv);
        attendanceTextView = mRootView.findViewById(R.id.attendanceTextView);
        mStudentSpinner = mRootView.findViewById(R.id.studentSpinner);
        chartViewLinearClick = mRootView.findViewById(R.id.chartViewLinear);
        listViewLinearClick = mRootView.findViewById(R.id.listViewLinear);
        chartLinear = mRootView.findViewById(R.id.chartLinear);
        pieChart = mRootView.findViewById(R.id.piechart);
        listView = mRootView.findViewById(R.id.listView);
        listViewGraph = mRootView.findViewById(R.id.listViewGraph);
        selectedTextView = mRootView.findViewById(R.id.selectedTextView);
        selectedTextViewDays = mRootView.findViewById(R.id.selectedTextViewDays);
        pieChart.setOnChartValueSelectedListener(this);
        listView.setHasFixedSize(true);
        mLayoutManagerChartList = new LinearLayoutManager(mContext);
        mLayoutManagerChartList.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(mLayoutManagerChartList);
        listViewGraph.setHasFixedSize(true);
        mLayoutManagerlistViewGraph = new LinearLayoutManager(mContext);
        mLayoutManagerlistViewGraph.setOrientation(LinearLayoutManager.VERTICAL);
        listViewGraph.setLayoutManager(mLayoutManagerlistViewGraph);
        mAttendanceModelList = new ArrayList<>();
        mAttendanceModelListPresent = new ArrayList<>();
        mAttendanceModelListLate = new ArrayList<>();
        mAttendanceModelListAuthorisedAbsence = new ArrayList<>();
        mAttendanceModelListUnauthorisedAbsence = new ArrayList<>();
        mAttendanceChartListModelArray = new ArrayList<>();
        mAttendanceModelListDialog = new ArrayList<>();
        initialiseUI();
        if (checkInternet(mContext)) {
            getStudentsListAPI();
        } else {
            showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }

        if (PreferenceManagerr.getIsFirstTimeInAttendance(mContext)) {
            PreferenceManagerr.setIsFirstTimeInAttendance(mContext, false);
            Intent mintent = new Intent(mContext, AttendanceInfoActivity.class);
            mintent.putExtra("type", 1);
            mContext.startActivity(mintent);
        }


        return mRootView;
    }

    private void initialiseUI() {
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAttendanceList(mAttendanceModelListDialog);
            }
        });
        mStudentSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentsModelArrayList.size() > 0) {
                    showSocialmediaList(studentsModelArrayList);
                } else {
                    showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);

                }
            }
        });
        chartViewLinearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pieChart.getVisibility() == View.VISIBLE) {

                } else {
                    chartViewLinearClick.setBackgroundResource(R.drawable.event_spinner_bg);
                    listViewLinearClick.setBackgroundResource(R.drawable.event_spinnerbg);
                    pieChart.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    selectedTextView.setVisibility(View.INVISIBLE);
                    selectedTextViewDays.setVisibility(View.INVISIBLE);
                    preferenceManager=new PreferenceManagerr();
                    mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
                    listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                    mAttendanceModelListDialog = new ArrayList<>();
                    mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();
                    if (mAttendanceModelList.size() <= 0) {
                        upArrowImage.setVisibility(View.GONE);
                        dividerTv.setVisibility(View.GONE);
                        attendanceTextView.setVisibility(View.GONE);
                    } else {
                        upArrowImage.setVisibility(View.VISIBLE);
                        dividerTv.setVisibility(View.VISIBLE);
                        attendanceTextView.setVisibility(View.VISIBLE);

                    }
                }

            }
        });
        listViewLinearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listView.getVisibility() == View.VISIBLE) {

                } else {
                    chartViewLinearClick.setBackgroundResource(R.drawable.event_spinnerbg);
                    listViewLinearClick.setBackgroundResource(R.drawable.event_spinner_bg);
                    reloadPieChart();

                    pieChart.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    selectedTextView.setVisibility(View.INVISIBLE);
                    selectedTextViewDays.setVisibility(View.INVISIBLE);
                    mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
                    listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                    mAttendanceModelListDialog = new ArrayList<>();
                    mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();
                    if (mAttendanceModelList.size() <= 0) {
                        upArrowImage.setVisibility(View.GONE);
                        dividerTv.setVisibility(View.GONE);
                        attendanceTextView.setVisibility(View.GONE);
                    } else {
                        upArrowImage.setVisibility(View.VISIBLE);
                        dividerTv.setVisibility(View.VISIBLE);
                        attendanceTextView.setVisibility(View.VISIBLE);

                    }
                }
            }
        });

        listView.addOnItemTouchListener(new RecyclerItemListener(mContext, listView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if (mAttendanceChartListModelArray.size() > 0) {
                            selectedTextView.setVisibility(View.VISIBLE);
                            selectedTextViewDays.setVisibility(View.VISIBLE);
                            if (mAttendanceChartListModelArray.get(position).getName().equalsIgnoreCase("Late"))
                            {
                                selectedTextView.setText(late + "/" + totaldays);

                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListLate, mAttendanceChartListModelArray);
                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                mAttendanceModelListDialog = new ArrayList<>();
                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListLate.clone();
                                if (mAttendanceModelListLate.size() <= 0) {
                                    upArrowImage.setVisibility(View.GONE);
                                    dividerTv.setVisibility(View.GONE);
                                    attendanceTextView.setVisibility(View.GONE);
                                } else {
                                    upArrowImage.setVisibility(View.VISIBLE);
                                    dividerTv.setVisibility(View.VISIBLE);
                                    attendanceTextView.setVisibility(View.VISIBLE);

                                }
                            } else if (mAttendanceChartListModelArray.get(position).getName().equalsIgnoreCase("Present/Authorised absence")) {
                                selectedTextView.setText(presentDays + "/" + totaldays);
                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListPresent, mAttendanceChartListModelArray);
                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                mAttendanceModelListDialog = new ArrayList<>();
                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListPresent.clone();
                                if (mAttendanceModelListPresent.size() <= 0) {
                                    upArrowImage.setVisibility(View.GONE);
                                    dividerTv.setVisibility(View.GONE);
                                    attendanceTextView.setVisibility(View.GONE);
                                } else {
                                    upArrowImage.setVisibility(View.VISIBLE);
                                    dividerTv.setVisibility(View.VISIBLE);
                                    attendanceTextView.setVisibility(View.VISIBLE);

                                }
//                            } else if (mAttendanceChartListModelArray.get(position).getName().equalsIgnoreCase("Authorised absence")) {
//                                selectedTextView.setText(authorised_absence + "/" + totaldays);
//                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListAuthorisedAbsence, mAttendanceChartListModelArray);
//                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
//                                mAttendanceModelListDialog = new ArrayList<>();
//                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListAuthorisedAbsence.clone();
//                                if (mAttendanceModelListAuthorisedAbsence.size() <= 0) {
//                                    upArrowImage.setVisibility(View.GONE);
//                                    dividerTv.setVisibility(View.GONE);
//                                    attendanceTextView.setVisibility(View.GONE);
//                                } else {
//                                    upArrowImage.setVisibility(View.VISIBLE);
//                                    dividerTv.setVisibility(View.VISIBLE);
//                                    attendanceTextView.setVisibility(View.VISIBLE);
//
//                                }
                            }
                            else if (mAttendanceChartListModelArray.get(position).getName().equalsIgnoreCase("Unauthorised absence")) {

                                selectedTextView.setText(unauthorised_absence + "/" + totaldays);
                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListUnauthorisedAbsence, mAttendanceChartListModelArray);
                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                mAttendanceModelListDialog = new ArrayList<>();
                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListUnauthorisedAbsence.clone();
                                if (mAttendanceModelListUnauthorisedAbsence.size() <= 0) {
                                    upArrowImage.setVisibility(View.GONE);
                                    dividerTv.setVisibility(View.GONE);
                                    attendanceTextView.setVisibility(View.GONE);
                                } else {
                                    upArrowImage.setVisibility(View.VISIBLE);
                                    dividerTv.setVisibility(View.VISIBLE);
                                    attendanceTextView.setVisibility(View.VISIBLE);

                                }
                            } else {
                                selectedTextView.setVisibility(View.INVISIBLE);
                                selectedTextViewDays.setVisibility(View.INVISIBLE);
                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                mAttendanceModelListDialog = new ArrayList<>();
                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();
                                if (mAttendanceModelList.size() <= 0) {
                                    upArrowImage.setVisibility(View.GONE);
                                } else {
                                    upArrowImage.setVisibility(View.VISIBLE);

                                }
                            }
                        } else {
                            selectedTextView.setVisibility(View.INVISIBLE);
                            selectedTextViewDays.setVisibility(View.INVISIBLE);
                            upArrowImage.setVisibility(View.GONE);
                            dividerTv.setVisibility(View.GONE);
                            attendanceTextView.setVisibility(View.GONE);


                        }
                    }

                    public void onLongClickItem(View v, int position) {
                    }
                }));
    }

    public void showSocialmediaList(final ArrayList<StudentModelJava> mStudentArray) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_student_media_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogDismiss = dialog.findViewById(R.id.btn_dismiss);
        ImageView iconImageView = dialog.findViewById(R.id.iconImageView);
        iconImageView.setImageResource(R.drawable.boy);
        RecyclerView socialMediaList = dialog.findViewById(R.id.recycler_view_social_media);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_new));

        } else {
            dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.button_new));

        }
        socialMediaList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        socialMediaList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        socialMediaList.setLayoutManager(llm);

        StrudentSpinnerAdapter studentAdapter = new StrudentSpinnerAdapter(mContext, mStudentArray);
        socialMediaList.setAdapter(studentAdapter);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.dismiss();

            }

        });

        socialMediaList.addOnItemTouchListener(new RecyclerItemListener(mContext, socialMediaList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        dialog.dismiss();
                        if (isNetworkConnected(mContext)) {

                            studentName.setText(mStudentArray.get(position).getName());
                            stud_id = mStudentArray.get(position).getId();
                            studClass = mStudentArray.get(position).getmClass();
                            PreferenceManagerr.setLeaveStudentId(mContext, stud_id);
                            PreferenceManagerr.setLeaveStudentName(mContext, mStudentArray.get(position).getName());
                            stud_img = studentsModelArrayList.get(position).getPhoto();
                            if (!(stud_img.equals(""))) {

                                Glide.with(mContext).load(stud_img).placeholder(R.drawable.boy).into(studImg);
                            } else

                            {

                                studImg.setImageResource(R.drawable.boy);
                            }
                            if (studtemp_id.equalsIgnoreCase(stud_id)) {

                            } else {
                                studtemp_id = stud_id;
                                if (isNetworkConnected(mContext)) {

                                    getList(mStudentArray.get(position).getId());
                                } else {
                                    showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                }
                            }
                        }else if (!(stud_id.equalsIgnoreCase(mStudentArray.get(position).getId()))){
                            showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        dialog.show();
    }



            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("VAL SELECTED",
                        "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                + ", DataSet index: " + dataSetIndex);
                selectedTextView.setVisibility(View.VISIBLE);
                selectedTextViewDays.setVisibility(View.VISIBLE);
                if (colorList.get(e.getXIndex()) == getResources().getColor(R.color.late)) {
                    selectedTextView.setText(late + "/" + totaldays );
                    mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListLate, mAttendanceChartListModelArray);
                    listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                    mAttendanceModelListDialog = new ArrayList<>();
                    mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListLate.clone();
                    if (mAttendanceModelListLate.size() <= 0) {
                        upArrowImage.setVisibility(View.GONE);
                        dividerTv.setVisibility(View.GONE);
                        attendanceTextView.setVisibility(View.GONE);
                    } else {
                        upArrowImage.setVisibility(View.VISIBLE);
                        dividerTv.setVisibility(View.VISIBLE);
                        attendanceTextView.setVisibility(View.VISIBLE);

                    }
                } else if (colorList.get(e.getXIndex()) == getResources().getColor(R.color.present)) {
                    selectedTextView.setText(presentDays + "/" + totaldays );
                    mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListPresent, mAttendanceChartListModelArray);
                    listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                    mAttendanceModelListDialog = new ArrayList<>();
                    mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListPresent.clone();
                    if (mAttendanceModelListPresent.size() <= 0) {
                        upArrowImage.setVisibility(View.GONE);
                        dividerTv.setVisibility(View.GONE);
                        attendanceTextView.setVisibility(View.GONE);
                    } else {
                        upArrowImage.setVisibility(View.VISIBLE);
                        dividerTv.setVisibility(View.VISIBLE);
                        attendanceTextView.setVisibility(View.VISIBLE);

                    }
                }
//        else if (colorList.get(e.getXIndex()) == getResources().getColor(R.color.authorisedabsence)) {
//            selectedTextView.setText(authorised_absence + "/" + totaldays );
//            mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListAuthorisedAbsence, mAttendanceChartListModelArray);
//            listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
//            mAttendanceModelListDialog = new ArrayList<>();
//            mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListAuthorisedAbsence.clone();
//            if (mAttendanceModelListAuthorisedAbsence.size() <= 0) {
//                upArrowImage.setVisibility(View.GONE);
//                dividerTv.setVisibility(View.GONE);
//                attendanceTextView.setVisibility(View.GONE);
//            } else {
//                upArrowImage.setVisibility(View.VISIBLE);
//                dividerTv.setVisibility(View.VISIBLE);
//                attendanceTextView.setVisibility(View.VISIBLE);
//
//            }
//        }
                else if (colorList.get(e.getXIndex()) == getResources().getColor(R.color.unauthorisedabsence)) {

                    selectedTextView.setText(unauthorised_absence + "/" + totaldays );
                    mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelListUnauthorisedAbsence, mAttendanceChartListModelArray);
                    listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                    mAttendanceModelListDialog = new ArrayList<>();
                    mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelListUnauthorisedAbsence.clone();

                    if (mAttendanceModelListUnauthorisedAbsence.size() <= 0) {
                        upArrowImage.setVisibility(View.GONE);
                        dividerTv.setVisibility(View.GONE);
                        attendanceTextView.setVisibility(View.GONE);
                    } else {
                        upArrowImage.setVisibility(View.VISIBLE);
                        dividerTv.setVisibility(View.VISIBLE);
                        attendanceTextView.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
        selectedTextView.setVisibility(View.INVISIBLE);
        selectedTextViewDays.setVisibility(View.INVISIBLE);
        mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
        listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
        mAttendanceModelListDialog = new ArrayList<>();
        mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();
        if (mAttendanceModelList.size() <= 0) {
            upArrowImage.setVisibility(View.GONE);
            dividerTv.setVisibility(View.GONE);
            attendanceTextView.setVisibility(View.GONE);
        } else {
            upArrowImage.setVisibility(View.VISIBLE);
            dividerTv.setVisibility(View.VISIBLE);
            attendanceTextView.setVisibility(View.VISIBLE);

        }
    }

    private void getStudentsListAPI() {

        Log.e("pref", PreferenceManagerr.getUserId(mContext));
        ApiInterface service =
                APIClient.getRetrofitInstance().create(ApiInterface.class);
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("users_id",PreferenceManagerr.getUserId(mContext));
        Call<StudentResponseModelJava> call = service.studentlistattendance("Bearer "+PreferenceManagerr.getAccessToken(mContext),paramObject);

         call.enqueue(new Callback<StudentResponseModelJava>()
        {
            @Override
            public void onResponse(Call<StudentResponseModelJava> call, Response<StudentResponseModelJava> response) {
                if (response.isSuccessful()) {
                    try {
                        StudentResponseModelJava apiResponse = response.body();
                        //Log.e("response", String.valueOf(apiResponse));
                        System.out.println("attenresponse" + apiResponse);
                        String responsCode = apiResponse.getResponsecode();

                        if (responsCode.equals("200")) {
                            String statuscode = apiResponse.getResponse().getStatuscode();
                            Log.e("response", statuscode);

                            if (statuscode.equals("303")) {
                                ArrayList<StudentModelJava> data = response.body().getResponse().getData();
                                Log.e("response", String.valueOf(data.size()));
                                studentsModelArrayList.clear();
                                studentList.clear();
                                if (data.size() > 0) {
                                    //studentsModelArrayList.add(0,);
                                    for (int i = 0; i < data.size(); i++) {

                                        StudentModelJava  dataObject = data.get(i);

                                        if (dataObject.getAlumi().equalsIgnoreCase("0")) {
                                            studentsModelArrayList.add(addStudentDetails(dataObject));
                                        }
//                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                        studentList.add(studentsModelArrayList.get(i).getAlumi());
                                        studentList.add(studentsModelArrayList.get(i).getProgressreport());
                                    }
                                    Log.e("studsize",studentsModelArrayList.get(0).getName());
                                    studentName.setText(studentsModelArrayList.get(0).getName());
                                    stud_img = studentsModelArrayList.get(0).getPhoto();
                                    if (!(stud_img.equals(""))) {

                                        Glide.with(mContext).load(stud_img).placeholder(R.drawable.boy).into(studImg);
                                    } else {

                                        studImg.setImageResource(R.drawable.boy);
                                    }


                                    stud_id = studentsModelArrayList.get(0).getId();
                                    studtemp_id = studentsModelArrayList.get(0).getId();
                                    alumini = studentsModelArrayList.get(0).getAlumi();
                                    progressreport = studentsModelArrayList.get(0).getProgressreport();
                                    PreferenceManagerr.setLeaveStudentId(mContext, stud_id);
                                    PreferenceManagerr.setLeaveStudentName(mContext, studentsModelArrayList.get(0).getName());
                                    studClass = studentsModelArrayList.get(0).getmClass();
                                    if (isNetworkConnected(mContext)) {
                                        getList(stud_id);
                                    } else {
                                        showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                    }

                                } else {


                                    showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);
                                }

                            } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception ex) {
                        System.out.println("The Exception in edit profile is" + ex.toString());
                    }
                }
            }



            @Override
            public void onFailure(Call<StudentResponseModelJava> call, Throwable t) {

                Log.e("toadst", String.valueOf(t));
                //  Toast.makeText(mContext, "Successfully sent email to staff"+t, Toast.LENGTH_SHORT);

            }});



       /* VolleyWrapper volleyWrapper = new VolleyWrapper(URLHEAD);
        String[] name = {"access_token", "users_id"};
        String[] value = {PreferenceManager().getAccessToken(mContext), PreferenceManager().getUserId(mContext)};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is" + successResponse);
                try {
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            JSONArray data = secobj.getJSONArray("data");
                            studentsModelArrayList.clear();
                            studentList.clear();
                            if (data.length() > 0) {
                                //studentsModelArrayList.add(0,);
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    if (dataObject.optString("alumi").equalsIgnoreCase("0")) {
                                        studentsModelArrayList.add(addStudentDetails(dataObject));
                                    }
//                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                    studentList.add(studentsModelArrayList.get(i).getAlumini());
                                    studentList.add(studentsModelArrayList.get(i).getProgressreport());
                                }
                                studentName.setText(studentsModelArrayList.get(0).getmName());
                                stud_img = studentsModelArrayList.get(0).getmPhoto();
                                if (!(stud_img.equals(""))) {

                                    Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.boy).fit().into(studImg);
                                } else

                                {

                                    studImg.setImageResource(R.drawable.boy);
                                }


                                stud_id = studentsModelArrayList.get(0).getmId();
                                studtemp_id = studentsModelArrayList.get(0).getmId();
                                alumini = studentsModelArrayList.get(0).getAlumini();
                                progressreport = studentsModelArrayList.get(0).getProgressreport();
                                PreferenceManager().setLeaveStudentId(mContext, stud_id);
                                PreferenceManager().setLeaveStudentName(mContext, studentsModelArrayList.get(0).getmName());
                                studClass = studentsModelArrayList.get(0).getmClass();
                                if (AppUtils.isNetworkConnected(mContext)) {
                                    getList(URL_GET_ATTENDANCE, stud_id);
                                } else {
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                }

                            } else {


                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);
                            }
                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListAPI(URLHEAD);

                    } else {

                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }

            }

            @Override
            public void responseFailure(String failureResponse) {
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

            }
        });*/

    }

    private StudentModelJava addStudentDetails(StudentModelJava dataObject) {
        StudentModelJava studentModel = new StudentModelJava();
        studentModel.setId(dataObject.getId());
        studentModel.setName(dataObject.getName());
        studentModel.setmClass(dataObject.getmClass());
        studentModel.setSection(dataObject.getSection());
        studentModel.setHouse(dataObject.getHouse());
        studentModel.setPhoto(dataObject.getPhoto());
        studentModel.setProgressreport(dataObject.getProgressreport());
        studentModel.setAlumi(dataObject.getAlumi());
        return studentModel;
    }

    public void getList( final String student_id) {
        ApiInterface service =
                APIClient.getRetrofitInstance().create(ApiInterface.class);
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("users_id",PreferenceManagerr.getUserId(mContext)
        );
        paramObject.addProperty( "stud_id",student_id);
        Call<AttendanceResponseModel> call = service.attendance("Bearer "+PreferenceManagerr.getAccessToken(mContext),paramObject);

        call.enqueue(new Callback<AttendanceResponseModel>()
        {
            @Override
            public void onResponse(Call<AttendanceResponseModel> call, Response<AttendanceResponseModel> response) {
                if (response.isSuccessful()) {
                    try {
                        AttendanceResponseModel apiResponse = response.body();
                        //Log.e("response", String.valueOf(apiResponse));
                        System.out.println("response" + apiResponse);
                        String responsCode = apiResponse.getResponsecode();


                           // obj = new JSONObject(String.valueOf(response));

                        if (responsCode.equals("200")) {
                            AttendenceResponse respObject = response.body().getResponse();
                            String statuscode = apiResponse.getResponse().getStatuscode();
                            //Log.e("response", statuscode);

                            if (statuscode.equals("303")) {
                                totaldays = "";
                                unauthorised_absence = "";
                                authorised_absence = "";
                                late = "";
                                present = "";
                                presentDays = 0;
                                pieChart.invalidate();
                                pieChart.clear();
                                selectedTextView.setVisibility(View.INVISIBLE);
                                selectedTextViewDays.setVisibility(View.INVISIBLE);
                                mAttendanceModelListDialog = new ArrayList<>();
                                mAttendanceModelList = new ArrayList<>();
                                mAttendanceModelListLate = new ArrayList<>();
                                mAttendanceModelListPresent = new ArrayList<>();
                                mAttendanceModelListAuthorisedAbsence = new ArrayList<>();
                                mAttendanceModelListUnauthorisedAbsence = new ArrayList<>();
                                mAttendanceChartListModelArray = new ArrayList<>();
                                ResponseArrayAttendanceModel dataJsonObj = respObject.getResponseArray();

                                totaldays = String.valueOf(dataJsonObj.getTotaldays());
                                present = String.valueOf(dataJsonObj.getPresent());
                                late = String.valueOf(dataJsonObj.getLate());
                                authorised_absence = String.valueOf(dataJsonObj.getAuthorised_absence());
                                unauthorised_absence = String.valueOf(dataJsonObj.getUnauthorised_absence());
                                presentDays = Integer.valueOf(present);


                                pieChart.setUsePercentValues(false);
                                // IMPORTANT: In a PieChart, no values (Entry) should have the same
                                // xIndex (even if from different DataSets), since no values can be
                                // drawn above each other.
                                colorList = new ArrayList<>();
                                colorValueList = new ArrayList<>();
                                ArrayList<Entry> yvalues = new ArrayList<Entry>();
                                if (!(present.equalsIgnoreCase("0"))) {
                                    colorList.add(getResources().getColor(R.color.present));

                                    if (authorised_absence.equalsIgnoreCase("0"))
                                    {
                                    }
                                    else
                                    {
                                        int authorisedAbsence = Integer.valueOf(authorised_absence);
                                        presentDays = presentDays + authorisedAbsence;
                                    }

                                    yvalues.add(new Entry(presentDays, 0));
                                    colorValueList.add(getResources().getColor(R.color.white));

                                } else {
                                    if (authorised_absence.equalsIgnoreCase("0"))
                                    {
                                        colorList.add(getResources().getColor(R.color.present));
                                        colorValueList.add(getResources().getColor(R.color.transparent));
                                        yvalues.add(new Entry(presentDays, 0));
                                    }
                                    else
                                    {
                                        int authorisedAbsence = Integer.valueOf(authorised_absence);
                                        presentDays = authorisedAbsence;
                                        colorList.add(getResources().getColor(R.color.present));
                                        colorValueList.add(getResources().getColor(R.color.transparent));
                                        yvalues.add(new Entry(presentDays, 0));
                                    }

                                }

                                for (int i = 0; i < legendArrayNew.length; i++) {
                                    AttendanceChartListModel mAttendanceChartListModel = new AttendanceChartListModel();
                                    mAttendanceChartListModel.setName(legendArrayNew[i]);
                                    switch (i) {
                                        case 0:
                                            mAttendanceChartListModel.setCount(totaldays);

                                            break;
                                        case 1:
                                            mAttendanceChartListModel.setCount(String.valueOf(presentDays));

                                            break;
                                        case 2:
                                            mAttendanceChartListModel.setCount(late);

                                            break;
//                                                case 3:
//                                                    mAttendanceChartListModel.setCount(authorised_absence);
//
//                                                    break;
                                        case 3:
                                            mAttendanceChartListModel.setCount(unauthorised_absence);

                                            break;
                                    }
                                    mAttendanceChartListModel.setName(legendArrayNew[i]);
                                    mAttendanceChartListModelArray.add(mAttendanceChartListModel);
                                }
                                if (!(late.equalsIgnoreCase("0"))) {
                                    colorList.add(getResources().getColor(R.color.late));
                                    yvalues.add(new Entry(Integer.valueOf(late), 1));
                                    colorValueList.add(getResources().getColor(R.color.white));

                                } else {
                                    colorList.add(getResources().getColor(R.color.late));
                                    colorValueList.add(getResources().getColor(R.color.transparent));
                                    yvalues.add(new Entry(Integer.valueOf(late), 1));
                                }
//                                        if (!(authorised_absence.equalsIgnoreCase("0"))) {
//                                            colorList.add(getResources().getColor(R.color.authorisedabsence));
//                                            colorValueList.add(getResources().getColor(R.color.white));
//                                            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//                                        } else {
//                                            colorList.add(getResources().getColor(R.color.authorisedabsence));
//                                            colorValueList.add(getResources().getColor(R.color.transparent));
//                                            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//                                        }
                                if (!(unauthorised_absence.equalsIgnoreCase("0"))) {
                                    colorList.add(getResources().getColor(R.color.unauthorisedabsence));
                                    colorValueList.add(getResources().getColor(R.color.white));
                                    yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
                                } else {
                                    colorList.add(getResources().getColor(R.color.unauthorisedabsence));
                                    colorValueList.add(getResources().getColor(R.color.transparent));
                                    yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
                                }
                                int colors[] = new int[colorList.size()];
                                for (int i = 0; i < colorList.size(); i++) {
                                    colors[i] = colorList.get(i);
                                }
                                PieDataSet dataSet = new PieDataSet(yvalues, "");
                                ArrayList<String> xVals = new ArrayList<String>();
//                                        if (!(present.equalsIgnoreCase("0")))
                                xVals.add("Present/Authorised absence");
//                                        if (!(late.equalsIgnoreCase("0")))
                                xVals.add("Late");
//                                        if (!(authorised_absence.equalsIgnoreCase("0")))
                                //   xVals.add("Authorised absence");
//                                        if (!(unauthorised_absence.equalsIgnoreCase("0")))
                                xVals.add("Unauthorised absence");
                                PieData data = new PieData(xVals, dataSet);
                                // In Percentage
//                                        data.setValueFormatter(new PercentFormatter());
                                data.setValueFormatter(new LargeValueFormatter());
                                // Default value
//                                        data.setValueFormatter(new DefaultValueFormatter(0));
                                Legend l = pieChart.getLegend();
                                l.setEnabled(false);
                                pieChart.setData(data);
                                pieChart.setRotationEnabled(false);
                                pieChart.setDescriptionTextSize(100f);
                                pieChart.setDescriptionColor(R.color.white);
                                pieChart.setDescription("");
                                pieChart.setDrawSliceText(false);
                                pieChart.setDrawHoleEnabled(false);
                                pieChart.setTransparentCircleRadius(58f);
                                pieChart.setHoleRadius(58f);
                                dataSet.setColors(colors);
                                data.setValueTextSize(15f);
                                data.setValueTextColors(colorValueList);
                                pieChart.animateXY(1400, 1400);
                                ArrayList<AttendanceModel> dataDetailsArray = respObject.getDetails();

                                if (dataDetailsArray.size() > 0) {

                                    for (int i = 0; i < dataDetailsArray.size(); i++) {
                                        AttendanceModel dataObject = dataDetailsArray.get(i);
                                        AttendanceModel mAttendanceModel = new AttendanceModel();
                                        mAttendanceModel.setId(dataObject.getId());
                                        mAttendanceModel.setDate(dataObject.getDate());
                                        mAttendanceModel.setPresent(dataObject.getPresent());
                                        mAttendanceModel.setLate(dataObject.getLate());
                                        mAttendanceModel.setAuthorised_absence(dataObject.getAuthorised_absence());
                                        mAttendanceModel.setUnauthorised_absence(dataObject.getUnauthorised_absence());

                                        mAttendanceModelList.add(mAttendanceModel);
//                                                if (dataObject.optString("late").equalsIgnoreCase("1")) {
//                                                    //mAttendanceModel.setTagList("Late");
//                                                    mAttendanceModelListLate.add(mAttendanceModel);
//
//                                                }
//                                                 if (dataObject.optString("present").equalsIgnoreCase("1")) {
//                                                   // mAttendanceModel.setTagList("Present");
//                                                    mAttendanceModelListPresent.add(mAttendanceModel);
//
//                                                }
                                        if ((dataObject.getLate().equalsIgnoreCase("1"))&&(dataObject.getPresent().equalsIgnoreCase("1")))
                                        {
                                            mAttendanceModel.setLate("1");
                                            mAttendanceModel.setPresent("0");
                                            mAttendanceModelListLate.add(mAttendanceModel);
                                            AttendanceModel mDataModel = new AttendanceModel();
                                            mDataModel.setId(dataObject.getId());
                                            mDataModel.setDate(dataObject.getDate());
                                            mDataModel.setPresent(dataObject.getPresent());
                                            mDataModel.setAuthorised_absence(dataObject.getAuthorised_absence());
                                            mDataModel.setUnauthorised_absence(dataObject.getUnauthorised_absence());
                                            mDataModel.setLate("0");
                                            mAttendanceModelListPresent.add(mDataModel);

                                            mAttendanceModelList.add(mDataModel);
                                        }
                                        else
                                        {
                                            if (dataObject.getLate().equalsIgnoreCase("1")) {
                                                //mAttendanceModel.setTagList("Late");
                                                mAttendanceModelListLate.add(mAttendanceModel);

                                            }
                                            if (dataObject.getPresent().equalsIgnoreCase("1")) {
                                                // mAttendanceModel.setTagList("Present");
                                                mAttendanceModelListPresent.add(mAttendanceModel);

                                            }
                                        }
                                        if (dataObject.getAuthorised_absence().equalsIgnoreCase("1")) {
                                            //mAttendanceModel.setTagList("Authorised absence");
                                            mAttendanceModelListPresent.add(mAttendanceModel);

                                        }
                                        if (dataObject.getUnauthorised_absence().equalsIgnoreCase("1")) {
                                            //mAttendanceModel.setTagList("Unauthorised absence");
                                            mAttendanceModelListUnauthorisedAbsence.add(mAttendanceModel);

                                        }



                                    }
                                }
                                mChartListRecyclerAdapter = new ChartListRecyclerAdapter(mContext, mAttendanceChartListModelArray);
                                listView.setAdapter(mChartListRecyclerAdapter);
                                mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
                                listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                if (mAttendanceModelList.size() <= 0) {
                                    upArrowImage.setVisibility(View.GONE);
                                    dividerTv.setVisibility(View.GONE);
                                    attendanceTextView.setVisibility(View.GONE);
                                } else {
                                    upArrowImage.setVisibility(View.VISIBLE);
                                    dividerTv.setVisibility(View.VISIBLE);
                                    attendanceTextView.setVisibility(View.VISIBLE);

                                }
                                mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();


                            } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception ex) {
                        System.out.println("The Exception in attendence" + ex.toString());
                    }
                }
            }



            @Override
            public void onFailure(Call<AttendanceResponseModel> call, Throwable t) {

                Log.e("toadst", String.valueOf(t));
                //  Toast.makeText(mContext, "Successfully sent email to staff"+t, Toast.LENGTH_SHORT);

            }});
        /*  try {

            final VolleyWrapper manager = new VolleyWrapper(URL);
            String[] name = new String[]{JTAG_ACCESSTOKEN, "users_id",
                    "stud_id"};
            String[] value = new String[]{PreferenceManager().getAccessToken(mContext), PreferenceManager().getUserId(mContext), student_id};

            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

                @Override
                public void responseSuccess(String successResponse) {
                    if (successResponse != null) {
                        try {
                            JSONObject rootObject = new JSONObject(successResponse);

                            System.out.println("The response is attendance" + successResponse);

                            if (rootObject.optString(JTAG_RESPONSE) != null) {
                                String response_code = rootObject.optString(JTAG_RESPONSECODE);
                                if (response_code.equals(RESPONSE_SUCCESS)) {
                                    JSONObject respObject = rootObject.getJSONObject(JTAG_RESPONSE);
                                    String statusCode = respObject.optString(JTAG_STATUSCODE);
                                    if (statusCode.equals(STATUS_SUCCESS)) {
                                        totaldays = "";
                                        unauthorised_absence = "";
                                        authorised_absence = "";
                                        late = "";
                                        present = "";
                                        presentDays = 0;
                                        pieChart.invalidate();
                                        pieChart.clear();
                                        selectedTextView.setVisibility(View.INVISIBLE);
                                        selectedTextViewDays.setVisibility(View.INVISIBLE);
                                        mAttendanceModelListDialog = new ArrayList<>();
                                        mAttendanceModelList = new ArrayList<>();
                                        mAttendanceModelListLate = new ArrayList<>();
                                        mAttendanceModelListPresent = new ArrayList<>();
                                        mAttendanceModelListAuthorisedAbsence = new ArrayList<>();
                                        mAttendanceModelListUnauthorisedAbsence = new ArrayList<>();
                                        mAttendanceChartListModelArray = new ArrayList<>();
                                        JSONObject dataJsonObj = respObject.optJSONObject(JTAG_RESPONSE_ARRAY);

                                        totaldays = dataJsonObj.optString("totaldays");
                                        present = dataJsonObj.optString("present");
                                        late = dataJsonObj.optString("late");
                                        authorised_absence = dataJsonObj.optString("authorised_absence");
                                        unauthorised_absence = dataJsonObj.optString("unauthorised_absence");
                                        presentDays = Integer.valueOf(present);


                                        pieChart.setUsePercentValues(false);
                                        // IMPORTANT: In a PieChart, no values (Entry) should have the same
                                        // xIndex (even if from different DataSets), since no values can be
                                        // drawn above each other.
                                        colorList = new ArrayList<>();
                                        colorValueList = new ArrayList<>();
                                        ArrayList<Entry> yvalues = new ArrayList<Entry>();
                                        if (!(present.equalsIgnoreCase("0"))) {
                                            colorList.add(getResources().getColor(R.color.present));

                                            if (authorised_absence.equalsIgnoreCase("0"))
                                            {
                                            }
                                            else
                                            {
                                                int authorisedAbsence = Integer.valueOf(authorised_absence);
                                                presentDays = presentDays + authorisedAbsence;
                                            }

                                            yvalues.add(new Entry(presentDays, 0));
                                            colorValueList.add(getResources().getColor(R.color.white));

                                        } else {
                                            if (authorised_absence.equalsIgnoreCase("0"))
                                            {
                                                colorList.add(getResources().getColor(R.color.present));
                                                colorValueList.add(getResources().getColor(R.color.transparent));
                                                yvalues.add(new Entry(presentDays, 0));
                                            }
                                            else
                                            {
                                                int authorisedAbsence = Integer.valueOf(authorised_absence);
                                                presentDays = authorisedAbsence;
                                                colorList.add(getResources().getColor(R.color.present));
                                                colorValueList.add(getResources().getColor(R.color.transparent));
                                                yvalues.add(new Entry(presentDays, 0));
                                            }

                                        }

                                        for (int i = 0; i < legendArrayNew.length; i++) {
                                            AttendanceChartListModel mAttendanceChartListModel = new AttendanceChartListModel();
                                            mAttendanceChartListModel.setName(legendArrayNew[i]);
                                            switch (i) {
                                                case 0:
                                                    mAttendanceChartListModel.setCount(totaldays);

                                                    break;
                                                case 1:
                                                    mAttendanceChartListModel.setCount(String.valueOf(presentDays));

                                                    break;
                                                case 2:
                                                    mAttendanceChartListModel.setCount(late);

                                                    break;
//                                                case 3:
//                                                    mAttendanceChartListModel.setCount(authorised_absence);
//
//                                                    break;
                                                case 3:
                                                    mAttendanceChartListModel.setCount(unauthorised_absence);

                                                    break;
                                            }
                                            mAttendanceChartListModel.setName(legendArrayNew[i]);
                                            mAttendanceChartListModelArray.add(mAttendanceChartListModel);
                                        }
                                        if (!(late.equalsIgnoreCase("0"))) {
                                            colorList.add(getResources().getColor(R.color.late));
                                            yvalues.add(new Entry(Integer.valueOf(late), 1));
                                            colorValueList.add(getResources().getColor(R.color.white));

                                        } else {
                                            colorList.add(getResources().getColor(R.color.late));
                                            colorValueList.add(getResources().getColor(R.color.transparent));
                                            yvalues.add(new Entry(Integer.valueOf(late), 1));
                                        }
//                                        if (!(authorised_absence.equalsIgnoreCase("0"))) {
//                                            colorList.add(getResources().getColor(R.color.authorisedabsence));
//                                            colorValueList.add(getResources().getColor(R.color.white));
//                                            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//                                        } else {
//                                            colorList.add(getResources().getColor(R.color.authorisedabsence));
//                                            colorValueList.add(getResources().getColor(R.color.transparent));
//                                            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//                                        }
                                        if (!(unauthorised_absence.equalsIgnoreCase("0"))) {
                                            colorList.add(getResources().getColor(R.color.unauthorisedabsence));
                                            colorValueList.add(getResources().getColor(R.color.white));
                                            yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
                                        } else {
                                            colorList.add(getResources().getColor(R.color.unauthorisedabsence));
                                            colorValueList.add(getResources().getColor(R.color.transparent));
                                            yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
                                        }
                                        int colors[] = new int[colorList.size()];
                                        for (int i = 0; i < colorList.size(); i++) {
                                            colors[i] = colorList.get(i);
                                        }
                                        PieDataSet dataSet = new PieDataSet(yvalues, "");
                                        ArrayList<String> xVals = new ArrayList<String>();
//                                        if (!(present.equalsIgnoreCase("0")))
                                        xVals.add("Present/Authorised absence");
//                                        if (!(late.equalsIgnoreCase("0")))
                                        xVals.add("Late");
//                                        if (!(authorised_absence.equalsIgnoreCase("0")))
                                        //   xVals.add("Authorised absence");
//                                        if (!(unauthorised_absence.equalsIgnoreCase("0")))
                                        xVals.add("Unauthorised absence");
                                        PieData data = new PieData(xVals, dataSet);
                                        // In Percentage
//                                        data.setValueFormatter(new PercentFormatter());
                                        data.setValueFormatter(new LargeValueFormatter());
                                        // Default value
//                                        data.setValueFormatter(new DefaultValueFormatter(0));
                                        Legend l = pieChart.getLegend();
                                        l.setEnabled(false);
                                        pieChart.setData(data);
                                        pieChart.setRotationEnabled(false);
                                        pieChart.setDescriptionTextSize(100f);
                                        pieChart.setDescriptionColor(R.color.white);
                                        pieChart.setDescription("");
                                        pieChart.setDrawSliceText(false);
                                        pieChart.setDrawHoleEnabled(false);
                                        pieChart.setTransparentCircleRadius(58f);
                                        pieChart.setHoleRadius(58f);
                                        dataSet.setColors(colors);
                                        data.setValueTextSize(15f);
                                        data.setValueTextColors(colorValueList);
                                        pieChart.animateXY(1400, 1400);
                                        JSONArray dataDetailsArray = respObject.optJSONArray(JTAG_DETAILS);

                                        if (dataDetailsArray.length() > 0) {

                                            for (int i = 0; i < dataDetailsArray.length(); i++) {
                                                JSONObject dataObject = dataDetailsArray.optJSONObject(i);
                                                AttendanceModel mAttendanceModel = new AttendanceModel();
                                                mAttendanceModel.setId(dataObject.optString("id"));
                                                mAttendanceModel.setDate(dataObject.optString("date"));
                                                mAttendanceModel.setPresent(dataObject.optString("present"));
                                                mAttendanceModel.setLate(dataObject.optString("late"));
                                                mAttendanceModel.setAuthorised_absence(dataObject.optString("authorised_absence"));
                                                mAttendanceModel.setUnauthorised_absence(dataObject.optString("unauthorised_absence"));

                                                mAttendanceModelList.add(mAttendanceModel);
//                                                if (dataObject.optString("late").equalsIgnoreCase("1")) {
//                                                    //mAttendanceModel.setTagList("Late");
//                                                    mAttendanceModelListLate.add(mAttendanceModel);
//
//                                                }
//                                                 if (dataObject.optString("present").equalsIgnoreCase("1")) {
//                                                   // mAttendanceModel.setTagList("Present");
//                                                    mAttendanceModelListPresent.add(mAttendanceModel);
//
//                                                }
                                                if ((dataObject.optString("late").equalsIgnoreCase("1"))&&(dataObject.optString("present").equalsIgnoreCase("1")))
                                                {
                                                    mAttendanceModel.setLate("1");
                                                    mAttendanceModel.setPresent("0");
                                                    mAttendanceModelListLate.add(mAttendanceModel);
                                                    AttendanceModel mDataModel = new AttendanceModel();
                                                    mDataModel.setId(dataObject.optString("id"));
                                                    mDataModel.setDate(dataObject.optString("date"));
                                                    mDataModel.setPresent(dataObject.optString("present"));
                                                    mDataModel.setAuthorised_absence(dataObject.optString("authorised_absence"));
                                                    mDataModel.setUnauthorised_absence(dataObject.optString("unauthorised_absence"));
                                                    mDataModel.setLate("0");
                                                    mAttendanceModelListPresent.add(mDataModel);

                                                    mAttendanceModelList.add(mDataModel);
                                                }
                                                else
                                                {
                                                    if (dataObject.optString("late").equalsIgnoreCase("1")) {
                                                        //mAttendanceModel.setTagList("Late");
                                                        mAttendanceModelListLate.add(mAttendanceModel);

                                                    }
                                                    if (dataObject.optString("present").equalsIgnoreCase("1")) {
                                                        // mAttendanceModel.setTagList("Present");
                                                        mAttendanceModelListPresent.add(mAttendanceModel);

                                                    }
                                                }
                                                if (dataObject.optString("authorised_absence").equalsIgnoreCase("1")) {
                                                    //mAttendanceModel.setTagList("Authorised absence");
                                                    mAttendanceModelListPresent.add(mAttendanceModel);

                                                }
                                                if (dataObject.optString("unauthorised_absence").equalsIgnoreCase("1")) {
                                                    //mAttendanceModel.setTagList("Unauthorised absence");
                                                    mAttendanceModelListUnauthorisedAbsence.add(mAttendanceModel);

                                                }



                                            }
                                        }
                                        mChartListRecyclerAdapter = new ChartListRecyclerAdapter(mContext, mAttendanceChartListModelArray);
                                        listView.setAdapter(mChartListRecyclerAdapter);
                                        mSelectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceModelList, mAttendanceChartListModelArray);
                                        listViewGraph.setAdapter(mSelectedListRecyclerAdapter);
                                        if (mAttendanceModelList.size() <= 0) {
                                            upArrowImage.setVisibility(View.GONE);
                                            dividerTv.setVisibility(View.GONE);
                                            attendanceTextView.setVisibility(View.GONE);
                                        } else {
                                            upArrowImage.setVisibility(View.VISIBLE);
                                            dividerTv.setVisibility(View.VISIBLE);
                                            attendanceTextView.setVisibility(View.VISIBLE);

                                        }
                                        mAttendanceModelListDialog = (ArrayList<AttendanceModel>) mAttendanceModelList.clone();

                                    }
                                    else if(statusCode.equalsIgnoreCase("707"))
                                    {

                                    }
                                    else {
                                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.some_error), R.drawable.exclamationicon, R.drawable.round);

                                    }
                                } else if (response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                        response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        response_code.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                    AppUtils.postInitParam(getActivity(), new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    getList(URL, student_id);

                                } else if (response_code.equals(RESPONSE_ERROR)) {
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                }
                            } else {
                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void responseFailure(String failureResponse) {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }
    void reloadPieChart()
    {
        pieChart.invalidate();
        pieChart.clear();
        mAttendanceChartListModelArray = new ArrayList<>();
        selectedTextView.setVisibility(View.INVISIBLE);
        selectedTextViewDays.setVisibility(View.INVISIBLE);
        pieChart.setUsePercentValues(false);
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        colorList = new ArrayList<>();
        colorValueList = new ArrayList<>();
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        if (!(present.equalsIgnoreCase("0"))) {
            colorList.add(getResources().getColor(R.color.present));
            yvalues.add(new Entry(presentDays, 0));
            colorValueList.add(getResources().getColor(R.color.white));

        } else {
            colorList.add(getResources().getColor(R.color.present));
            colorValueList.add(getResources().getColor(R.color.transparent));
            yvalues.add(new Entry(presentDays, 0));
        }

        for (int i = 0; i < legendArrayNew.length; i++) {
            AttendanceChartListModel mAttendanceChartListModel = new AttendanceChartListModel();
            mAttendanceChartListModel.setName(legendArrayNew[i]);
            switch (i) {
                case 0:
                    mAttendanceChartListModel.setCount(totaldays);

                    break;
                case 1:
                    mAttendanceChartListModel.setCount(String.valueOf(presentDays));

                    break;
                case 2:
                    mAttendanceChartListModel.setCount(late);

                    break;
//                case 3:
//                    mAttendanceChartListModel.setCount(authorised_absence);

                // break;
                case 3:
                    mAttendanceChartListModel.setCount(unauthorised_absence);

                    break;
            }
            mAttendanceChartListModel.setName(legendArrayNew[i]);
            mAttendanceChartListModelArray.add(mAttendanceChartListModel);
        }
        System.out.println("color value list"+colorList.size());
        if (!(late.equalsIgnoreCase("0"))) {
            colorList.add(getResources().getColor(R.color.late));
            yvalues.add(new Entry(Integer.valueOf(late), 1));
            colorValueList.add(getResources().getColor(R.color.white));

        } else {
            colorList.add(getResources().getColor(R.color.late));
            colorValueList.add(getResources().getColor(R.color.transparent));
            yvalues.add(new Entry(Integer.valueOf(late), 1));
        }
//        if (!(authorised_absence.equalsIgnoreCase("0"))) {
//            colorList.add(getResources().getColor(R.color.authorisedabsence));
//            colorValueList.add(getResources().getColor(R.color.white));
//            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//        } else {
//            colorList.add(getResources().getColor(R.color.authorisedabsence));
//            colorValueList.add(getResources().getColor(R.color.transparent));
//            yvalues.add(new Entry(Integer.valueOf(authorised_absence), 2));
//        }
        if (!(unauthorised_absence.equalsIgnoreCase("0"))) {
            colorList.add(getResources().getColor(R.color.unauthorisedabsence));
            colorValueList.add(getResources().getColor(R.color.white));
            yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
        } else {
            colorList.add(getResources().getColor(R.color.unauthorisedabsence));
            colorValueList.add(getResources().getColor(R.color.transparent));
            yvalues.add(new Entry(Integer.valueOf(unauthorised_absence), 2));
        }
        int colors[] = new int[colorList.size()];
        for (int i = 0; i < colorList.size(); i++) {
            colors[i] = colorList.get(i);
        }
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<String> xVals = new ArrayList<String>();
//                                        if (!(present.equalsIgnoreCase("0")))
        xVals.add("Present/Authorised absence");
//                                        if (!(late.equalsIgnoreCase("0")))
        xVals.add("Late");
//                                        if (!(authorised_absence.equalsIgnoreCase("0")))
        //      xVals.add("Authorised absence");
//                                        if (!(unauthorised_absence.equalsIgnoreCase("0")))
        xVals.add("Unauthorised absence");
        PieData data = new PieData(xVals, dataSet);
        // In Percentage
//                                        data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new LargeValueFormatter());
        // Default value
//                                        data.setValueFormatter(new DefaultValueFormatter(0));
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        pieChart.setData(data);
        pieChart.setRotationEnabled(false);
        pieChart.setDescriptionTextSize(100f);
        pieChart.setDescriptionColor(R.color.white);
        pieChart.setDescription("");
        pieChart.setDrawSliceText(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(colors);
        data.setValueTextSize(15f);
        data.setValueTextColors(colorValueList);
        pieChart.animateXY(1400, 1400);

    }

    public void showAttendanceList(final ArrayList<AttendanceModel> mAttendanceList)
    {
        final Dialog  dialogAttendance = new Dialog(mContext,R.style.DialogTheme);
        dialogAttendance.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager manager = (WindowManager) mContext.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        width = point.x;
        height = point.y;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogAttendance.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        dialogAttendance.getWindow().setAttributes(lp);

//        dialogAttendance.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT);

        dialogAttendance.getWindow().getAttributes().windowAnimations = R.style.DialogAnimatioUp;
        dialogAttendance.setCancelable(false);
//        dialogAttendance.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogAttendance.setContentView(R.layout.dialog_attendance);
        ImageView closeImageView = dialogAttendance.findViewById(R.id.closeImageView);
        RecyclerView recycler_view_attendance = dialogAttendance.findViewById(R.id.recycler_view_attendance);

//        socialMediaList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        recycler_view_attendance.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_attendance.setLayoutManager(llm);

        SelectedListRecyclerAdapter selectedListRecyclerAdapter = new SelectedListRecyclerAdapter(mContext, mAttendanceList, mAttendanceChartListModelArray);
        recycler_view_attendance.setAdapter(selectedListRecyclerAdapter);
        closeImageView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialogAttendance.dismiss();

            }

        });

        dialogAttendance.show();
        dialogAttendance.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogAttendance.dismiss();
                }
                return true;
            }
        });
    }
            public static boolean checkInternet(Context context) {
                ConnectivityManager connec = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connec.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnectedOrConnecting();
            }
            public static void showDialogAlertDismiss(final Activity activity, String msgHead, String msg, int ico, int bgIcon) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert_dialogue_ok_layout);
                ImageView icon = dialog.findViewById(R.id.iconImageView);
                icon.setBackgroundResource(bgIcon);
                icon.setImageResource(ico);
                TextView text = dialog.findViewById(R.id.text_dialog);
                TextView textHead = dialog.findViewById(R.id.alertHead);
                text.setText(msg);
                textHead.setText(msgHead);

                Button dialogButton = dialog.findViewById(R.id.btn_Ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
//		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});

                dialog.show();

            }
            public static boolean isNetworkConnected(Context context) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                return ni != null;
            }
    @Override
    public void onResume() {
        super.onResume();
        if(!PreferenceManagerr.getUserId(mContext).equalsIgnoreCase(""))
        {
          /*  AppController().getInstance().getGoogleAnalyticsTracker().set("&uid", PreferenceManager().getUserId(mContext));
            AppController().getInstance().getGoogleAnalyticsTracker().set("&cid", PreferenceManager().getUserId(mContext));
            AppController().getInstance().trackScreenView("Attendance Fragment." +" "+"("+ PreferenceManager().getUserEmail(mContext)+")"+" "+"("+ Calendar.getInstance().getTime()+")");
*/
        }

    }

}
