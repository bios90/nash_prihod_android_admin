package com.dimfcompany.nashprihodadmin.base.diff

import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.utils.areDatesEqualForDiff

class DiffEvents(items_new: List<ModelNews>, items_old: List<ModelNews>) : BaseDiff<ModelNews>(items_new, items_old)
{
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    {
        val item_new = items_new.get(newItemPosition)
        val item_old = items_old.get(oldItemPosition)

        if (!areDatesEqualForDiff(item_new.updated, item_old.updated))
        {
            return false
        }

        if (!item_new.title.equals(item_old.title))
        {
            return false
        }

        if (!item_new.text.equals(item_old.text))
        {
            return false
        }

        if (item_new.type != item_old.type)
        {
            return false
        }

        if (item_new.media_objs?.size != item_old.media_objs?.size)
        {
            return false
        }

        return true
    }
}