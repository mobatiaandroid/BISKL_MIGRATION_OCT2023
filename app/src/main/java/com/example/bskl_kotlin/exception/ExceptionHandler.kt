package com.example.bskl_kotlin.exception

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Process
import com.example.bskl_kotlin.activity.splash.SplashActivity
import java.io.PrintWriter
import java.io.StringWriter


class ExceptionHandler : Application, Thread.UncaughtExceptionHandler {
    /** The mIntent  */
    private var mIntent: Intent? = null

    constructor() {
        context = null
    }

    constructor(mContext: Context?) {
        context = mContext
    }

    constructor(mContext: Context?, mClass: Class<*>?) {
        context = mContext
    }

    override fun onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory()
    }

    /**
     * (non-Javadoc)
     *
     * @see Application.onCreate
     */
    override fun onCreate() {
        context = this
        super.onCreate()
    }

    /**
     * (non-Javadoc)
     *
     * @see Thread.UncaughtExceptionHandler.uncaughtException
     */
    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val mStringWriter = StringWriter()
        exception.printStackTrace(PrintWriter(mStringWriter))
        // System.out.println(mStringWriter);
        mIntent = Intent(context, SplashActivity::class.java)
        val s = mStringWriter.toString()
        // System.out.println("wizz exception " + s);
        // you can use this String to know what caused the exception and in
        // which Activity
        mIntent!!.putExtra(
            "uncaught Exception",
            "Exception is: $mStringWriter"
        )
        mIntent!!.putExtra("uncaught stacktrace", s)
        mIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context!!.startActivity(mIntent)
        // System.out.println("error " + s);
        // for restarting the Activity
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    companion object {
        /** The mContext  */
        var context: Context? = null
            private set
    }
}
