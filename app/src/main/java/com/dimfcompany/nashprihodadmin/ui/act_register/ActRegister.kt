package com.dimfcompany.nashprihodadmin.ui.act_register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogBottom
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirstMvp
import io.reactivex.subjects.BehaviorSubject

class ActRegister : BaseActivity()
{
    lateinit var mvp_view: ActRegisterMvp.MvpView

    val bs_avatar_image: BehaviorSubject<Optional<MyFileItem>> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActRegisterMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
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

    private fun setEvents()
    {
        bs_avatar_image
                .mainThreaded()
                .subscribe(
                    {
                        it.value?.let(
                            {
                                mvp_view.bindAvatarImage(it)
                            })
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : ActRegisterMvp.Presenter
    {
        override fun clickedAvatar()
        {
            checkAndPickImage(
                {
                    bs_avatar_image.onNext(it.asOptional())
                })
        }

        override fun clickedRegister()
        {
            val first_name = mvp_view.getFirstNameStr()
            val last_name = mvp_view.getLastNameStr()
            val email = mvp_view.getEmailStr()
            val password_1 = mvp_view.getPassword1Str()
            val password_2 = mvp_view.getPassword2Str()
            val avatar = bs_avatar_image.value?.value

            val data = ValidationManager.validateRegister(first_name, last_name, email, password_1, password_2)
            if (!data.is_valid)
            {
                data.show(this@ActRegister)
                return
            }

            base_networker.makeRegister(
                first_name!!,
                last_name!!,
                email!!,
                password_1!!,
                avatar,
                {
                    val intent = Intent()
                    intent.myPutExtra(Constants.Extras.REGISTER_MADE, true)
                    intent.myPutExtra(Constants.Extras.EMAIL, email)
                    finishWithResultOk(intent)
                })
        }

        override fun clickedLogin()
        {
            finish()
        }
    }
}