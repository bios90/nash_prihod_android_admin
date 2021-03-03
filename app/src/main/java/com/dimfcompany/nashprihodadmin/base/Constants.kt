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
    }

    object Extras
    {
        const val REGISTER_MADE = "register_made"
        const val EMAIL = "email"
        const val MY_PUSH = "my_push"
        const val MEDIA_OBJECTS = "media_objects"
        const val MEDIA_OBJECTS_START_POS = "media_objects_start_pos"
        const val NEWS_TO_EDIT = "news_to_edit"
    }
}