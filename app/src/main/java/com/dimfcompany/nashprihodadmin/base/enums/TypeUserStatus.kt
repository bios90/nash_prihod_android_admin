package com.dimfcompany.nashprihodadmin.base.enums

enum class TypeUserStatus
{
    WAIT_ADMIN_APPROVE,
    ACTIVE,
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