package com.example.bskl_kotlin.fragment.social_media

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.web_view.LoadWebViewActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.home.bsklNameConstants
import com.example.bskl_kotlin.fragment.home.bsklTabConstants
import com.example.bskl_kotlin.fragment.social_media.model.InstagramModel
import com.example.bskl_kotlin.fragment.social_media.model.YoutubeModel
import com.example.bskl_kotlin.manager.AppUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SocialMediaFragment     (title: String, tabId: String) : Fragment() {

    lateinit var mContext: Context
    private val mRootView: View? = null
    private val mTitle: String? = null
    private val mTabId: String? = null
    private var relMain: RelativeLayout? = null
    private var youtubeBtnRelative: RelativeLayout? = null
    private var instagramButtonLinear: LinearLayout? = null
    private var facebookButtonLinear: LinearLayout? = null
    var nextPageToken = ""
    var youtubeImg: ImageView? = null
    var instagramImg: ImageView? = null
    var fbImg: ImageView? = null
    var fbTextView: TextView? = null
    var fbTextViewOnly: TextView? = null
    var InstaTextViewOnly: TextView? = null
    lateinit var instaTextView: TextView
    lateinit var mYoutubeModelArrayList: ArrayList<YoutubeModel>
    lateinit var mInstagramModelArrayList: ArrayList<InstagramModel>
    lateinit var fbJsonArray: JSONArray
    var YOTUBE_CHANNEL_ID="UCAYJEbMSpKfddk-1z6RGwQA"
    var YOTUBE_API_KEY="&key=AIzaSyA20bELTNQfl67jGCp4lWRuMbOvi2kaEW0"
    var URL_GET_YOUTUBE_LIST=
        "https://www.googleapis.com/youtube/v3/search?order=date&part=snippet&channelId=$YOTUBE_CHANNEL_ID&maxResults=10$YOTUBE_API_KEY";
    var INSTA_TOKEN = "5730874820.b5baecd.9a6ef3f5e0034879ae77edcfef21db6b"
    var URL_GET_INSTA_LIST =
        "https://api.instagram.com/v1/users/self/media/recent/?access_token=$INSTA_TOKEN"
    var INSTA_PAGE_NAME_ID = "britishschoolkl/"
    var URL_INSTA_PAGE = "https://www.instagram.com/$INSTA_PAGE_NAME_ID"
    var FB_PAGE_ID = "BritishSchoolKL"
    var FB_FIELDS = "full_picture,caption,description,picture,message,event"
    var FB_TOKEN =
        "EAAGfhpgCwKgBACtp6hgSsDixEi6mY6XuwAgZAWD6JQYF4by74vSu5nZAuXDTwVUmwxkSfSyglFkl9SOwrecMfm5tl37wDQFacsCJZCn3zAkJi7FJNxIZBhUr3nwHpmpo0ZCZAMYZBzPzLmHGrqZCAvwER32SZAREZC50q51wZAQfs3kRXXZB2PSBOkCF"

    var URL_GET_FB_LIST =
        "https://graph.facebook.com/v3.1/$FB_PAGE_ID/feed?access_token=$FB_TOKEN&fields=$FB_FIELDS"
    var FB_PAGE_NAME_ID = "BritishSchoolKL/"
    var URL_FB_PAGE = "https://www.facebook.com/$FB_PAGE_NAME_ID"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.social_media_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Social Media"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        mYoutubeModelArrayList = ArrayList()
        mInstagramModelArrayList = ArrayList()
        fbJsonArray= JSONArray()
        initialiseUI()
        if (AppUtils().isNetworkConnected(mContext)) {
            FetchYouTubeData().execute()
            //getYouTubeList(mYoutubeModelArrayList)

        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }


    }
    private fun initialiseUI() {
        relMain = requireView().findViewById(R.id.social_rel)
        youtubeImg = requireView().findViewById(R.id.youtubeImg)
        instagramImg = requireView().findViewById(R.id.instagramImg)
        fbImg = requireView().findViewById(R.id.fbImg)
        fbTextView = requireView().findViewById(R.id.fbTextView)
        fbTextViewOnly = requireView().findViewById(R.id.fbTextViewOnly)
        InstaTextViewOnly = requireView().findViewById(R.id.InstaTextViewOnly)
        instaTextView = requireView().findViewById(R.id.instaTextView)
        youtubeBtnRelative = requireView().findViewById(R.id.youtubeBtnRelative)
        instagramButtonLinear = requireView().findViewById(R.id.instagramButton)
        facebookButtonLinear = requireView().findViewById(R.id.facebookButton)

        youtubeBtnRelative!!.setOnClickListener {
            if (mYoutubeModelArrayList.size > 0) {

                val mIntent: Intent = Intent(
                    mContext,
                    LoadWebViewActivity::class.java
                )
                mIntent.putExtra(
                    "url",
                    "https://www.youtube.com/channel/" + mYoutubeModelArrayList[0].channelId + "/videos"
                )
                mIntent.putExtra("tab_type", bsklNameConstants.SOCIAL_MEDIA)
                mContext.startActivity(mIntent)
            }
        }
        instagramButtonLinear!!.setOnClickListener{
            val mIntent = Intent(
                mContext,
                LoadWebViewActivity::class.java
            )
            mIntent.putExtra("url", URL_INSTA_PAGE)
            mIntent.putExtra("tab_type", bsklNameConstants.SOCIAL_MEDIA)
            mContext.startActivity(mIntent)
        }
        facebookButtonLinear!!.setOnClickListener {
            val mIntent = Intent(
                mContext,
                LoadWebViewActivity::class.java
            )
            mIntent.putExtra("url", URL_FB_PAGE)
            mIntent.putExtra("tab_type",bsklNameConstants.SOCIAL_MEDIA)
            mContext.startActivity(mIntent)
        }
    }
    inner class FetchYouTubeData : AsyncTask<Void, Void, String>() {
        private val apiUrl =
            URL_GET_YOUTUBE_LIST.replace(
                YOTUBE_CHANNEL_ID,
                PreferenceManager().getYouKey(mContext).toString())

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            getYouTubeList(mYoutubeModelArrayList)
        }
        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: Void?): String? {
            var result: String? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    result = inputStreamToString(connection.inputStream)
                        val youtubeJsonObj: JSONObject = JSONObject(result)
                    nextPageToken = youtubeJsonObj.optString("nextPageToken")
                    val youtubeListArray = youtubeJsonObj.optJSONArray("items")
                    for (i in 0 until youtubeListArray.length()) {
                        val youtubeListItem = youtubeListArray.optJSONObject(i)
                        val snippetYoutube = youtubeListItem.optJSONObject("snippet")
                        val idYoutube = youtubeListItem.optJSONObject("id")
                        val thumbnailsJsonObject = snippetYoutube.optJSONObject("thumbnails")
                        val defaultUrlJsonObject = thumbnailsJsonObject.optJSONObject("default")
                        val mediumUrlJsonObject = thumbnailsJsonObject.optJSONObject("medium")
                        val highUrlJsonObject = thumbnailsJsonObject.optJSONObject("high")
                        val mYoutubeModel = YoutubeModel()
                        mYoutubeModel.videoId=idYoutube.optString("videoId")
                        mYoutubeModel.title=snippetYoutube.optString("title")
                        mYoutubeModel.publishedAt=snippetYoutube.optString("publishedAt")
                        mYoutubeModel.channelId=snippetYoutube.optString("channelId")
                        mYoutubeModel.thumbnailsDefault=defaultUrlJsonObject.optString("url")
                        mYoutubeModel.thumbnailsMedium=mediumUrlJsonObject.optString("url")
                        mYoutubeModel.thumbnailsHigh=highUrlJsonObject.optString("url")
                        mYoutubeModelArrayList.add(mYoutubeModel)
                    }

                    if (mYoutubeModelArrayList.size > 0) {
                        Glide.with(mContext)
                            .load(mYoutubeModelArrayList[0].thumbnailsHigh.toString())
                            .placeholder(R.drawable.student)
                            .error(R.drawable.student)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(youtubeImg!!)
                        /*Glide.with(mContext)
                            .load(AppUtils().replace(mYoutubeModelArrayList[0].thumbnailsHigh.toString()))
                            .centerCrop().into(youtubeImg!!)*/
                    }


                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
            return result
        }
    }
    inner class FetchInstaData : AsyncTask<Void, Void, String>() {
        private val apiUrl =
            URL_GET_INSTA_LIST.replace(
                INSTA_TOKEN,
                PreferenceManager().getInstaKey(mContext).toString()
            )

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            getInstaList(mInstagramModelArrayList)
        }
        override fun doInBackground(vararg params: Void?): String? {
            var result: String? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    result = inputStreamToString(connection.inputStream)
                    val instaJsonObj: JSONObject = JSONObject(result)
                    val instaListArray = instaJsonObj.optJSONArray("data")
                    for (i in 0 until instaListArray.length()) {
                        val mInstagramModel = InstagramModel()
                        val instaListItem = instaListArray.optJSONObject(i)
                        val userJsonObject = instaListItem.optJSONObject("user")
                        if (instaListItem.has("caption")) {
                            val captionJsonObject = instaListItem.optJSONObject("caption")
                            mInstagramModel.text=captionJsonObject.optString("text")

                        } else {
                            mInstagramModel.text=""
                        }
                        val thumbnailsinstaImagesJsonObject = instaListItem.optJSONObject("images")
                        val defaultUrlJsonObject =
                            thumbnailsinstaImagesJsonObject.optJSONObject("thumbnail")
                        val lowresUrlJsonObject =
                            thumbnailsinstaImagesJsonObject.optJSONObject("low_resolution")
                        val highresUrlJsonObject =
                            thumbnailsinstaImagesJsonObject.optJSONObject("standard_resolution")
                        mInstagramModel.username=userJsonObject.optString("username")
                        mInstagramModel.type=instaListItem.optString("type")
                        mInstagramModel.imagesthumbnail=defaultUrlJsonObject.optString("url")
                        mInstagramModel.imageslowres=defaultUrlJsonObject.optString("url")
                        mInstagramModel.imageshighres=defaultUrlJsonObject.optString("url")
                        mInstagramModelArrayList.add(mInstagramModel)
                    }
                    getInstaList(mInstagramModelArrayList)



                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
            return result
        }
    }
    private fun getInstaList( mInstagramModelArrayList: ArrayList<InstagramModel>) {
        if (mInstagramModelArrayList.size > 0) {
            InstaTextViewOnly!!.visibility = View.GONE
            instaTextView.visibility = View.VISIBLE
            instaTextView.setText(mInstagramModelArrayList[0].text)
            Glide.with(mContext)
                .load(mInstagramModelArrayList[0].imageshighres)
                .centerCrop().into(instagramImg!!)
        } else {
            InstaTextViewOnly!!.visibility = View.GONE
            instaTextView.visibility = View.GONE
            instagramImg!!.visibility = View.VISIBLE
            instagramImg!!.setImageResource(R.drawable.app_icon)
        }

        FetchFBData().execute()
       /* getFBList(
            URL_GET_FB_LIST.replace(
                FB_TOKEN,
                PreferenceManager.getFbKey(SocialMediaFragment.mContext)
            )
        )*/

    }
    inner class FetchFBData : AsyncTask<Void, Void, String>() {
        private val apiUrl =
            URL_GET_FB_LIST.replace(
                FB_TOKEN,
                PreferenceManager().getFbKey(mContext).toString()
            )

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            getFBList(fbJsonArray)
        }
        override fun doInBackground(vararg params: Void?): String? {
            var result: String? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    result = inputStreamToString(connection.inputStream)
                    val fbJsonObj: JSONObject = JSONObject(result)
                     fbJsonArray = fbJsonObj.optJSONArray("data")



                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
            return result
        }
    }
    private fun getFBList(fbJsonArray: JSONArray?) {
        if (fbJsonArray!!.length() > 0) {
            val fbJson = fbJsonArray.optJSONObject(0)
            if (fbJson.has("description")) {
                Glide.with(mContext)
                    .load(fbJson.optString("full_picture"))
                    .centerCrop().into(fbImg!!)
                fbTextView!!.text = fbJson.optString("description")
                fbImg!!.visibility = View.VISIBLE
                fbTextView!!.visibility = View.VISIBLE
                fbTextViewOnly!!.visibility = View.GONE
            } else if (fbJson.has("full_picture") && fbJson.has("message")) {
                Glide.with(mContext)
                    .load(fbJson.optString("full_picture"))
                    .centerCrop().into(fbImg!!)
                fbTextView!!.text = fbJson.optString("message")
                fbImg!!.visibility = View.VISIBLE
                fbTextView!!.visibility = View.VISIBLE
                fbTextViewOnly!!.visibility = View.GONE
            } else if (fbJson.has("full_picture")) {
                Glide.with(mContext)
                    .load(fbJson.optString("full_picture"))
                    .centerCrop().into(fbImg!!)
                fbImg!!.visibility = View.VISIBLE
                fbTextViewOnly!!.visibility = View.GONE
                fbTextView!!.visibility = View.GONE
            } else if (fbJson.has("message")) {
                fbTextViewOnly!!.text = fbJson.optString("message")
                fbTextViewOnly!!.visibility = View.VISIBLE
                fbImg!!.visibility = View.GONE
                fbTextView!!.visibility = View.GONE
            }
        } else {
            fbTextViewOnly!!.visibility = View.GONE
            //fbTextViewOnly.setText("No Data Available.");
            fbTextViewOnly!!.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            fbTextViewOnly!!.visibility = View.VISIBLE
            fbImg!!.visibility = View.VISIBLE
            fbImg!!.setImageResource(R.drawable.app_icon)
            fbTextView!!.visibility = View.GONE
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        return sb.toString()
    }
    private fun getYouTubeList( mYoutubeModelArrayList:ArrayList<YoutubeModel>) {
        if (mYoutubeModelArrayList.size > 0) {
            Glide.with(mContext)
                .load(mYoutubeModelArrayList[0].thumbnailsHigh.toString())
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(youtubeImg!!)
            /*Glide.with(mContext)
                .load(AppUtils().replace(mYoutubeModelArrayList[0].thumbnailsHigh.toString()))
                .centerCrop().into(youtubeImg!!)*/
        }
        FetchInstaData().execute()
       /* getInstaList(
            URL_GET_INSTA_LIST.replace(
                INSTA_TOKEN,
                PreferenceManager().getInstaKey(mContext).toString()
            )
        )*/
    }


}
