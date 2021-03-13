package com.dimfcompany.nashprihodadmin.base.enums

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.google.gson.annotations.SerializedName

enum class TypeUserStatus
{
    @SerializedName("wait_admin_approve")
    WAIT_ADMIN_APPROVE,

    @SerializedName("active")
    ACTIVE,

    @SerializedName("banned")
    BANNED;

    companion object
    {
        fun initFromRbStatus(pos: Int): TypeUserStatus?
        {
            return when (pos)
            {
                1 -> return ACTIVE
                2 -> return WAIT_ADMIN_APPROVE
                3 -> return BANNED
                else -> null
            }
        }
    }

    fun getNameForServer(): String
    {
        return when (this)
        {
            ACTIVE -> "active"
            WAIT_ADMIN_APPROVE -> "wait_admin_approve"
            BANNED -> "banned"
        }
    }

    fun getNameForDisplay(): String
    {
        return when (this)
        {
            ACTIVE -> "активный"
            WAIT_ADMIN_APPROVE -> "ождает одобрения"
            BANNED -> "заблокирован"
        }
    }

    fun getColor(): Int
    {
        return when (this)
        {
            ACTIVE -> getColorMy(R.color.green)
            WAIT_ADMIN_APPROVE -> getColorMy(R.color.yellow_type_event)
            BANNED -> getColorMy(R.color.red)
        }
    }
}

fun TypeUserStatus?.getPosForRb(): Int
{
    return when (this)
    {
        null -> 0
        TypeUserStatus.ACTIVE -> 1
        TypeUserStatus.WAIT_ADMIN_APPROVE -> 2
        TypeUserStatus.BANNED -> 3
    }
}