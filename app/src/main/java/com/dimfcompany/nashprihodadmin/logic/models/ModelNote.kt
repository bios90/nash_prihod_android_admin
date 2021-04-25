package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.adapters.bindNote
import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.logic.utils.formatAsMoney
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ModelNote(
        override var id: Long? = null,
        var names: String? = null,
        var for_health: Boolean? = null,
        var user: ModelUser? = null,
        var reader: ModelUser? = null,
        var donation_sum: Double? = null,
        var donation_id: String? = null,
        var status: TypeNoteStatus? = null,
        @SerializedName("created_at")
        override var created: Date? = null,
        @SerializedName("updated_at")
        override var updated: Date? = null,
        @SerializedName("deleted_at")
        override var deleted: Date? = null,
) : Serializable, ObjectWithId, ObjectWithDates
{
    fun getStatusNameForDisplay(): String?
    {
        return when (status)
        {
            TypeNoteStatus.WAITS_FOR_READING -> getStringMy(R.string.waits_for_reading)

            TypeNoteStatus.IS_READING ->
            {
                val reader_name = reader?.getFullName()
                if (reader_name == null)
                {
                    return getStringMy(R.string.is_reading)
                }
                else
                {
                    return getStringMy(R.string.reads) + ": $reader_name"
                }
            }

            TypeNoteStatus.READED ->
            {
                val reader_name = reader?.getFullName()
                if (reader_name == null)
                {
                    return getStringMy(R.string.have_readed)
                }
                else
                {
                    return getStringMy(R.string.readed_by) + ": $reader_name"
                }
            }

            else -> return null
        }
    }

    fun getTextForHealthType(): String
    {
        val text_for_health = if (for_health == true) getStringMy(R.string.for_health) else getStringMy(R.string.for_peace)
        return text_for_health
    }

    fun getColorForHealthType(): Int
    {
        val color_for_health = if (for_health == true) getColorMy(R.color.red) else getColorMy(R.color.gray7)
        return color_for_health
    }

    fun getPriceText(): String
    {
        val text_donation = (this.donation_sum ?: 0.0).formatAsMoney() + " р."
        return text_donation
    }

    fun getNamesString(): String?
    {
        val text = this.names?.replace("*", ", ")
        return text
    }
}