package com.dimfcompany.nashprihodadmin.base

import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object BusMainEvents
{
    val bs_current_tab = BehaviorSubject.createDefault(TypeTab.NEWS)
    val ps_news_add_or_edit: PublishSubject<Long> = PublishSubject.create()
    val ps_notice_add_or_edit: PublishSubject<Long> = PublishSubject.create()
}