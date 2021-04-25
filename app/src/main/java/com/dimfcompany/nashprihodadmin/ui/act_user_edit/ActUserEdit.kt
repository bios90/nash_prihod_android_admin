package com.dimfcompany.nashprihodadmin.ui.act_user_edit

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.extensions.Optional
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespNewsSingle
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespUserSingle
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDateDialog
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderNet
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.logic.utils.minusYears
import com.dimfcompany.nashprihodadmin.networking.BaseNetworker
import com.dimfcompany.nashprihodadmin.networking.apis.makeInsertOrUpdateNews
import com.dimfcompany.nashprihodadmin.networking.apis.makeUserEdit
import io.reactivex.subjects.BehaviorSubject
import java.lang.RuntimeException
import java.util.*

class ActUserEdit : BaseActivity()
{
    lateinit var mvp_view: ActUserEditMvp.MvpView

    val bs_user_to_edit: BehaviorSubject<ModelUser> = BehaviorSubject.create()
    val bs_user_avatar: BehaviorSubject<ObjWithImageUrl> = BehaviorSubject.create()
    val bs_birthday: BehaviorSubject<Optional<Date>> = BehaviorSubject.create()
    val bs_nameday: BehaviorSubject<Optional<Date>> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActUserEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        loadUser()
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = true
        is_light_nav_bar = true
    }

    private fun setEvents()
    {
        BusMainEvents.ps_user_updated
                .mainThreaded()
                .subscribe(
                    {
                        if(it == getUserIdExtra())
                        {
                            finish()
                        }
                    })
                .disposeBy(composite_disposable)

        bs_user_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindUser(it)
                        bs_birthday.onNext(it.birthday.asOptional())
                        bs_nameday.onNext(it.name_day.asOptional())

                        if (it.avatar?.url != null)
                        {
                            bs_user_avatar.onNext(it.avatar!!)
                        }
                    })
                .disposeBy(composite_disposable)

        bs_birthday
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindBirthdayDate(it.value)
                    })
                .disposeBy(composite_disposable)

        bs_nameday
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNameDate(it.value)
                    })
                .disposeBy(composite_disposable)

        bs_user_avatar
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindUserAvatar(it)
                    })
                .disposeBy(composite_disposable)
    }

    private fun loadUser()
    {
        base_networker.getUserById(getUserIdExtra(),
            {
                bs_user_to_edit.onNext(it)
            })
    }

    private fun makeUserEdit(user: ModelUser)
    {
        BuilderNet<Any>()
                .setBaseActivity(this)
                .setActionMultiRequests(
                    {

                        if (bs_user_avatar.value != null && bs_user_avatar.value is MyFileItem)
                        {
                            val my_file = bs_user_avatar.value as MyFileItem
                            val uploaded_file = BaseNetworker.uploadImage(my_file, api_files)
                            user.avatar = uploaded_file
                        }

                        val user_after_server = api_users.makeUserEdit(user)
                                .toObjOrThrow(RespUserSingle::class.java)
                                .addParseCheckerForObj({ it.user?.id != null })
                                .user!!

                        BusMainEvents.ps_user_updated.onNext(user_after_server.id!!)
                    })
                .run()
    }

    inner class PresenterImplementer : ActUserEditMvp.Presenter
    {
        override fun clickedSave()
        {
            val user = ModelUser()
            user.id = getUserIdExtra()
            user.first_name = mvp_view.getEtFirstNameText()
            user.last_name = mvp_view.getEtLastText()
            user.email = mvp_view.getEtEmailText()
            user.phone = mvp_view.getEtPhoneText()
            user.about_me = mvp_view.getEtAboutMeText()
            user.birthday = bs_birthday.value?.value
            user.name_day = bs_nameday.value?.value

            val data = ValidationManager.validateUserEdit(user.first_name, user.last_name, user.email)
            if (!data.is_valid)
            {
                data.showErrors(this@ActUserEdit)
                return
            }

            makeUserEdit(user)
        }

        override fun clickedAvatar()
        {
            checkAndPickImage(
                {
                    bs_user_avatar.onNext(it)
                })
        }

        override fun clickedBirthday()
        {
            BuilderDateDialog()
                    .setDateMax(Date())
                    .setDateMin(Date().minusYears(100))
                    .setDateCurrent(bs_birthday.value?.value ?: Date())
                    .setActionSuccess(
                        {
                            bs_birthday.onNext(it.asOptional())
                        })
                    .show(supportFragmentManager)
        }

        override fun clickedNameday()
        {
            BuilderDateDialog()
                    .setDateMax(Date())
                    .setDateMin(Date().minusYears(100))
                    .setDateCurrent(bs_nameday.value?.value ?: Date())
                    .setActionSuccess(
                        {
                            bs_nameday.onNext(it.asOptional())
                        })
                    .show(supportFragmentManager)
        }
    }

    private fun getUserIdExtra(): Long
    {
        val user_id = intent.getLongExtraMy(Constants.Extras.USER_ID)
        if (user_id == null)
        {
            throw RuntimeException("*** Error no user_id ***")
        }

        return user_id
    }
}