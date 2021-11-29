package com.fastrata.eimprovement.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.fastrata.eimprovement.R
import com.google.android.material.textfield.TextInputEditText

class HelperNotification {

    interface CallBackNotificationYesNo {
        fun onNotificationYes()
        fun onNotificationNo()
    }

    interface CallBackNotificationYesNoWithComment {
        fun onNotificationYes(comment: String = "")
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
        fun onSubmit()
        fun onCheck()
        fun onImplementation()
        fun onSubmitLaporan()
        fun onReview()
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


    fun shownotificationyesno(
        activity: Activity,
        context : Context,
        headerColor : Int,
        header: String,
        content: String,
        yesText : String,
        noText : String,
        listener : CallBackNotificationYesNo
    ){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_yesno)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.linear_header)as LinearLayout).setBackgroundColor(ContextCompat.getColor(context, headerColor))
        (dialog.findViewById<View>(R.id.title_warning) as TextView).text = header
        (dialog.findViewById<View>(R.id.content_warning) as TextView).text = content
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        if (yesText.isNullOrEmpty()){
            (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).text = "OK"
        }else{
            (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).text = yesText
        }
        (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onNotificationYes()
        }
        if (noText.isNullOrEmpty()){
            (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).text = "No"
        }else{
            (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).text = noText
        }
        (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onNotificationNo()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun showNotificationYesNoWithComment(
        activity: Activity,
        context : Context,
        headerColor : Int,
        header: String,
        content: String,
        yesText : String,
        noText : String,
        listener : CallBackNotificationYesNoWithComment
    ){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_yesno)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.linear_header)as LinearLayout).setBackgroundColor(ContextCompat.getColor(context, headerColor))
        (dialog.findViewById<View>(R.id.title_warning) as TextView).text = header
        (dialog.findViewById<View>(R.id.content_warning) as TextView).text = content
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        (dialog.findViewById<View>(R.id.relative_layout_comment) as RelativeLayout).visibility = View.VISIBLE

        (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).text = yesText
        (dialog.findViewById<View>(R.id.bt_ok) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null) {
                val comment = (dialog.findViewById<View>(R.id.comment) as TextInputEditText).text
                listener.onNotificationYes(comment.toString())
            }
        }
        (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).text = noText
        (dialog.findViewById<View>(R.id.bt_no) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onNotificationNo()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun showListEdit(activity: Activity,header: String,
                     view: Boolean,
                     viewEdit : Boolean,
                     viewImplementation : Boolean,
                     viewDelete : Boolean,
                     viewSubmit : Boolean,
                     viewCheck : Boolean,
                     viewSubmitLaporan : Boolean,
                     viewReview:Boolean,
                     listener: CallbackList){
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
        if (!viewSubmit){
                (dialog.findViewById<View>(R.id.btn_submit) as LinearLayout).visibility = View.GONE
        }
        if (!viewCheck){
                (dialog.findViewById<View>(R.id.btn_check) as LinearLayout).visibility = View.GONE
        }
        if(!viewImplementation){
                (dialog.findViewById<View>(R.id.btn_implementation) as LinearLayout).visibility = View.GONE
        }
        if(!viewSubmitLaporan){
                (dialog.findViewById<View>(R.id.btn_submit_laporan) as LinearLayout).visibility = View.GONE
        }
        if (!viewReview){
                (dialog.findViewById<View>(R.id.btn_review) as LinearLayout).visibility = View.GONE
        }
        if (!viewDelete){
                (dialog.findViewById<View>(R.id.btn_delete) as LinearLayout).visibility = View.GONE
        }
        (dialog.findViewById<View>(R.id.btn_view) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onView()
        }
        (dialog.findViewById<View>(R.id.btn_edit) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onEdit()
        }
        (dialog.findViewById<View>(R.id.btn_submit) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if(listener != null)listener.onSubmit()
        }
        (dialog.findViewById<View>(R.id.btn_check) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onCheck()
        }
        (dialog.findViewById<View>(R.id.btn_implementation) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onImplementation()
        }
        (dialog.findViewById<View>(R.id.btn_submit_laporan) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onSubmitLaporan()
        }
        (dialog.findViewById<View>(R.id.btn_review) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onReview()
        }
        (dialog.findViewById<View>(R.id.btn_delete) as LinearLayout).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onDelete()
        }

        dialog.setCancelable(true)
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
        (dialog.findViewById<View>(R.id.bt_retry)as AppCompatButton).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onRetry()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

}


