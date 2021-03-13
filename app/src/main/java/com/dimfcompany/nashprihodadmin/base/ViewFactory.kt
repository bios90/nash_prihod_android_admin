package com.dimfcompany.nashprihodadmin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsersMvp
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsersMvpView
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirstMvp
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirstMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMainMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMainMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news.LaNewsMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news.LaNewsMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes.LaNotesMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes.LaNotesMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.LaProfileMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.LaProfileMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable.LaTimeTableMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable.LaTimeTableMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors.LaVisitorsMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors.LaVisitorsMvpView
import com.dimfcompany.nashprihodadmin.ui.act_media_carousel.ActCarouselMvp
import com.dimfcompany.nashprihodadmin.ui.act_media_carousel.ActCarouselMvpView
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit.ActProfileAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit.ActProfileAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvp
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvpView
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_time_add_edit.ActTimeAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_time_add_edit.ActTimeAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_timetable_day_add_edit.ActTimetableDayAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_timetable_day_add_edit.ActTimetableDayAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.la_media_image.LaMediaImageMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_image.LaMediaImageMvpView
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvpView
import javax.inject.Inject

class ViewFactory @Inject constructor(private val activity: BaseActivity)
{
    val layout_inflater: LayoutInflater

    init
    {
        layout_inflater = activity.layoutInflater
    }

    fun getActFirstMvpView(parent: ViewGroup?): ActFirstMvp.MvpView
    {
        return ActFirstMvpView(layout_inflater, parent)
    }

    fun getActRegisterMvpView(parent: ViewGroup?): ActRegisterMvp.MvpView
    {
        return ActRegisterMvpView(layout_inflater, parent)
    }

    fun getActMainMvpView(parent: ViewGroup?): ActMainMvp.MvpView
    {
        return ActMainMvpView(layout_inflater, parent)
    }

    fun getLaNewsMvpView(parent: ViewGroup?): LaNewsMvp.MvpView
    {
        return LaNewsMvpView(layout_inflater, parent)
    }

    fun getLaTimeTableMvpView(parent: ViewGroup?): LaTimeTableMvp.MvpView
    {
        return LaTimeTableMvpView(layout_inflater, parent)
    }

    fun getLaVisitorsMvpView(parent: ViewGroup?): LaVisitorsMvp.MvpView
    {
        return LaVisitorsMvpView(layout_inflater, parent)
    }

    fun getLaNotesMvpView(parent: ViewGroup?): LaNotesMvp.MvpView
    {
        return LaNotesMvpView(layout_inflater, parent)
    }

    fun getLaProfileMvpView(parent: ViewGroup?): LaProfileMvp.MvpView
    {
        return LaProfileMvpView(layout_inflater, parent)
    }

    fun getActNewsMvpView(parent: ViewGroup?): ActNewsAddEditMvp.MvpView
    {
        return ActNewsAddEditMvpView(layout_inflater, parent)
    }

    fun getActCarouselMvpView(parent: ViewGroup?): ActCarouselMvp.MvpView
    {
        return ActCarouselMvpView(layout_inflater, parent)
    }

    fun getMediaVideoMvpView(parent: ViewGroup?): LaMediaVideoMvp.MvpView
    {
        return LaMediaVideoMvpView(layout_inflater, parent)
    }

    fun getMediaImageMvpView(parent: ViewGroup?): LaMediaImageMvp.MvpView
    {
        return LaMediaImageMvpView(layout_inflater, parent)
    }

    fun getActNoticeAddEditMvpView(parent: ViewGroup?): ActNoticeAddEditMvp.MvpView
    {
        return ActNoticeAddEditMvpView(layout_inflater, parent)
    }

    fun getActServiceTextAddMvpView(parent: ViewGroup?): ActServiceTextAddEditMvp.MvpView
    {
        return ActServiceTextAddEditMvpView(layout_inflater, parent)
    }

    fun getActProfileAddEditMvpView(parent: ViewGroup?): ActProfileAddEditMvp.MvpView
    {
        return ActProfileAddEditMvpView(layout_inflater, parent)
    }

    fun getActTimetableDayAddEditMvpView(parent: ViewGroup?): ActTimetableDayAddEditMvp.MvpView
    {
        return ActTimetableDayAddEditMvpView(layout_inflater, parent)
    }

    fun getActFilterUsersMvpView(parent: ViewGroup?): ActFilterUsersMvp.MvpView
    {
        return ActFilterUsersMvpView(layout_inflater, parent)
    }

    fun getActTimeAddEditMvpView(parent: ViewGroup?): ActTimeAddEditMvp.MvpView
    {
        return ActTimeAddEditMvpView(layout_inflater, parent)
    }
}