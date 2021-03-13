package com.dimfcompany.nashprihodadmin.base.enums

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.google.gson.annotations.SerializedName

enum class TypeNews
{
    @SerializedName("news")
    NEWS,

    @SerializedName("preaching")
    PREACHING,

    @SerializedName("event")
    EVENT;

    companion object
    {
        fun initFromPos(pos: Int): TypeNews
        {
            return TypeNews.values().get(pos)
        }
    }

    fun getPos(): Int
    {
        return TypeNews.values().indexOf(this)
    }

    fun getNameForServer(): String
    {
        return this.name.toLowerCase()
    }

    fun getNameForDisplay(): String
    {
        return when (this)
        {
            NEWS -> getStringMy(R.string.news_single)
            PREACHING -> getStringMy(R.string.preaching)
            EVENT -> getStringMy(R.string.event)
        }
    }

    fun getColorForBubble(): Int
    {
        return when (this)
        {
            NEWS -> getColorMy(R.color.blue_type_news)
            PREACHING -> getColorMy(R.color.green_type_preaching)
            EVENT -> getColorMy(R.color.yellow_type_event)
        }
    }
}