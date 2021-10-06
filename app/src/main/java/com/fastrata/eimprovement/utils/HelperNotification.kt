package com.fastrata.eimprovement.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

import com.fastrata.eimprovement.R

class HelperNotification {

    fun showErrorDialog(activity: Activity, header :String, content : String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_error)
        (dialog.findViewById<View>(R.id.title_warning) as TextView).setText(header)
        (dialog.findViewById<View>(R.id.content_warning) as TextView).setText(content)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.bt_close) as AppCompatButton).setOnClickListener { v ->
            Toast.makeText(
                activity,
                (v as AppCompatButton).text.toString(),
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun showNotification(activity: Activity, header: String,content: String){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_notification)
        (dialog.findViewById<View>(R.id.txt_1) as TextView).setText(header)
        (dialog.findViewById<View>(R.id.txt_2) as TextView).setText(content)
        dialog.setCancelable(true)
        (dialog.findViewById<View>(R.id.btn_dismiss) as TextView).setOnClickListener { v ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface CallBackNotificationYesNo {
        fun onNotificationYes()
        fun onNotificationNo()
    }


    fun shownotificationyesno(activity: Activity,header: String,content: String,listener : CallBackNotificationYesNo){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_yesno)
        (dialog.findViewById<View>(R.id.title_warning) as TextView).setText(header)
        (dialog.findViewById<View>(R.id.content_warning) as TextView).setText(content)
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onNotificationYes()
        }
        (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onNotificationNo()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

}


