package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.View
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEdit

class TabProfile(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaProfileMvp.MvpView
    val composite_disposable = act_main.composite_disposable
    val base_networker = act_main.base_networker

    init
    {
        mvp_view = act_main.view_factory.getLaProfileMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())

        mvp_view.bindUser(ModelUser.getTestUser())

        setEvents()
        loadUser()
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }

    private fun setEvents()
    {
        SharedPrefsManager.pref_current_user.asObservable()
                .mainThreaded()
                .subscribe(
                    {
                        val user = it.value ?: return@subscribe

                        mvp_view.bindUser(user)
                    })
                .disposeBy(composite_disposable)

        BusMainEvents.ps_user_updated
                .mainThreaded()
                .subscribe(
                    {
                        if (it == SharedPrefsManager.pref_current_user.get().value?.id)
                        {
                            loadUser()
                        }
                    })
                .disposeBy(composite_disposable)
    }

    private fun loadUser()
    {
//        val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: return
        val user_id = 1L
        base_networker.getUserById(user_id,
            {
                SharedPrefsManager.pref_current_user.asConsumer().accept(it.asOptional())
            })
    }

    inner class PresenterImplementer : LaProfileMvp.Presenter
    {
        override fun clickedEdit()
        {
//            val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: return
            val user_id = 1L

            BuilderIntent()
                    .setActivityToStart(ActUserEdit::class.java)
                    .addParam(Constants.Extras.USER_ID, user_id)
                    .startActivity(act_main)
        }

        override fun clickedLogOut()
        {
            BuilderDialogMy()
                    .setViewId(R.layout.la_dialog_simple)
                    .setTitle(getStringMy(R.string.exiting))
                    .setText(getStringMy(R.string.exit_your_accaunt))
                    .setBtnOk(BtnAction(getStringMy(R.string.exit),
                        {
                            BusMainEvents.makeLogout()
                        }))
                    .setBtnCancel(BtnAction.getDefaultCancel())
                    .build(act_main)
        }
    }
}