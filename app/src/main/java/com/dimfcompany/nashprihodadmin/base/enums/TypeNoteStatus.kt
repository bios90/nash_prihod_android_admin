package com.dimfcompany.nashprihodadmin.base.enums

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

enum class TypeNoteStatus
{
    @SerializedName("waits_for_reading")
    WAITS_FOR_READING,

    @SerializedName("is_reading")
    IS_READING,

    @SerializedName("readed")
    READED;


    companion object
    {
        fun initFromRbStatus(pos: Int): TypeNoteStatus?
        {
            return when (pos)
            {
                1 -> return WAITS_FOR_READING
                2 -> return IS_READING
                3 -> return READED
                else -> null
            }
        }
    }

    fun getNameForServer(): String
    {
        return when (this)
        {
            WAITS_FOR_READING -> "waits_for_reading"
            IS_READING -> "is_reading"
            READED -> "readed"
        }
    }

    fun getColor(): Int
    {
        return when (this)
        {
            WAITS_FOR_READING -> getColorMy(R.color.yellow_type_event)
            IS_READING -> getColorMy(R.color.blue_type_news)
            READED -> getColorMy(R.color.green)
        }
    }
}

fun TypeNoteStatus?.getPosForRb(): Int
{
    return when (this)
    {
        null -> 0
        TypeNoteStatus.WAITS_FOR_READING -> 1
        TypeNoteStatus.IS_READING -> 2
        TypeNoteStatus.READED -> 3
    }
}