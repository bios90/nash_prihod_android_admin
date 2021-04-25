package com.dimfcompany.nashprihodadmin.base

object Constants
{
    const val SHARED_PREFS = "SharedPrefs"
    const val EXTENSION_PNG = "png"
    const val EXTENSION_JPEG = "jpeg"

    const val FOLDER_APP_ROOT = "nash_prihod"
    const val FOLDER_TEMP_FILES = FOLDER_APP_ROOT + "/temp_files"
    const val FOLDER_PDFS = FOLDER_APP_ROOT + "/pdfs"

    object Urls
    {
        const val test_mode = true
        val URL_BASE: String
            get()
            {
                if (test_mode)
                {
//                    return "http://nashprihod.test"
//                    return "http://10.0.2.2:8080/nash_prihod/"
//                    return "http://192.168.1.68/akcsl/"
                    return "http://192.168.1.68/"
//                    return "http://192.168.1.68/nash_prihod/"
                }
                else
                {
                    return "https://nashprihodapp.ru/"
                }
            }

        const val REGISTER = "register"
        const val LOGIN = "login"
        const val FORGOT_PASS = "forgotpass"
        const val UPLOAD_IMAGE = "upload_image"
        const val UPLOAD_VIDEO = "upload_video"
        const val INSERT_OR_UPDATE_NEWS = "upsert_news"
        const val GET_NEWS = "get_news"
        const val GET_NEWS_BY_ID = "get_news_by_id"
        const val INSERT_OR_UPDATE_NOTICE = "upsert_notice"
        const val GET_NOTICES = "get_notices"
        const val GET_NOTICE_BY_ID = "get_notice_by_id"
        const val DELETE_NOTICE = "delete_notice"
        const val GET_USERS = "get_users"
        const val GET_USER_BY_ID = "get_user_by_id"
        const val INSERT_SERVICE_TIME = "insert_service_time"
        const val INSERT_SERVICE_TEXT = "insert_service_text"
        const val INSERT_SERVICE = "insert_service"
        const val GET_SERVICES = "get_services"
        const val GET_SERVICE_BY_ID = "get_service_by_id"
        const val DELETE_SERVICE = "delete_service"
        const val INSERT_NOTE = "insert_note"
        const val GET_NOTES = "get_notes"
        const val GET_NOTE_BY_ID = "get_note_by_id"
        const val CHANGE_NOTE_STATUS = "change_note_status"
        const val EDIT_USER = "edit_user"
        const val SET_USER_STATUS = "set_user_status"
    }

    object Extras
    {
        const val REGISTER_MADE = "register_made"
        const val EMAIL = "email"
        const val MY_PUSH = "my_push"
        const val MEDIA_OBJECTS = "media_objects"
        const val MEDIA_OBJECTS_START_POS = "media_objects_start_pos"
        const val MEDIA_VIDEO_TIME = "media_video_time"
        const val NEWS_ID = "news_to_edit"
        const val NOTICE_TO_EDIT = "notice_to_edit"
        const val SERVICE_TO_EDIT = "notice_to_edit"
        const val MODEL_SERVICE_TIME = "model_service_time"
        const val MODEL_SERVICE_TEXT = "model_service_text"
        const val FILTER_DATA_USER = "filter_data_user"
        const val FILTER_DATA_NOTES = "filter_data_notes"
        const val DONATION_SUM = "donation_sum"
        const val DONATION_ID = "donation_id"
        const val NOTE_ID = "note_id"
        const val USER_ID = "user_id"
    }

    object TransitionNames
    {
        const val IMAGE_TO_CAROUSEL_TRANSITION = "image_to_carousel_transition"
    }
}