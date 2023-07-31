package com.example.bskl_kotlin.fragment.timetable.adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.timetable.model.FieldModel
import com.example.bskl_kotlin.fragment.timetable.model.PeriodModel
import com.example.bskl_kotlin.manager.AppUtils
import com.ryanharter.android.tooltips.ToolTip
import com.ryanharter.android.tooltips.ToolTipLayout

class TimeTableAllWeekSelectionAdapterNew(

    private val mContext: Context,
   private var mPeriodList: ArrayList<PeriodModel>,
  private var mFeildList: ArrayList<FieldModel>,
   private var tipContainer: ToolTipLayout,



) :
    RecyclerView.Adapter<TimeTableAllWeekSelectionAdapterNew.MyViewHolder>() {

    var isClick = false

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var periodTxt: TextView
        var timeTxt: TextView
        var tutor1: TextView
        var tutor2: TextView
        var tutor3: TextView
        var tutor4: TextView
        var tutor5: TextView
        var timeBreak: TextView
        var txtBreak: TextView
        var countMTextView: TextView
        var countTTextView: TextView
        var countWTextView: TextView
        var countThTextView: TextView
        var countFTextView: TextView
        var timeLinear: LinearLayout
        var tutorLinear: LinearLayout
        var starLinearR: LinearLayout
        var relSub: LinearLayout
        var llread: RelativeLayout
        var countMRel: RelativeLayout
        var countTRel: RelativeLayout
        var countWRel: RelativeLayout
        var countThRel: RelativeLayout
        var countFRel: RelativeLayout

        init {
            countFTextView = view.findViewById<View>(R.id.countFTextView) as TextView
            countThTextView = view.findViewById<View>(R.id.countThTextView) as TextView
            countWTextView = view.findViewById<View>(R.id.countWTextView) as TextView
            countTTextView = view.findViewById<View>(R.id.countTTextView) as TextView
            countMTextView = view.findViewById<View>(R.id.countMTextView) as TextView
            periodTxt = view.findViewById<View>(R.id.periodTxt) as TextView
            timeTxt = view.findViewById<View>(R.id.timeTxt) as TextView
            tutor1 = view.findViewById<View>(R.id.tutor1) as TextView
            tutor2 = view.findViewById<View>(R.id.tutor2) as TextView
            tutor3 = view.findViewById<View>(R.id.tutor3) as TextView
            tutor4 = view.findViewById<View>(R.id.tutor4) as TextView
            tutor5 = view.findViewById<View>(R.id.tutor5) as TextView
            timeBreak = view.findViewById<View>(R.id.timeBreak) as TextView
            txtBreak = view.findViewById<View>(R.id.txtBreak) as TextView
            timeLinear = view.findViewById<View>(R.id.timeLinear) as LinearLayout
            tutorLinear = view.findViewById<View>(R.id.tutorLinear) as LinearLayout
            starLinearR = view.findViewById<View>(R.id.starLinearR) as LinearLayout
            relSub = view.findViewById<View>(R.id.relSub) as LinearLayout
            llread = view.findViewById<View>(R.id.llread) as RelativeLayout
            countMRel = view.findViewById<View>(R.id.countMRel) as RelativeLayout
            countTRel = view.findViewById<View>(R.id.countTRel) as RelativeLayout
            countWRel = view.findViewById<View>(R.id.countWRel) as RelativeLayout
            countThRel = view.findViewById<View>(R.id.countThRel) as RelativeLayout
            countFRel = view.findViewById<View>(R.id.countFRel) as RelativeLayout
        }
    }

    init {
        this.mPeriodList = mPeriodList

        this.tipContainer = tipContainer
        this.mFeildList = mFeildList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_selection_time_table_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.periodTxt.setText(mFeildList[position].sortname)
        holder.timeTxt.visibility = View.GONE
        // holder.timeTxt.setText(AppUtils.timeParsingToAmPm(mPeriodList.get(position).getStarttime()));
        if (!mFeildList[position].periodId.equals("")) {
            holder.timeLinear.visibility = View.VISIBLE
            holder.tutorLinear.visibility = View.GONE
            holder.relSub.visibility = View.GONE
            holder.llread.visibility = View.GONE
            holder.starLinearR.visibility = View.GONE
            holder.timeBreak.visibility = View.INVISIBLE
            //holder.timeBreak.setText(AppUtils.timeParsingToAmPm(mPeriodList.get(position).getStarttime()));
            holder.txtBreak.setText(mFeildList[position].sortname)
            Log.e("BREAK TE", mFeildList[position].sortname!!)
            if (mFeildList[position].countBreak === 1) {
                if (mFeildList[position].sortname.equals("Break")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.lunch_bar)
                } else if (mFeildList[position].sortname.equals("Lunch")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.yellow_bar)
                } else {
                    holder.timeLinear.setBackgroundResource(R.drawable.violet_bar)
                }
            } else if (mFeildList[position].countBreak === 2) {
                if (mFeildList[position].sortname.equals("Break")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.lunch_bar)
                } else if (mFeildList[position].sortname.equals("Lunch")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.yellow_bar)
                } else {
                    holder.timeLinear.setBackgroundResource(R.drawable.violet_bar)
                }
            } else if (mFeildList[position].countBreak === 3) {
                if (mFeildList[position].sortname.equals("Break")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.lunch_bar)
                } else if (mFeildList[position].sortname.equals("Lunch")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.yellow_bar)
                } else {
                    holder.timeLinear.setBackgroundResource(R.drawable.violet_bar)
                }
            } else {
                if (mFeildList[position].sortname.equals("Break")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.lunch_bar)
                } else if (mFeildList[position].sortname.equals("Lunch")) {
                    holder.timeLinear.setBackgroundResource(R.drawable.yellow_bar)
                } else {
                    holder.timeLinear.setBackgroundResource(R.drawable.violet_bar)
                }
            }
        } else if (mPeriodList[position].timeTableDayModel!!.size > 0) {
            holder.timeLinear.visibility = View.GONE
            holder.tutorLinear.visibility = View.VISIBLE
            holder.llread.visibility = View.VISIBLE
            holder.starLinearR.visibility = View.VISIBLE
            holder.relSub.visibility = View.VISIBLE
            if (mPeriodList[position].timeTableListM!!.size > 1) {
                holder.tutor1.setText(
                    mPeriodList[position].timeTableListM!!.get(0)!!.subject_name
                )
            } else {
                holder.tutor1.setText(mPeriodList[position].monday)
            }
            if (mPeriodList[position].timeTableListT!!.size > 1) {
                holder.tutor2.setText(
                    mPeriodList[position].timeTableListT!!.get(0)!!.subject_name
                )
            } else {
                holder.tutor2.setText(mPeriodList[position].tuesday)
            }
            if (mPeriodList[position].timeTableListW!!.size > 1) {
                holder.tutor3.setText(
                    mPeriodList[position].timeTableListW!!.get(0)!!.subject_name
                )
            } else {
                holder.tutor3.setText(mPeriodList[position].wednesday)
            }
            if (mPeriodList[position].timeTableListTh!!.size > 1) {
                holder.tutor4.setText(
                    mPeriodList[position].timeTableListTh!!.get(0)!!.subject_name
                )
            } else {
                holder.tutor4.setText(mPeriodList[position].thursday)
            }
            if (mPeriodList[position].timeTableListF!!.size > 1) {
                holder.tutor5.setText(
                    mPeriodList[position].timeTableListF!!.get(0)!!.subject_name
                )
            } else {
                holder.tutor5.setText(mPeriodList[position].friday)
            }
            if (mPeriodList[position].timeTableListM!!.size > 1) {
                holder.countMRel.visibility = View.VISIBLE
                holder.countMTextView.visibility = View.VISIBLE
                val count: Int = mPeriodList[position].timeTableListM!!.size - 1
                holder.countMTextView.text = "+$count"
            } else {
                holder.countMRel.visibility = View.GONE
                holder.countMTextView.visibility = View.GONE
            }
            if (mPeriodList[position].timeTableListT!!.size > 1) {
                holder.countTRel.visibility = View.VISIBLE
                holder.countTTextView.visibility = View.VISIBLE
                val count: Int = mPeriodList[position].timeTableListT!!.size - 1
                holder.countTTextView.text = "+$count"
            } else {
                holder.countTRel.visibility = View.GONE
                holder.countTTextView.visibility = View.GONE
            }
            if (mPeriodList[position].timeTableListW!!.size > 1) {
                holder.countWRel.visibility = View.VISIBLE
                holder.countWTextView.visibility = View.VISIBLE
                val count: Int = mPeriodList[position].timeTableListW!!.size - 1
                holder.countWTextView.text = "+$count"
            } else {
                holder.countWRel.visibility = View.GONE
                holder.countWTextView.visibility = View.GONE
            }
            if (mPeriodList[position].timeTableListTh!!.size > 1) {
                holder.countThRel.visibility = View.VISIBLE
                holder.countThTextView.visibility = View.VISIBLE
                val count: Int = mPeriodList[position].timeTableListTh!!.size - 1
                holder.countThTextView.text = "+$count"
            } else {
                holder.countThRel.visibility = View.GONE
                holder.countThTextView.visibility = View.GONE
            }
            if (mPeriodList[position].timeTableListF!!.size > 1) {
                holder.countFRel.visibility = View.VISIBLE
                holder.countFTextView.visibility = View.VISIBLE
                val count: Int = mPeriodList[position].timeTableListF!!.size - 1
                holder.countFTextView.text = "+$count"
            } else {
                holder.countFRel.visibility = View.GONE
                holder.countFTextView.visibility = View.GONE
            }
        } else {
            holder.tutor1.text = ""
            holder.tutor2.text = ""
            holder.tutor3.text = ""
            holder.tutor4.text = ""
            holder.tutor5.text = ""
            holder.countMRel.visibility = View.GONE
            holder.countMTextView.visibility = View.GONE
            holder.countTRel.visibility = View.GONE
            holder.countTTextView.visibility = View.GONE
            holder.countWRel.visibility = View.GONE
            holder.countWTextView.visibility = View.GONE
            holder.countThRel.visibility = View.GONE
            holder.countThTextView.visibility = View.GONE
            holder.countFRel.visibility = View.GONE
            holder.countFTextView.visibility = View.GONE
        }
        holder.timeLinear.setOnClickListener {
            if (holder.txtBreak.text.toString().equals("", ignoreCase = true)) {
            } else {
                isClick = true
                println("mon:::" + mFeildList.size)
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity_break, null, false)
                val mondayTime = itemView.findViewById<TextView>(R.id.mondayTime)
                val fridayTime = itemView.findViewById<TextView>(R.id.fridayTime)
                val sortName = itemView.findViewById<TextView>(R.id.sortName)
                mondayTime.text =
                    "Monday-Thursday |" + AppUtils().timeParsingToAmPm(
                        mFeildList[position].starttime
                    ).toString() + "-" + AppUtils().timeParsingToAmPm(
                        mFeildList[position].endtime
                    )
                fridayTime.text =
                    "Friday |" + AppUtils().timeParsingToAmPm(
                        mFeildList[position].fridyaStartTime
                    ).toString() + "-" + AppUtils().timeParsingToAmPm(
                        mFeildList[position].fridayEndTime
                    )
                sortName.setText(mFeildList[position].sortname)


                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                println("Position clicked$position")
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.timeLinear) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.timeLinear) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
        holder.tutor1.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor1.text.toString().equals("", ignoreCase = true)) {
            } else {
                isClick = true
                System.out.println(
                    "mon:::" + mPeriodList[position].timeTableListM!!.size
                )
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable =
                    itemView.findViewById<RecyclerView>(R.id.recycler_view_timetable)
                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(
                    mContext, mPeriodList[position].timeTableListM
                )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor1) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor1) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
        holder.tutor2.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor2.text.toString().equals("", ignoreCase = true)) {
            } else {
                System.out.println(
                    "tue:::" + mPeriodList[position].timeTableListT!!.size
                )
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable =
                    itemView.findViewById<RecyclerView>(R.id.recycler_view_timetable)
                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(
                    mContext, mPeriodList[position].timeTableListT
                )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor2) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor2) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
        holder.tutor3.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor3.text.toString().equals("", ignoreCase = true)) {
            } else {
                System.out.println(
                    "wed:::" + mPeriodList[position].timeTableListW!!.size
                )
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable =
                    itemView.findViewById<RecyclerView>(R.id.recycler_view_timetable)
                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(
                    mContext, mPeriodList[position].timeTableListW
                )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor3) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor3) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
        holder.tutor4.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor4.text.toString().equals("", ignoreCase = true)) {
            } else {
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable =
                    itemView.findViewById<RecyclerView>(R.id.recycler_view_timetable)
                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(
                    mContext, mPeriodList[position].timeTableListTh
                )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor4) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor4) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
        holder.tutor5.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor5.text.toString().equals("", ignoreCase = true)) {
            } else {
                System.out.println(
                    "fri:" + mPeriodList[position].timeTableListF!!.size
                )
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable =
                    itemView.findViewById<RecyclerView>(R.id.recycler_view_timetable)
                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(
                    mContext, mPeriodList[position].timeTableListF
                )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor5) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor5) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }
    }

    override fun getItemCount(): Int {
        println("period size" + mPeriodList.size)
        return mFeildList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}