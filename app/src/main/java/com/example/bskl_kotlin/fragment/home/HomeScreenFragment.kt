package com.example.bskl_kotlin.fragment.home

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.mobatia.bskl.R

class HomeScreenFragment(title:String,
                         mDrawerLayouts: DrawerLayout, listView: ListView, linearLayout: LinearLayout,
                         mListItemArray:ArrayList<String>, mListImgArray: TypedArray
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_activity, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



}
}