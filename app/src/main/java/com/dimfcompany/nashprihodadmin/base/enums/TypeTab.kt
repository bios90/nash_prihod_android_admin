package com.dimfcompany.nashprihodadmin.base.enums

enum class TypeTab
{
    NEWS,
    TIMETABLE,
    VISITORS,
    NOTES,
    PROFILE;

    companion object
    {
        fun initFromPos(pos: Int): TypeTab
        {
            return TypeTab.values().get(pos)
        }
    }

    fun getPos():Int
    {
        return TypeTab.values().indexOf(this)
    }
}