package com.fastrata.eimprovement.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivitySuggestionSystemBinding
import com.fastrata.eimprovement.databinding.SnackbarIconTextBinding
import com.google.android.material.snackbar.Snackbar

class SnackBarCustom {
    companion object {
        fun snackBarIconInfo(
            parentView: View,
            layoutInflater: LayoutInflater,
            ctx: Resources,
            context: Context,
            message: String,
            icon: Int,
            color: Int
        ) {
            val snackBar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT)
            val snackBarView = snackBar.view as Snackbar.SnackbarLayout
            //inflate view
            val binding = SnackbarIconTextBinding.inflate(layoutInflater)
            val customView = binding.root

            snackBar.view.setBackgroundColor(Color.TRANSPARENT)

            binding.parentView.setBackgroundColor(ContextCompat.getColor(context, color))
            binding.message.text = message
            binding.icon.setImageResource(icon)
            binding.icon.setOnClickListener {
                snackBar.dismiss()
            }

            snackBarView.setPadding(0, 0, 0, 0)
            snackBarView.addView(customView, 0)
            snackBar.show()
        }
    }
}
