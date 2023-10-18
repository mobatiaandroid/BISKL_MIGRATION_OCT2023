package com.example.bskl_kotlin.fragment.contactus

import ApiClient
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.contactus.adapter.ContactusAdapter
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsListModel
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactUsFragment(title: String, tabId: String) : Fragment() {
    lateinit var mContext: Context
    private val TAB_ID: String? = null
    var anim: RotateAnimation? = null
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var c_latitude: String
    lateinit var c_longitude: String
    lateinit var description: String
    lateinit var contactUsList: ArrayList<ContactUsListModel>
    lateinit var locationManager: LocationManager
    lateinit var contact_usrecyclerview: RecyclerView
    var isGPSEnabled: Boolean? = null
    var isNetworkEnabled: Boolean? = null
    var lat: Double = 0.0
    var long: Double = 0.0
    lateinit var mapFragment: SupportMapFragment
    lateinit var map: GoogleMap
    lateinit var desc: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()

        initUi()
       //fetchlatitudelongitude()
        contactUsApi()

    }
    private fun initUi(){
        contactUsList= ArrayList()
        contact_usrecyclerview = requireView().findViewById<RecyclerView>(R.id.mnewsLetterListView)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Contact us"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        desc = requireView().findViewById<TextView>(R.id.description)
        mapFragment =
            (childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?)!!
    }
    /*private fun fetchlatitudelongitude() {
        var location: Location
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled!! && !isNetworkEnabled!!) {

        } else {
            if (isNetworkEnabled as Boolean) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    *//*locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0.0F,
                        this
                    )*//*

                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    if (location != null) {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
            }
            if (isGPSEnabled as Boolean) {
               *//* locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0.0F,
                    this
                )*//*
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                    println("lat---$lat")
                    println("lat---$long")
                    Log.e("CONTACTLATITUDE:", (lat + long).toString())
                }
            }
        }
        c_latitude = lat.toString()
        c_longitude = long.toString()

    }*/
    private fun contactUsApi(){
        contactUsList=ArrayList()
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim!!.setInterpolator(mContext, android.R.interpolator.linear)
        anim!!.setRepeatCount(Animation.INFINITE)
        anim!!.setDuration(1000)

        val call: Call<ContactUsModel> = ApiClient.getClient.contact_us(
            PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<ContactUsModel> {
            override fun onFailure(call: Call<ContactUsModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<ContactUsModel>,
                response: Response<ContactUsModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        latitude=responsedata.response.data.latitude
                        longitude=responsedata.response.data.longitude
                        description=responsedata.response.data.description
                        desc.setText(description)

                        contactUsList.addAll(responsedata!!.response.data.contacts)
                        contact_usrecyclerview.layoutManager=LinearLayoutManager(mContext)
                        val contactusAdapter = ContactusAdapter(mContext,contactUsList)
                        contact_usrecyclerview.adapter = contactusAdapter

                    }
                }
                mapFragment.getMapAsync { googleMap ->
                    Log.d("Map Working", "good")
                    map = googleMap
                    map.uiSettings.isMapToolbarEnabled = false
                    map.uiSettings.isZoomControlsEnabled = false
                    val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
                    map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .title("BSKL")
                    )


                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    map.animateCamera(CameraUpdateFactory.zoomTo(13f))
                    map.setOnInfoWindowClickListener {

                        if (!isGPSEnabled!!) {
                            val callGPSSettingIntent = Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                            startActivity(callGPSSettingIntent)
                        } else {
                            //val url = "http://maps.google.com/maps?saddr=$c_latitude,$c_longitude&daddr=The British International School,Abudhabi"
                            val url = "http://maps.google.com/maps?saddr=\" + c_latitude + \",\" + c_longitude + \"&daddr=British International School Kulalalumpur - Kulalalumpur "

                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }


                    }
                }
            }
        })

    }
}