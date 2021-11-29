package com.fastrata.eimprovement.utils

import android.widget.ImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fastrata.eimprovement.wrapper.Event
import com.fastrata.eimprovement.wrapper.VoidEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pattern: yyyy-MM-dd HH:mm:ss
 */
fun Date.formatToServerDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyyMMddHHmmss
 */
fun Date.formatToTruncatedDateTime(): String {
    val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyyMMdd
 */
fun Date.formatToTruncatedDate(): String {
    val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToServerTimeDefaults(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Date.formatToViewDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToViewTimeDefaults(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}

/**
 * Compare integer value
 * **/

fun Int?.isGreaterThan(other: Int?) =
    this != null && other != null && this > other

fun Int?.isLessThan(other: Int?) =
    this != null && other != null && this < other


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: androidx.lifecycle.Observer<T>) {
    observe(lifecycleOwner, Observer { t ->
        observer.onChanged(t)
        removeObservers(lifecycleOwner)
    })
}

fun <T> LiveData<out Event<T>>.observeEvent(owner: LifecycleOwner, onEventUnhandled: (T) -> Unit) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandled) })
}

fun LiveData<out VoidEvent>.observeEvent(owner: LifecycleOwner, onEventUnhandled: () -> Unit) {
    observe(owner, Observer { if (!it.hasBeenHandled()) onEventUnhandled() })
}


fun ImageButton.enable() {
    this.isEnabled = true
    this.imageAlpha = 0xFF
}

fun ImageButton.disable() {
    this.isEnabled = false
    this.imageAlpha = 0x3F
}

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute()
    val result = withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
        doInBackground()
    }
    onPostExecute(result)
}