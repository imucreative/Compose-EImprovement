package com.fastrata.eimprovement.utils

import android.Manifest

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
const val HAWK_USER = "user"
const val HAWK_DEVICE_UID = "device_uid"
const val HAWK_DEVICE_NAME = "device_name"
const val HAWK_SUCCESS_LOGIN = "succes"
const val HAWK_BALANCE = "balance"
const val HAWK_DOC_ID ="doc_id"
const val HAWK_STATUS_USER_ACTIVE ="status_user_active"

// MENU --------------------------------------------------------------------------
const val MENU_DASHBOARD = "DASHBOARD"
const val MENU_LIST_APPROVAL = "DAFTAR PERSETUJUAN"
const val MENU_SS = "SISTEM SARAN (SS)"
const val MENU_PI = "PROJECT IMPROVEMENT (PI)"
const val MENU_CP = "PENUKARAN POIN"

// SUGGESTION SYSTEM (SS) --------------------------------------------------------------------------
const val SS = "SS"
const val SS_CREATE = "suggestion_system"
const val SS_DETAIL_DATA = "detail_data_ss"

const val PREF_WELCOME = "isDisplay"
const val SS_STATUS_IMPLEMENTATION = "status_suggestion"


// Project Improvement (PI) --------------------------------------------------------------------------
const val PI = "PI"
const val PI_CREATE = "project_improvement"
const val PI_DETAIL_DATA = "detail_data_pi"
const val PI_STATUS_IMPLEMENTATION = "status_implement"

// Change point/ Reward Point (CP/ RP) --------------------------------------------------------------------------
const val CP = "RP"
const val CP_CREATE = "change_point"
const val CP_DETAIL_DATA = "detail_data_cp"

// Approval ------------------------------------------------------------------------------------------
const val APPR = "APPR"

// action navigation
const val ADD = "add"
const val EDIT = "edit"
const val DETAIL = "detail"
const val SUBMIT_PROPOSAL = "submit_proposal"
const val APPROVE = "approve"
const val ACTION_DETAIL_DATA = "action_detail_data"

// provide attachment
val FILE_NAME_EXT = arrayOf("JPEG","JPG","PNG","PDF","DOC","DOCX","XLS","XLSX")
val PERMISSIONS = listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
const val FILE_PICKER_REQUEST_CODE = 101
const val DOWNLOAD_FILE_CODE = 102
const val PROPOSAL = "Proposal"
const val IMPLEMENT = "Implementasi"
const val REQUEST_EXTERNAL_STORAGE = 1
val PERMISSIONS_STORAGE = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

/**
 * Worker Variables
 * **/

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "Syncing Process"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

const val DELAY_TIME_MILLIS: Long = 3000