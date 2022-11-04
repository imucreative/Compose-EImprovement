package com.fastrata.eimprovement.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.fastrata.eimprovement.BuildConfig
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.AuthInterceptor
import com.fastrata.eimprovement.utils.FileUtils
import com.fastrata.eimprovement.utils.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.net.ConnectException
import java.util.concurrent.TimeUnit

/**
 * Create a Notification that is shown as a heads-up notification if possible.
 *
 * For this codelab, this is used to show a notification so that you know when different steps
 * of the background work chain are starting
 *
 * @param message Message shown on the notification
 * @param context Context needed to create Toast
 */
fun makeStatusNotification(message: String, context: Context) {
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_logo_eimprovement)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

/**
 * Method for sleeping for a fixed about of time to emulate slower work
 */
fun sleep() {
    try {
        Thread.sleep(DELAY_TIME_MILLIS, 0)
    } catch (e: InterruptedException) {
        Timber.e(e)
    }
}

fun service(): AppService {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
        //.addInterceptor(GzipInterceptor())
        .readTimeout(500, TimeUnit.SECONDS)
        .connectTimeout(1000, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(AppService::class.java)
}

/**
 * Method for sync undelivered-photos to server
 * **/
suspend fun processSyncPhotos(context: Context, file: File) {
    // splitSuffix change path
    //      /file:/storage/emulated/0...
    // to
    //      /storage/emulated/0...
    //val splitSuffix = file.absolutePath.toString().drop(6)

    var fileUri: Uri? = null
    fileUri = file.path.toUri()
    val fl: File = FileUtils.getFile(context, fileUri)

    val requestBodyFile: RequestBody = RequestBody.create("*/*".toMediaType(), fl)
    val body: MultipartBody.Part = MultipartBody.Part.createFormData("file_images", file.name, requestBodyFile)
    val descriptionString = "Capture photo file desc"
    val description = RequestBody.create(MultipartBody.FORM, descriptionString)
    //val surveyorCode = PreferenceUtil(context).getStringPreference(PREF_SURVEYOR_CODE, "") ?: ""

    //FIXME try to get the variable from Dagger
    try {
        /*service().uploadCheckOutPhoto(surveyorCode, description, body).let {
            if (it.isSuccessful) {
                Timber.i("Success post Photos")
                //Update sent flag
                val capturePhotoEntity = SurveyPhotoEntity(file.name, "file://$fl", 1)
                AppDatabase.getInstance(context).surveyPhotoDao()
                    .insertCapturedPhoto(capturePhotoEntity)
            } else {
                Timber.i("Failed post Photos")
            }
        }*/
    } catch (err: ConnectException) {
        Timber.e("### Error : ${err.message}")
    }
}


/**
 * Method for sync undelivered-survey-data to server
 * **/
/*
suspend fun processSyncSurveyData(context: Context, surveyResult: SurveyResultRequest) {
    //FIXME try to get the variable from Dagger
    try {
        service().postSurveyResult(surveyResult).let {
            if (it.isSuccessful) {
                //Update sent flag
                val surveyCode = it.body()!!.data[0].survey_code
                Timber.i("Sucess post Survey Data %s", surveyCode)
                AppDatabase.getInstance(context).surveyHeaderDao().updateSurveySentFlag(surveyCode)
            } else {
                Timber.i("Failed post Survey Data")
            }
        }
    } catch (err: ConnectException) {
        Timber.e("### Error : ${err.message}")
    }
}*/
