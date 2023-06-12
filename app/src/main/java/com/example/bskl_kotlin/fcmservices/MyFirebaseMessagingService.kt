package com.example.bskl_kotlin.fcmservices

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.manager.NotificationID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mobatia.bskl.R
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var intent: Intent? = null
    private val TAG = "MyFirebaseMsgService"
    var badgecount:Int=0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val type = ""
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)
        // Check if message contains a data payload.
        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            //            String questionTitle = data.get("questionTitle").toString();
            try {
                val json = JSONObject(remoteMessage.data.toString())
                handleDataMessage(json)
            } catch (e: Exception) {
                //log.e(TAG, "Exception: " + e.getMessage());
            }
        } else {
            Log.d(
                TAG,
                "Message data payload else: " + remoteMessage.data
            )
        }

        // Check if message contains a notification payload.

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage.notification!!.body, type)
            Log.d(
                TAG,
                "Message Notification Body: " + remoteMessage.notification!!
                    .body
            )
        }
    }

    private fun handleDataMessage(json: JSONObject) {

        Log.e(TAG, "push json: $json")

        try {
            val data = json.getJSONObject("notification")
            val badge = data.getString("badge")
            val type = data.getString("type")
            badgecount = Integer.valueOf(badge)
            val message = data.getString("message")
            //            //log.e(TAG, "title: " + title);
            //log.e(TAG, "message: " + message);
            sendNotification(message, type)
        } catch (e: java.lang.Exception) {
            //log.e(TAG, "Json Exception: " + e.getMessage());
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    private fun sendNotification(messageBody: String?,type:String) {
        // ShortcutBadger.applyCount(this, badgecount)

//        ShortcutBadger.with(this).count(badgeCount); //for 1.1.3

        //        ShortcutBadger.with(this).count(badgeCount); //for 1.1.3
        intent = Intent(this, HomeActivity::class.java)
        intent!!.action = java.lang.Long.toString(System.currentTimeMillis())
        val mIntent = Intent("badgenotify")
        LocalBroadcastManager.getInstance(this).sendBroadcast(mIntent)
        if (type.equals("Text")) {
            Log.e("It enters ", "Here")
            intent!!.putExtra("Notification_Recieved", 1)
        } else if (type.equals("Calender")) {
            intent!!.putExtra("Notification_Recieved", 2)
        } else if (type.equals("safe_guarding")) {
            intent!!.putExtra("Notification_Recieved", 3)
        } else {
            intent!!.putExtra("Notification_Recieved", 1)
        }
        val notId: Int = NotificationID.getID()
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(intent!!)
// Get the PendingIntent containing the entire back stack
        // Get the PendingIntent containing the entire back stack
        val pendingIntent = stackBuilder.getPendingIntent(notId, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_ID = getString(R.string.app_name) + "_01" // The id of the channel.
            val name: CharSequence =
                getString(R.string.app_name) // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationBuilder.setChannelId(mChannel.id)
            mChannel.setShowBadge(true)
            mChannel.canShowBadge()
            mChannel.enableLights(true)
            mChannel.lightColor = resources.getColor(R.color.split_bg)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            assert(notificationManager != null)
            notificationManager!!.createNotificationChannel(mChannel)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notify_small)
            notificationBuilder.color = resources.getColor(R.color.split_bg)
        } else {
            notificationBuilder.setSmallIcon(R.drawable.nas_large)
        }


        notificationManager!!.notify(notId, notificationBuilder.build())

    }
}