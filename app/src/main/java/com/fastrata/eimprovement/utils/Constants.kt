package com.fastrata.eimprovement.utils

/**
 * Constants used throughout the app.
 */
const val DATA_FILENAME = "survey.json"
const val DATA_SURVEY_HEADER_FILENAME = "survey_header.json"
const val DATABASE_NAME = "e-improvement-db.db"
const val DEFAULT_REQUEST = "json"

/**
* Hawk Key
* */

//LOGIN
const val USER_NAME = "user_name"
const val USER_ID = "user_id"
const val TOKEN = "token"
const val API_KEY = "api_key"
const val DEVICE_UID = "device_uid"
const val DEVICE_NAME = "device_name"
const val SUCCES_LOGIN= "succes"
const val point= "point"


// SUGGESTION SYSTEM (SS) --------------------------------------------------------------------------
const val SS_CREATE = "suggestion_system"

const val PREF_WELCOME = "isDisplay"

/**
 * working timing survey
 * **/
const val PREF_TIME_START = "time_start"
const val PREF_TIME_STOP = "time_stop"
const val PREF_TIME_DURATION = "time_duration"

/**
 * working location lat long
 * **/
const val PREF_LATITUDE_CHECKIN = "latitude_checkin"
const val PREF_LONGITUDE_CHECKIN = "longitude_checkin"
const val PREF_LATITUDE_SURVEY = "latitude_survey"
const val PREF_LONGITUDE_SURVEY = "longitude_survey"
const val PREF_LATITUDE_CHECKOUT = "latitude_checkout"
const val PREF_LONGITUDE_CHECKOUT = "longitude_checkout"
/**
 * Survey option variable
 * **/
const val SURVEY_OPTION_SCAN_BARCODE = "scan_option"
const val SURVEY_OPTION_INPUT_CUST_NO = "manual_option"
const val SURVEY_OPTION_NOT_FB_OUTLET = "not_my_outlet"

/**
 * Key
 * **/
const val GOOGLE_SERVER_KEY = "AIzaSyCYVbkaOZNUs7nddqrJoN_25mYU34m2O7M"
const val VERSION_CODE_KEY = "app_latest_version_code"
const val APP_DOWNLOAD_URL = "app_download_url"

/**
 * Absent Type
 * **/
const val ABSENT_OUT = "OUT"
const val ABSENT_IN = "IN"

/**
 * Worker Variables
 * **/

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "Syncing Process"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the image manipulation work
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Other keys
const val OUTPUT_PATH = "blur_filter_outputs"
const val TAG_OUTPUT = "OUTPUT"
const val DELAY_TIME_MILLIS: Long = 3000
const val KEY_UNSENT_DATA_PERIODIC = "KEY_UNSENT_DATA_PERIODIC"
const val KEY_UNSENT_DATA_ONE_TIME = "KEY_UNSENT_DATA_ONE_TIME"

//GpsUtils
const val GPS_REQUEST: Int = 1001
