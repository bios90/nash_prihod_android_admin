package com.dimfcompany.nashprihodadmin.base.diff

import com.dimfcompany.nashprihodadmin.base.extensions.toJsonMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.areDatesEqualForDiff

class DiffService(items_new: List<ModelService>, items_old: List<ModelService>) : BaseDiff<ModelService>(items_new, items_old)
{
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    {
        val item_new = items_new.get(newItemPosition)
        val item_old = items_old.get(oldItemPosition)

        if (!areDatesEqualForDiff(item_new.updated, item_old.updated))
        {
            return false
        }

        if (!areDatesEqualForDiff(item_new.date, item_old.date))
        {
            return false
        }

        if (!item_new.title.equals(item_old.title))
        {
            return false
        }

        if (item_new.service_times?.size != item_old.service_times?.size)
        {
            return false
        }
        else if (!compareTimes(item_new.service_times, item_old.service_times))
        {
            return false
        }

        return true
    }

    private fun compareTimes(times_old: ArrayList<ModelServiceTime>?, times_new: ArrayList<ModelServiceTime>?): Boolean
    {
        if (times_new?.size != times_old?.size)
        {
            return false
        }

        times_new?.size ?: return false

        return times_new.toJsonMy().equals(times_old.toJsonMy())
    }
}