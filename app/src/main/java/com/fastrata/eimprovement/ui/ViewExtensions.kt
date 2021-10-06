package com.fastrata.eimprovement.ui

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun Fragment.setToolbar(toolbar: Toolbar, title: String) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).supportActionBar!!.title = title
    (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

fun setToolbar(activity: Activity, toolbar: Toolbar, title: String) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    activity.supportActionBar!!.title = title
    activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}
