package com.fastrata.eimprovement.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

import com.fastrata.eimprovement.R

class HelperNotification {

    interface CallBackNotificationYesNo {
        fun onNotificationYes()
        fun onNotificationNo()
    }

    interface CallbackRetry{
        fun onRetry()
    }

    interface CallbackDismis{
        fun onDismiss()
    }

    interface CallbackList {
        fun onView()
        fun onEdit()
        fun onImplementation()
        fun onDelete()
    }

    fun showErrorDialog(activity: Activity, header :String, content : String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_error)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.txt_1) as TextView).setText(header)
        (dialog.findViewById<View>(R.id.txt_2) as TextView).setText(content)
        dialog.setCancelable(true)
        (dialog.findViewById<View>(R.id.btn_dismiss) as TextView).setOnClickListener { v ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showNotificationDismisAction(activity: Activity, header: String,content: String,listener : CallbackDismis){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_notification)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.txt_1) as TextView).setText(header)
        (dialog.findViewById<View>(R.id.txt_2) as TextView).setText(content)
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.btn_dismiss) as TextView).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onDismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }


    fun shownotificationyesno(activity: Activity,header: String,content: String,listener : CallBackNotificationYesNo){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_yesno)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    fun showListEdit(activity: Activity,header: String,view: Boolean,viewEdit : Boolean,viewImplementation : Boolean,viewDelete : Boolean,listener: CallbackList){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.txt_title) as TextView).text = header
        if (!view){
            (dialog.findViewById<View>(R.id.btn_view) as LinearLayout).visibility = View.GONE
        }
        if (!viewEdit){
            (dialog.findViewById<View>(R.id.btn_edit) as LinearLayout).visibility = View.GONE
        }
        if (!viewImplementation){
            (dialog.findViewById<View>(R.id.btn_implementation) as LinearLayout).visibility = View.GONE
        }
        if (!viewDelete){
            (dialog.findViewById<View>(R.id.btn_delete) as LinearLayout).visibility = View.GONE
        }
        dialog.setCancelable(true)
        (dialog.findViewById<View>(R.id.btn_view) as LinearLayout).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onView()
        }
        (dialog.findViewById<View>(R.id.btn_edit) as LinearLayout).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onEdit()
        }
        (dialog.findViewById<View>(R.id.btn_implementation) as LinearLayout).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onImplementation()
        }

        (dialog.findViewById<View>(R.id.btn_delete) as LinearLayout).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onDelete()
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun displayNoInternet(activity: Activity,listener: CallbackRetry){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.nointernet_screen)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.bt_retry)as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onRetry()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

}


