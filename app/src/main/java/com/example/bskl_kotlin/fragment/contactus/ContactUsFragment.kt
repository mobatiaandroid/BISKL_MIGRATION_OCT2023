package com.example.bskl_kotlin.fragment.contactus

import ApiClient
import android.content.Context
import android.content.Intent
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
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()

        contactUsApi()

    }
    private fun contactUsApi(){
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

                        contactUsList.addAll(responsedata!!.response.data.contacts)
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