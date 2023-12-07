package com.example.bskl_kotlin.fcmservices

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

public class MyFirebaseInstanceIDService  {
    //var mContext: Context
    private val REG_TOKEN: String? = "MyFirebaseIIDService"

    // Context mContext=this;
    var tokenM = " "


    /* fun onTokenRefresh() {


         // Get updated InstanceID token.
         FirebaseMessaging.getInstance().token.addOnCompleteListener(object :
             OnCompleteListener<String?> {
             fun onComplete(task: Task<String>) {
                 if (task.isComplete) {
                     val token = task.result
                     tokenM = token
                 }
             }
         })
         val refreshedToken = tokenM
//        AppController().DEVICE_ID = refreshedToken;
         //        AppController().DEVICE_ID = refreshedToken;
         Log.d(REG_TOKEN, "Refreshed token: $refreshedToken")
         // System.out.println("refreshedToken:" + refreshedToken);
         // If you want to send messages to this application instance or
         // manage this apps subscriptions on the server side, send the
         // Instance ID token to your app server.
         // System.out.println("refreshedToken:" + refreshedToken);
         // If you want to send messages to this application instance or
         // manage this apps subscriptions on the server side, send the
         // Instance ID token to your app server.
         sendRegistrationToServer(refreshedToken)
     }*/

    private fun sendRegistrationToServer(refreshedToken: String) {
        /* if (refreshedToken != null) {
             sharedprefs.setFcmID(mContext, refreshedToken)
         }*/

    }
}