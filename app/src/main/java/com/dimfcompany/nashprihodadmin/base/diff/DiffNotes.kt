package com.dimfcompany.nashprihodadmin.base.diff

import com.dimfcompany.nashprihodadmin.base.extensions.toJsonMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.utils.areDatesEqualForDiff

class DiffNotes(items_new: List<ModelNote>, items_old: List<ModelNote>) : BaseDiff<ModelNote>(items_new, items_old)
{
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    {
        val item_new = items_new.get(newItemPosition)
        val item_old = items_old.get(oldItemPosition)

        if (!areDatesEqualForDiff(item_new.updated, item_old.updated))
        {
            return false
        }

        if (item_new.status != item_old.status)
        {
            return false
        }

        val reader_old = item_old.reader.toJsonMy()
        val reader_new = item_new.reader.toJsonMy()
        if (!reader_new.equals(reader_old))
        {
            return false
        }

        return true
    }
}