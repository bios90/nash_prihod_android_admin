package com.dimfcompany.nashprihodadmin.base

import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import io.reactivex.subjects.BehaviorSubject

object BusMainEvents
{
    val bs_current_tab = BehaviorSubject.createDefault(TypeTab.NEWS)
}