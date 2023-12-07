package com.example.bskl_kotlin.activity.pdf

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.home.mContext
import com.example.bskl_kotlin.manager.AppUtils
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class PDF_View_New:AppCompatActivity() {
    lateinit var pdf: PDFView
    lateinit var ShareBtn: ImageView
    lateinit var DownloadBtn:android.widget.ImageView
    lateinit var BackBtn:android.widget.ImageView
    lateinit var Extras: Bundle
    lateinit var Url: String
    lateinit var mContext: Context
    lateinit var mProgressDialog: ProgressDialog
    var name = "BSKL"
    var fileData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf__view__new)

        mContext = this
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        Extras = intent.extras!!
        if (Extras != null) {
            Url = Extras.getString("pdf_url")!!
            fileData = Extras.getString("fileName")!!
        }
        Log.d("PDF_URL", Url)

        InitUi()


    }
private fun InitUi(){
    pdf = findViewById<PDFView>(R.id.Csutom_pdfView)
    ShareBtn = findViewById<ImageView>(R.id.PDFshared)
    DownloadBtn = findViewById<ImageView>(R.id.PDFdownload)
    BackBtn = findViewById<ImageView>(R.id.PDF_GoBack)

    BackBtn.setOnClickListener {
        finish()
    }
    ShareBtn.setOnClickListener {
        if (AppUtils().isNetworkConnected(mContext)) {
            val intentShareFile = Intent(Intent.ACTION_SEND)
            val fileWithinMyDir: File = File(getFilepath("$fileData.pdf"))
            if (fileWithinMyDir.exists()) {
                intentShareFile.type = "application/pdf"
                intentShareFile.putExtra(
                    Intent.EXTRA_STREAM, Uri.parse(
                        "file://" + getFilepath(
                            "$fileData.pdf"
                        )
                    )
                )
                intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...")
                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...")
                startActivity(Intent.createChooser(intentShareFile, "Share File"))
            }
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
    }
    DownloadBtn.setOnClickListener {
        if (AppUtils().isNetworkConnected(mContext)) {
            mProgressDialog = ProgressDialog(this@PDF_View_New)
            mProgressDialog.setMessage("Downloading..")
            mProgressDialog.isIndeterminate = true
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            mProgressDialog.setCancelable(true)
            DownloadPDF().execute()
            mProgressDialog.setOnCancelListener {
                DownloadPDF()
                    .cancel(true) //cancel the task
            }
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
    }
   loadPDF().execute()

}
    inner class loadPDF : AsyncTask<Void, Void, String>() {

        private val exception: Exception? = null
        private val dialog: ProgressDialog? = null
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
          /*  if (dialog!!.isShowing) {
                dialog.dismiss()
            }*/

            val file =
                File(Environment.getExternalStorageDirectory().absolutePath + "/download/" + fileData + ".pdf")
            val uri =
                Uri.parse(Environment.getExternalStorageDirectory().absolutePath + "/download/" + fileData + ".pdf")
            // System.out.println("file.exists() = " + file.exists());
            // pdf.fromUri(uri);

            // System.out.println("file.exists() = " + file.exists());
            // pdf.fromUri(uri);
            pdf.fromFile(file).defaultPage(0).enableSwipe(true).load()

        }
        override fun doInBackground(vararg params: Void?): String? {
            var u: URL? = null
            try {
                val fileName = "$fileData.pdf"
                u = URL(Url)
                val c = u.openConnection() as HttpURLConnection
                c.requestMethod = "GET"
                // c.setDoOutput(true);
                val auth = "SGHCXFTPUser" + ":" + "cXFTPu$3r"

                var encodedAuth = Base64.encodeToString(auth.toByteArray(), Base64.DEFAULT)
                encodedAuth = encodedAuth.replace("\n", "")
                c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                c.addRequestProperty("Authorization", "Basic $encodedAuth")
                //c.setRequestProperty("Accept", "application/json");
                // c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                c.connect()
                val response = c.responseCode
                val PATH = Environment.getExternalStorageDirectory()
                    .toString() + "/download/"
                // Log.d("Abhan", "PATH: " + PATH);
                val file = File(PATH)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val outputFile = File(file, fileName)
                val fos = FileOutputStream(outputFile)
                val `is` = c.inputStream
                val buffer = ByteArray(1024)
                var len1 = 0
                while (`is`.read(buffer).also { len1 = it } != -1) {
                    fos.write(buffer, 0, len1)
                }
                fos.flush()
                fos.close()
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }
   /* class loadPDF : AsyncTask<String?, Void?, Void?>() {
        private val exception: Exception? = null
        private var dialog: ProgressDialog? = null

    }*/
  inner class DownloadPDF :
       AsyncTask<String?, Void?, Void?>() {
       private val exception: java.lang.Exception? = null
       private var dialog: ProgressDialog? = null

       var filename: String = name.replace(" ", "_")
       var fileName = "$filename.pdf"
       override fun onPreExecute() {
           super.onPreExecute()
           dialog = ProgressDialog(mContext)
           dialog!!.setMessage(mContext.getResources().getString(R.string.pleasewait)) //Please wait...
           dialog!!.show()
       }

       override fun doInBackground(vararg urls: String?): Void? {
           var u: URL? = null
           try {
               u = URL(Url)
               val c = u.openConnection() as HttpURLConnection
               c.requestMethod = "GET"
               // c.setDoOutput(true);
               val auth = "SGHCXFTPUser" + ":" + "cXFTPu$3r"
              /* var bytes=File(compressedImage.path).readBytes()
               var encode= Base64.encodeToString(bytes, 2)*/
               var encodedAuth = Base64.encodeToString(auth.toByteArray(), Base64.DEFAULT)
               encodedAuth = encodedAuth.replace("\n", "")
               c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
               c.addRequestProperty("Authorization", "Basic $encodedAuth")
               //c.setRequestProperty("Accept", "application/json");
               // c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
               c.connect()
               val response = c.responseCode
               val PATH = Environment.getExternalStorageDirectory()
                   .toString() + "/download/"
               // Log.d("Abhan", "PATH: " + PATH);
               val file = File(PATH)
               if (!file.exists()) {
                   file.mkdirs()
               }
               val outputFile = File(file, fileName)
               val fos = FileOutputStream(outputFile)
               val `is` = c.inputStream
               val buffer = ByteArray(1024)
               var len1 = 0
               while (`is`.read(buffer).also { len1 = it } != -1) {
                   fos.write(buffer, 0, len1)
               }
               fos.flush()
               fos.close()
               `is`.close()
           } catch (e: IOException) {
               e.printStackTrace()
           }
           return null
       }

       override fun onPostExecute(aVoid: Void?) {
           super.onPostExecute(aVoid)
           if (dialog!!.isShowing) {
               dialog!!.dismiss()
           }
           val file =
               File(Environment.getExternalStorageDirectory().absolutePath + "/download/" + fileName)
           val uri =
               Uri.parse(Environment.getExternalStorageDirectory().absolutePath + "/download/" + fileName)
           println("file.exists() = " + file.exists())
           if (file.exists()) {
               Toast.makeText(mContext, "File Downloaded to Downloads folder", Toast.LENGTH_SHORT)
                   .show()
           } else {
               Toast.makeText(mContext, "Something Went Wrong. Download failed", Toast.LENGTH_SHORT)
                   .show()
           }
           // pdf.fromUri(uri);

           // pdf.fromFile(file).defaultPage(1).enableSwipe(true).load();

           //web.loadUrl(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
           // Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_LONG).show();
       }
   }

    private fun getFilepath(filename: String): String? {
        return File(
            Environment.getExternalStorageDirectory().absolutePath,
            "/Download/$filename"
        ).path
    }

}
