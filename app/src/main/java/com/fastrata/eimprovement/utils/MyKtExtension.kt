package com.fastrata.eimprovement.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fastrata.eimprovement.featuresglobal.data.model.DownloadResult
import com.fastrata.eimprovement.wrapper.Event
import com.fastrata.eimprovement.wrapper.VoidEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.contentLength
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.OutputStream
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import kotlin.math.roundToInt
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

/*
* https://stackoverflow.com/a/43510648/14795594
**/
interface MyTextWatcher: TextWatcher {
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}

fun EditText.setMaskingMoney(currencyText: String) {
    this.addTextChangedListener(object: MyTextWatcher{
        val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(this@setMaskingMoney)
        override fun afterTextChanged(editable: Editable?) {
            val editText = editTextWeakReference.get() ?: return
            val s = editable.toString()
            editText.removeTextChangedListener(this)
            val cleanString = s.replace("[Rp,. ]".toRegex(), "")
            val newVal = currencyText + cleanString.monetize()

            editText.setText(newVal)
            editText.setSelection(newVal.length)
            editText.addTextChangedListener(this)
        }
    })
}

fun String.monetize(): String = if (this.isEmpty()) "0"
else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(),"").toLong())

/**
 * https://blog.kotlin-academy.com/show-download-progress-in-a-recyclerview-with-ktor-client-and-flow-asynchronous-data-stream-9debab3d2cb6
 * https://spin.atomicobject.com/2020/05/18/android-download-files-kotlin/
 */
suspend fun HttpClient.downloadFile(file: OutputStream, url: String): Flow<DownloadResult> {
    return flow {
        try {
            val response = call {
                url(url)
                method = HttpMethod.Get
            }.response

            val data = ByteArray(response.contentLength()!!.toInt())
            var offset = 0

            do {
                val currentRead = response.content.readAvailable(data, offset, data.size)
                offset += currentRead
                val progress = (offset * 100f / data.size).roundToInt()
                emit(DownloadResult.Progress(progress))
            } while (currentRead > 0)

            response.close()

            if (response.status.isSuccess()) {
                withContext(Dispatchers.IO) {
                    file.write(data)
                }
                emit(DownloadResult.Success)
            } else {
                emit(DownloadResult.Error("File not downloaded"))
            }
        } catch (e: TimeoutCancellationException) {
            emit(DownloadResult.Error("Connection timed out", e))
        } catch (t: Throwable) {
            emit(DownloadResult.Error("Failed to connect"))
        }
    }
}