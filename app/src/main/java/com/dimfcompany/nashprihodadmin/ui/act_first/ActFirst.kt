package com.dimfcompany.nashprihodadmin.ui.act_first

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.BtnActionWithText
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderAlerter
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegister

class ActFirst : BaseActivity()
{
    lateinit var mvp_view: ActFirstMvp.MvpView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        checkLogin()
        mvp_view = view_factory.getActFirstMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun checkLogin()
    {
        val is_logged = SharedPrefsManager.pref_current_user.get().value != null
        if (is_logged)
        {
            BuilderIntent()
                    .setActivityToStart(ActMain::class.java)
                    .addOnStartAction(
                        {
                            finish()
                        })
                    .startActivity(this)
        }
    }

    inner class PresenterImplementer : ActFirstMvp.Presenter
    {
        override fun clickedLogin()
        {
            val email = mvp_view.getEmailStr()
            val password = mvp_view.getPasswordStr()
            val fb_token = SharedPrefsManager.pref_fb_token.get().value

            val data = ValidationManager.validateLogin(email, password)
            if (!data.is_valid)
            {
                data.showErrors(this@ActFirst)
                return
            }

            base_networker.makeLogin(email!!, password!!, fb_token,
                {
                    if (it.is_admin != true)
                    {
                        BuilderAlerter.getRedBuilder("Вход в приложение возможен только администраторам")
                                .show(this@ActFirst)
                        return@makeLogin
                    }

                    SharedPrefsManager.pref_current_user.asConsumer().accept(it.asOptional())
                    checkLogin()
                })
        }

        override fun clickedRegister()
        {
            BuilderIntent()
                    .setActivityToStart(ActRegister::class.java)
                    .setOkAction(
                        {
                            val email = it?.getStringExtra(Constants.Extras.EMAIL)
                            if (email != null)
                            {
                                mvp_view.bindEmail(email)
                            }

                            if (it?.getBooleanExtra(Constants.Extras.REGISTER_MADE, false) == true)
                            {
                                BuilderAlerter.getGreenBuilder(getStringMy(R.string.congrats_register_made))
                                        .show(this@ActFirst)
                            }
                        })
                    .startActivity(this@ActFirst)
        }

        override fun clickedForgotPass()
        {
            BuilderDialogMy()
                    .setTitle(getStringMy(R.string.password_recover))
                    .setText(getStringMy(R.string.for_password_recover))
                    .setViewId(R.layout.la_dialog_with_et)
                    .setBtnOkWithText(BtnActionWithText(getStringMy(R.string.recover),
                        {
                            if (!ValidationManager.isEmail(it))
                            {
                                BuilderAlerter.getRedBuilder(getStringMy(R.string.enter_valid_email))
                                        .show(this@ActFirst)
                                return@BtnActionWithText
                            }

                            base_networker.makePassReset(it!!,
                                {
                                    BuilderAlerter.getGreenBuilder("На почту $it отправлено письмо с инструкцией по восстановлению")
                                            .show(this@ActFirst)
                                })
                        }))
                    .setBtnCancel(BtnAction.getDefaultCancel())
                    .build(this@ActFirst)
        }
    }
}