package com.fastrata.eimprovement.utils

/**
 * Constants used throughout the app.
 */
const val DATA_FILENAME = "survey.json"
const val DATA_SURVEY_HEADER_FILENAME = "survey_header.json"
const val DATABASE_NAME = "e-improvement-db.db"
const val DEFAULT_REQUEST = "json"

/**
* Shared Preferences Variable
* */
// SURVEY OI ---------------------------------------------------------------------------------------
const val PREF_SURVEYOR_CODE = "surveyor_code"
const val PREF_SURVEYOR_NAME = "surveyor_name"
const val PREF_SURVEYOR_ID = "surveyor_id"
const val PREF_SURVEYOR_ORG_ID = "surveyor_org_id"
const val PREF_SURVEYOR_WAREHOUSE_ID = "surveyor_warehouse_id"
const val PREF_CHECKOUT_DATE = "checkout_date"
const val PREF_SURVEY_UM = "survey_um"
const val PREF_SURVEY_OM = "survey_om"
const val PREF_SURVEY_AV = "survey_av"

// SURVEY EMR ---------------------------------------------------------------------------------------
const val PREF_USER_ID = "user_id"
const val PREF_USER_NAME = "user_name"
const val PREF_PASSWORD = "password"
const val PREF_PASSWORD_LOGIN = "password_login"
const val PREF_EMPLOYEE_NIK = "employee_nik"
const val PREF_EMPLOYEE_NAME = "employee_name"
const val PREF_TOKEN_EMR = "token_emr"
const val PREF_API_KEY_EMR = "api_key_emr"
const val PREF_INITIAL_LOGIN = "initial_login"
const val PREF_SURVEYOR_PHOTO = "surveyor_photo"
const val PREF_LAST_ITEM_SURVEY = "last_item_survey"
const val PREF_DIVISION_ID = "division_id"
const val PREF_DIVISION = "division"
const val PREF_IS_OPERATOR = "is_operator"
const val PREF_PING_PERIOD = "ping"

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
