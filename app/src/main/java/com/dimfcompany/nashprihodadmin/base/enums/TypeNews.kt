package com.dimfcompany.nashprihodadmin.base.enums

import android.graphics.drawable.Drawable
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
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


    fun getBgBubble(): Drawable
    {
        val bg = BuilderBg.getSimpleDrawable(999f, getColorForBubble())
        return bg
    }
}

fun TypeNews?.getColorForBubble(): Int
{
    return when (this)
    {
        TypeNews.NEWS -> getColorMy(R.color.blue_type_news)
        TypeNews.PREACHING -> getColorMy(R.color.green_type_preaching)
        TypeNews.EVENT -> getColorMy(R.color.yellow_type_event)
        else -> return getColorMy(R.color.gray4)
    }
}