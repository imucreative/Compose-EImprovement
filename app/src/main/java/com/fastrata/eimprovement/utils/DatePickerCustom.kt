package com.fastrata.eimprovement.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.fastrata.eimprovement.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

// https://thesimplycoder.com/226/android-date-picker-kotlin-tutorial/
class DatePickerCustom(
    context: Context,
    themeDark: Boolean = false,
    minDateIsCurrentDate: Boolean,
    fragmentManager: FragmentManager
) {
    private var datePicker: DatePickerDialog
    private var callback: Callback? = null
    private var fragmentMgr: FragmentManager = fragmentManager

    private val listener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
        callback?.onDateSelected(dayOfMonth, monthOfYear, year)
    }

    init {
        val cal = Calendar.getInstance()
        datePicker = DatePickerDialog.newInstance(
            listener, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]
        )
        datePicker.accentColor = ContextCompat.getColor(context, R.color.colorPrimary)
        datePicker.isThemeDark = themeDark
        if (minDateIsCurrentDate) datePicker.minDate = cal
    }

    fun showDialog(callback: Callback?) {
        this.callback = callback
        datePicker.show(fragmentMgr, "DatePickerDialog")
    }

    interface Callback {
        fun onDateSelected(dayOfMonth: Int, month: Int, year: Int)
    }
}
