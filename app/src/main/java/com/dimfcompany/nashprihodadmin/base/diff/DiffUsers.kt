package com.dimfcompany.nashprihodadmin.base.diff

import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.areDatesEqualForDiff

class DiffUsers(items_new: List<ModelUser>, items_old: List<ModelUser>) : BaseDiff<ModelUser>(items_new, items_old)
{
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    {
        val item_new = items_new.get(newItemPosition)
        val item_old = items_old.get(oldItemPosition)

        if (!areDatesEqualForDiff(item_new.updated, item_old.updated))
        {
            return false
        }

        if (!areDatesEqualForDiff(item_new.birthday, item_old.birthday))
        {
            return false
        }

        if (!item_new.avatar?.url.equals(item_old.avatar?.url))
        {
            return false
        }

        if (!item_new.getFullName().equals(item_old.getFullName()))
        {
            return false
        }

        if (!item_new.phone.equals(item_old.phone))
        {
            return false
        }

        if (!item_new.email.equals(item_old.email))
        {
            return false
        }

        if (item_new.status != item_old.status)
        {
            return false
        }

        return true
    }
}