package com.dimfcompany.nashprihodadmin.base.enums

enum class TypeSort
{
    BY_NAME,
    BY_STATUS,
    BY_REGISTER_DATE;

    companion object
    {
        fun initFromRbStatus(pos: Int): TypeSort?
        {
            return when (pos)
            {
                0 -> return BY_NAME
                1 -> return BY_STATUS
                2 -> return BY_REGISTER_DATE
                else -> null
            }
        }
    }

    fun getNameForServer(): String
    {
        return when (this)
        {
            BY_NAME -> "by_name"
            BY_STATUS -> "status"
            BY_REGISTER_DATE -> "register_date"
        }
    }

    fun getPosForRb(): Int
    {
        return when (this)
        {
            BY_NAME -> 0
            BY_STATUS -> 1
            BY_REGISTER_DATE -> 2
        }
    }
}