package com.fastrata.eimprovement.features.utils

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fastrata.eimprovement.R
import com.google.android.material.snackbar.Snackbar

class SnackBarCustom {
    companion object {
        fun snackBarIconInfo(
            parentView: View,
            layoutInflater: LayoutInflater,
            ctx: Resources,
            message: String,
            icon: Int,
            color: Int
        ) {
            val snackBar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT)
            //inflate view
            val customView: View = layoutInflater.inflate(R.layout.snackbar_icon_text, null)
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackBarView = snackBar.view as Snackbar.SnackbarLayout
            snackBarView.setPadding(0, 0, 0, 0)
            (customView.findViewById<View>(R.id.message) as TextView).text = message
            (customView.findViewById<View>(R.id.icon) as ImageView).setImageResource(icon)
            customView.findViewById<View>(R.id.parent_view)
                .setBackgroundColor(ctx.getColor(color))
            snackBarView.addView(customView, 0)
            snackBar.show()
        }
    }
}
