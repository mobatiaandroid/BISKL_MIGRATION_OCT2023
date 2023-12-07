package com.example.bskl_kotlin.fragment.attendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.fragment.absence.model.StudentModel;
import com.example.bskl_kotlin.fragment.attendance.model.StudentModelJava;
import com.example.bskl_kotlin.manager.AppUtils;

import java.util.ArrayList;

public class StrudentSpinnerAdapter extends RecyclerView.Adapter<StrudentSpinnerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<StudentModelJava> mStudentList;
    String dept;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTxt,listTxtClass;
        ImageView imgIcon;
        RelativeLayout relSub;

        public MyViewHolder(View view) {
            super(view);

            mTitleTxt= view.findViewById(R.id.listTxtTitle);
            listTxtClass= view.findViewById(R.id.listTxtClass);
            imgIcon= view.findViewById(R.id.imagicon);
            relSub= view.findViewById(R.id.relSub);
        }
    }


    public StrudentSpinnerAdapter(Context mContext, ArrayList<StudentModelJava> mStudentList) {
        this.mContext = mContext;
        this.mStudentList = mStudentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mStudentList.get(position).getAlumi().equalsIgnoreCase("0")) {
            holder.relSub.setVisibility(View.VISIBLE);
            holder.mTitleTxt.setText(mStudentList.get(position).getName());
            holder.imgIcon.setVisibility(View.VISIBLE);
            holder.listTxtClass.setText(mStudentList.get(position).getmClass());
            if (!mStudentList.get(position).getPhoto().equals("")) {

                Glide.with(mContext).load(mStudentList.get(position).getPhoto().toString()).placeholder(R.drawable.boy).into(holder.imgIcon);
            } else

            {

                holder.imgIcon.setImageResource(R.drawable.boy);
            }
        }
        else
        {
            holder.relSub.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
