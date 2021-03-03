package com.dimfcompany.nashprihodadmin.base

import android.util.Log
import android.view.View
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.animateFadeIn
import com.dimfcompany.nashprihodadmin.base.extensions.animateFadeOut
import com.dimfcompany.nashprihodadmin.logic.utils.getHourMinuteDiff
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

interface TabView
{
    fun getView(): View
}

interface ObjectWithId
{
    var id: Long?
}

fun ArrayList<out ObjectWithId>.getPosOfObject(id: Long): Int?
{
    this.forEachIndexed(
        { index, obj ->
            if (obj.id == id)
            {
                return@getPosOfObject index
            }
        })

    return null
}


interface ObjectWithDates
{
    var created: Date?
    var updated: Date?
    var deleted: Date?

    fun isNew(): Boolean
    {
        if (updated == null)
        {
            return false
        }

        val diff_hours = getHourMinuteDiff(updated!!, Date()).first
        return diff_hours < 48
    }
}

class MediaItemsWrapper(var items: ArrayList<ObjWithMedia> = arrayListOf()) : Serializable

interface ObjWithMedia
{
    var type: TypeMedia?
    var preview_url: String?
}

interface ObjWithVideo
{
    val video_url: String?
    val video_preview_url: String?
}

interface ObjWithImageUrl
{
    val image_url: String?

    companion object
    {
        fun fromString(str: String): ObjWithImageUrl
        {
            return object : ObjWithImageUrl
            {
                override val image_url: String = str
            }
        }
    }
}

interface ObjWithFile
{
    fun getObjFileName(): String
    fun getObjFileSize(): Double
}

interface ViewWithOverlay
{
    var view_overlay: View?

    fun showOverlay(duration: Int = 300)
    {
        view_overlay?.animateFadeOut(duration)
    }

    fun hideOverlay(duration: Int = 300, visibility: Int = View.GONE)
    {
        view_overlay?.animateFadeIn(duration, visibility)
    }
}
