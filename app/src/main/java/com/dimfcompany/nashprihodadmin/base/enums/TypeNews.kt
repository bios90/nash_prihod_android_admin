package com.dimfcompany.nashprihodadmin.base.enums

enum class TypeNews
{
    NEWS,
    PREACHING,
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
}