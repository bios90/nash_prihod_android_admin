package com.dimfcompany.nashprihodadmin.base

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.extensions.Optional
import com.dimfcompany.nashprihodadmin.base.extensions.applyShareFlags
import com.dimfcompany.nashprihodadmin.base.extensions.makeClearAllPrevious
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.MyPush
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirst
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object BusMainEvents
{
    val bs_current_tab = BehaviorSubject.createDefault(TypeTab.NEWS)
    val ps_news_add_or_edit: PublishSubject<Long> = PublishSubject.create()
    val ps_notice_add_or_edit: PublishSubject<Long> = PublishSubject.create()
    val ps_service_add_or_edit: PublishSubject<Long> = PublishSubject.create()
    val ps_note_inserted: PublishSubject<ModelNote> = PublishSubject.create()
    val ps_user_updated: PublishSubject<Long> = PublishSubject.create()
    val bs_push_clicked: BehaviorSubject<MyPush> = BehaviorSubject.create()

    fun makeLogout()
    {
        SharedPrefsManager.pref_current_user.asConsumer().accept(Optional(null))
        val intent = Intent(AppClass.app, ActFirst::class.java)
        intent.makeClearAllPrevious()
        AppClass.app.startActivity(intent)
    }
}