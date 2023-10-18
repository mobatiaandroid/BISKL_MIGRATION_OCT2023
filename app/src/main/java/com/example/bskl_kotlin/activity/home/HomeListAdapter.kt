package com.example.bskl_kotlin.activity.home

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager

class HomeListAdapter : BaseAdapter {
    private var mContext: Context
    private var mListItemArray: Array<String>
    private var mListImgArray: TypedArray? = null
    private var customLayout: Int
    private var mDisplayListImage: Boolean

    constructor(
        context: Context, listItemArray: Array<String>,
        listImgArray: TypedArray?, customLayout: Int, displayListImage: Boolean
    ) {
        mContext = context
        mListItemArray = listItemArray
        mListImgArray = listImgArray
        this.customLayout = customLayout
        mDisplayListImage = displayListImage
    }

    constructor(
        context: Context, listItemArray: Array<String>,
        customLayout: Int, displayListImage: Boolean
    ) {
        mContext = context
        mListItemArray = listItemArray
        this.customLayout = customLayout
        mDisplayListImage = displayListImage
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return mListItemArray.size
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return mListItemArray[position]
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            val mInflater = mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = mInflater.inflate(customLayout, null)
            holder = ViewHolder()
            holder.txtTitle = convertView.findViewById(R.id.listTxtView)
            holder.imgView = convertView.findViewById(R.id.listImg)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

//        TextView txtTitle = (TextView) convertView
//                .findViewById(R.id.listTxtView);
//        ImageView imgView = (ImageView) convertView.findViewById(R.id.listImg);
        holder.txtTitle!!.text = mListItemArray[position]
        if (mDisplayListImage) {
            holder.imgView!!.visibility = View.VISIBLE
            holder.imgView!!.setImageDrawable(mListImgArray!!.getDrawable(position))
        } else {
            holder.imgView!!.visibility = View.GONE
        }
        if (PreferenceManager().getIsVisible(mContext).equals("1")) {
            if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                if (mListItemArray[position].equals(
                        "Home"
                    ) || mListItemArray[position].equals(
                        "Reports"
                    ) || mListItemArray[position].equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.logout_user))
                }
            } else {
                if (mListItemArray[position].equals(
                        "Home"
                    ) || mListItemArray[position].equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.logout_user))
                }
            }

//            if(mListItemArray[position].equalsIgnoreCase("Home") || mListItemArray[position].equalsIgnoreCase("Reports")|| mListItemArray[position].equalsIgnoreCase("Contact us"))
//            {
//                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.white));
//
//            }
//            else
//            {
//                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.logout_user));
//
//            }
        } else {
            if (PreferenceManager().getIsApplicant(mContext).equals("1")) {
                if (mListItemArray[position].equals(
                        "Home"
                    ) || mListItemArray[position].equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.logout_user))
                }
            } else {
//|| mListItemArray[position].equalsIgnoreCase("Timetable")
                if (PreferenceManager().getReportMailMerge(mContext).equals("0")) {
                    if (mListItemArray[position].equals(
                            "Home"
                        ) || mListItemArray[position].equals(
                            "Messages"
                        ) || mListItemArray[position].equals(
                            "Calendar"
                        ) || mListItemArray[position].equals(
                            "BSKL News"
                        ) || mListItemArray[position].equals(
                            "Social Media"
                        ) || mListItemArray[position].equals("Contact us")
                    ) {
                        holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.white))
                    } else {
                        holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.logout_user))
                    }
                } else {
                    holder.txtTitle!!.setTextColor(mContext.resources.getColor(R.color.white))
                }
            }
        }
        return convertView!!
    }

    internal class ViewHolder {
        var txtTitle: TextView? = null
        var imgView: ImageView? = null
    }
}
/*
class HomeListAdapter(
    private val context: Activity,
    private val mListItemArray: Array<String>,
    private val mListImgArray: TypedArray,
    private val customLayout:Int,
    private val mDisplayListImage:Boolean
) : ArrayAdapter<String>(context, R.layout.custom_list_adapter) {

lateinit var mContext:Context
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
Log.e("adapt","homelistt")
        val holder: ViewHolder

        if (convertView == null) {
            Log.e("adapt","view null")
            val inflater = context.layoutInflater
            var convertView = inflater.inflate((R.layout.custom_list_adapter), null, true)
            holder= ViewHolder()
            holder.txtTitle = convertView!!.findViewById(R.id.listTxtView) as TextView
            holder.imgView = convertView!!.findViewById(R.id.listImg) as ImageView
            convertView.setTag(holder)
        } else {
            Log.e("adapt","view not null")
            holder =
                convertView.getTag() as ViewHolder
        }
Log.e("adapt size",mListItemArray.size.toString())
        holder.txtTitle!!.setText(mListItemArray.get(position))
        if (mDisplayListImage) {
            holder.imgView!!.setVisibility(View.VISIBLE)
            holder.imgView!!.setImageDrawable(mListImgArray.getDrawable(position))
        } else {
            holder.imgView?.setVisibility(View.GONE)
        }
        if (PreferenceManager().getIsVisible(mContext).equals("1")) {
            if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                if (mListItemArray.get(position)
                        .equals("Home") || mListItemArray.get(position)
                        .equals("Reports") || mListItemArray.get(position)
                        .equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.getResources().getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(
                        mContext.getResources().getColor(R.color.logout_user)
                    )
                }
            } else {
                if (mListItemArray.get(position)
                        .equals("Home") || mListItemArray.get(position)
                        .equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.getResources().getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(
                        mContext.getResources().getColor(R.color.logout_user)
                    )
                }
            }

        } else {
            if (PreferenceManager().getIsApplicant(mContext).equals("1")) {
                if (mListItemArray.get(position)
                        .equals("Home") || mListItemArray.get(position)
                        .equals("Contact us")
                ) {
                    holder.txtTitle!!.setTextColor(mContext.getResources().getColor(R.color.white))
                } else {
                    holder.txtTitle!!.setTextColor(
                        mContext.getResources().getColor(R.color.logout_user)
                    )
                }
            } else {
//|| mListItemArray[position].equalsIgnoreCase("Timetable")
                if (PreferenceManager().getReportMailMerge(mContext).equals("0")) {
                    if (mListItemArray.get(position)
                            .equals("Home") || mListItemArray.get(position)
                            .equals("Messages") || mListItemArray.get(position)
                            .equals("Calendar") || mListItemArray.get(position)
                            .equals("BSKL News") || mListItemArray.get(position)
                            .equals("Social Media") || mListItemArray.get(
                            position
                        ).equals("Contact us")
                    ) {
                        holder.txtTitle!!.setTextColor(
                            mContext.getResources().getColor(R.color.white)
                        )
                    } else {
                        holder.txtTitle!!.setTextColor(
                            mContext.getResources().getColor(R.color.logout_user)
                        )
                    }
                } else {
                    holder.txtTitle!!.setTextColor(mContext.getResources().getColor(R.color.white))
                }
            }
        }

        return convertView!!


    }
    internal class ViewHolder {
        var txtTitle: TextView? = null
        var imgView: ImageView? = null
    }
}*/
