package com.dimfcompany.nashprihodadmin.logic.utils.builders

import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.dimfcompany.nashprihodadmin.logic.utils.addDays
import com.dimfcompany.nashprihodadmin.logic.utils.addYears
import com.dimfcompany.nashprihodadmin.logic.utils.minusYears
import com.dimfcompany.nashprihodadmin.ui.dialogs.DialogBottomDate
import com.dimfcompany.nashprihodadmin.ui.dialogs.DialogBottomTime
import com.justordercompany.barista.ui.dialogs.DialogBottomSheetRounded
import java.util.*

class BuilderTimeDialog
{
    var title: String? = null
        private set
    var date_current = Date()
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
            val dialog = DialogBottomTime(this)
            dialog.show(fm, null)
        })
}