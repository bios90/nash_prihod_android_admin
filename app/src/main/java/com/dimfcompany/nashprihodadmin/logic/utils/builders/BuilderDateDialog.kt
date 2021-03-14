package com.dimfcompany.nashprihodadmin.logic.utils.builders

import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.dimfcompany.nashprihodadmin.logic.utils.addDays
import com.dimfcompany.nashprihodadmin.logic.utils.addYears
import com.dimfcompany.nashprihodadmin.logic.utils.minusYears
import com.dimfcompany.nashprihodadmin.ui.dialogs.DialogBottomDate
import com.justordercompany.barista.ui.dialogs.DialogBottomSheetRounded
import java.util.*

class BuilderDateDialog
{
    var title: String? = null
        private set
    var date_current = Date()
        private set
    var date_min = Date().minusYears(1)
        private set
    var date_max = Date().addYears(1)
        private set
    var action_success: ((Date) -> Unit)? = null
        private set
    var action_cancel: ((Date) -> Unit)? = null
        private set

    fun setTitle(title: String) = apply(
        {
            this.title = title
        })

    fun setDateCurrent(date: Date) = apply(
        {
            this.date_current = date
        })

    fun setDateMin(date: Date) = apply(
        {
            this.date_min = date
        })

    fun setDateMax(date: Date) = apply(
        {
            this.date_max = date
        })

    fun setActionSuccess(action: ((Date) -> Unit)) = apply(
        {
            this.action_success = action
        })

    fun setActionCancel(action: ((Date) -> Unit)) = apply(
        {
            this.action_cancel = action
        })

    fun show(fm: FragmentManager) = apply(
        {
            val dialog = DialogBottomDate(this)
            dialog.show(fm, null)
        })
}