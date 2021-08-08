package com.fastrata.eimprovement.utils

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.fastrata.eimprovement.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class DatePickerCustom {
    companion object {
        fun dialogDatePicker(context: Context, fragmentManager: FragmentManager, themeDark: Boolean, minDateIsCurrentDate: Boolean) {
            val curCalender = Calendar.getInstance()
            val datePicker: DatePickerDialog = DatePickerDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val dateShipMillis = calendar.timeInMillis

                Toast.makeText(context, Tools.getFormattedDateSimple(dateShipMillis), Toast.LENGTH_LONG).show()
            },
                curCalender[Calendar.YEAR],
                curCalender[Calendar.MONTH],
                curCalender[Calendar.DAY_OF_MONTH]
            )
            //set dark light
            datePicker.isThemeDark = themeDark
            datePicker.accentColor = ContextCompat.getColor(context, R.color.colorPrimary)
            if (minDateIsCurrentDate) datePicker.minDate = curCalender
            datePicker.show(fragmentManager, "DatePickerDialog")
        }
    }
}

