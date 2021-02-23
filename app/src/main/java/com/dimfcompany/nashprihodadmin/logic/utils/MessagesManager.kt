package com.dimfcompany.nashprihodadmin.logic.utils

import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.extensions.makeTransparentBg
import com.dimfcompany.nashprihodadmin.base.extensions.openAppSettings
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class BtnAction(val text: String, val action: (() -> Unit)?, val faw_text: String? = null)
{
    companion object
    {
        fun getDefaultCancel(): BtnAction
        {
            return BtnAction(getStringMy(R.string.cancel), null)
        }

        fun getDefaultOk(action: (() -> Unit)? = null): BtnAction
        {
            return BtnAction(getStringMy(R.string.ok), null)
        }
    }
}

class BtnActionWithText(val text: String, val action: ((String?) -> Unit)?)

class MessagesManager @Inject constructor(val activity: BaseActivity)
{
    companion object
    {

        fun getBuilderPermissionsBlockedNow(text: String = getStringMy(R.string.need_permissions_global), action_try_again: (() -> Unit)?): BuilderDialogMy
        {
            val dialog = BuilderDialogMy()
                    .setTitle(getStringMy(R.string.permissions))
                    .setText(text)
                    .setViewId(R.layout.la_dialog_simple)
                    .setBtnOk(BtnAction(getStringMy(R.string.try_again), action_try_again))
                    .setBtnCancel(BtnAction(getStringMy(R.string.later), null))

            return dialog
        }

        fun getBuilderPermissionsBlockedFinally(text: String = getStringMy(R.string.need_permissions_global)): BuilderDialogMy
        {
            val dialog = BuilderDialogMy()
                    .setTitle(getStringMy(R.string.permissions))
                    .setText(text)
                    .setViewId(R.layout.la_dialog_simple)
                    .setBtnOk(BtnAction(getStringMy(R.string.ok), null))
                    .setBtnLeft(BtnAction(getStringMy(R.string.to_settings),
                        {
                            openAppSettings()
                        }))

            return dialog
        }
    }

    private val show_delay = 700L
    private var last_time_shown: Long? = null
    private var job_showing_progress: Job? = null

    @Volatile
    private var callers_show_progess = 0

    @Volatile
    private var callers_disable_screen = 0
    var progress_dialog: AlertDialog? = null

    fun showProgressDialog()
    {
        callers_show_progess++
        if (progress_dialog?.isShowing == true || job_showing_progress?.isActive == true)
        {
            return
        }

        job_showing_progress = activity.lifecycleScope.launch(block =
        {
            delay(show_delay)
            showProgressSimple()
        })
    }

    fun showProgressSimple()
    {
        if (progress_dialog != null && progress_dialog!!.isShowing)
        {
            return
        }

        val dialogView =
                LayoutInflater.from(activity).inflate(R.layout.la_progress, null)

        progress_dialog = AlertDialog.Builder(activity).create()
        progress_dialog!!.setCancelable(false)
        progress_dialog!!.setCanceledOnTouchOutside(false)
        progress_dialog!!.setView(dialogView)
        progress_dialog!!.makeTransparentBg()
        try
        {
            progress_dialog!!.show()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
    }

    fun dismissProgressDialog()
    {
        callers_show_progess--
        val action: () -> Unit =
                {
                    if (callers_show_progess <= 0)
                    {
                        progress_dialog?.dismiss()
                        progress_dialog = null
                        job_showing_progress?.cancel()
                    }
                }


        val current_time = System.currentTimeMillis()
        if (last_time_shown != null && (current_time - last_time_shown!! < show_delay))
        {
            runActionWithDelay(activity.lifecycleScope, show_delay.toInt(), action)
        }
        else
        {
            action()
        }
    }

    fun disableScreenTouches()
    {
        callers_disable_screen++
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun enableScreenTouches(forced: Boolean)
    {
        if (forced)
        {
            callers_disable_screen = 0
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else
        {
            callers_disable_screen--
            if(callers_disable_screen < 0)
            {
                callers_disable_screen = 0
            }
            if (callers_disable_screen <= 0)
            {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }
}