package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.View
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabProfile(act_main: ActMain) : TabPresenter
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
            //        val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: return
            val user_id = 1L

//            BuilderIntent()
//                    .setActivityToStart(ActUse)
        }

        override fun clickedLogOut()
        {
        }
    }
}