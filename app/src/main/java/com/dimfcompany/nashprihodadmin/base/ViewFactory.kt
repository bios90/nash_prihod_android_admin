package com.dimfcompany.nashprihodadmin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullscreenMvp
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullscreenMvpView
import com.dimfcompany.nashprihodadmin.ui.act_filter_notes.ActFilterNotes
import com.dimfcompany.nashprihodadmin.ui.act_filter_notes.ActFilterNotesMvp
import com.dimfcompany.nashprihodadmin.ui.act_filter_notes.ActFilterNotesMvpView
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
import com.dimfcompany.nashprihodadmin.ui.la_carousel.LaCarouselMvp
import com.dimfcompany.nashprihodadmin.ui.la_carousel.LaCarouselMvpView
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_news_show.ActNewsShowMvp
import com.dimfcompany.nashprihodadmin.ui.act_news_show.ActNewsShowMvpView
import com.dimfcompany.nashprihodadmin.ui.act_note_add.ActNoteAddMvp
import com.dimfcompany.nashprihodadmin.ui.act_note_add.ActNoteAddMvpView
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShow
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShowMvp
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShowMvpView
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit.ActProfileAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit.ActProfileAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvp
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvpView
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit.ActServiceTimeAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit.ActServiceTimeAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_service_add_edit.ActServiceAddEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_service_add_edit.ActServiceAddEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_service_show.ActServiceShowMvp
import com.dimfcompany.nashprihodadmin.ui.act_service_show.ActServiceShowMvpView
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEditMvp
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEditMvpView
import com.dimfcompany.nashprihodadmin.ui.act_user_show.ActUserShowMvp
import com.dimfcompany.nashprihodadmin.ui.act_user_show.ActUserShowMvpView
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

    fun getActCarouselFullscreenMvpView(parent: ViewGroup?): ActCarouselFullscreenMvp.MvpView
    {
        return ActCarouselFullscreenMvpView(layout_inflater, parent)
    }

    fun getActCarouselMvpView(parent: ViewGroup?): LaCarouselMvp.MvpView
    {
        return LaCarouselMvpView(layout_inflater, parent)
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

    fun getActNoteAddMvpView(parent: ViewGroup?): ActNoteAddMvp.MvpView
    {
        return ActNoteAddMvpView(layout_inflater, parent)
    }

    fun getActServiceTextAddMvpView(parent: ViewGroup?): ActServiceTextAddEditMvp.MvpView
    {
        return ActServiceTextAddEditMvpView(layout_inflater, parent)
    }

    fun getActProfileAddEditMvpView(parent: ViewGroup?): ActProfileAddEditMvp.MvpView
    {
        return ActProfileAddEditMvpView(layout_inflater, parent)
    }

    fun getActTimetableDayAddEditMvpView(parent: ViewGroup?): ActServiceAddEditMvp.MvpView
    {
        return ActServiceAddEditMvpView(layout_inflater, parent)
    }

    fun getActFilterUsersMvpView(parent: ViewGroup?): ActFilterUsersMvp.MvpView
    {
        return ActFilterUsersMvpView(layout_inflater, parent)
    }

    fun getActTimeAddEditMvpView(parent: ViewGroup?): ActServiceTimeAddEditMvp.MvpView
    {
        return ActServiceTimeAddEditMvpView(layout_inflater, parent)
    }

    fun getActNewsShowMvpView(parent: ViewGroup?): ActNewsShowMvp.MvpView
    {
        return ActNewsShowMvpView(layout_inflater, parent)
    }

    fun getActNoteShowMvpView(parent: ViewGroup?): ActNoteShowMvp.MvpView
    {
        return ActNoteShowMvpView(layout_inflater, parent)
    }

    fun getActUserEditMvpView(parent: ViewGroup?): ActUserEditMvp.MvpView
    {
        return ActUserEditMvpView(layout_inflater, parent)
    }

    fun getActUserShowMvpView(parent: ViewGroup?): ActUserShowMvp.MvpView
    {
        return ActUserShowMvpView(layout_inflater, parent)
    }

    fun getActFilterNotesMvpView(parent: ViewGroup?): ActFilterNotesMvp.MvpView
    {
        return ActFilterNotesMvpView(layout_inflater, parent)
    }

    fun getActServiceTextShowMvpView(parent: ViewGroup?): ActServiceShowMvp.MvpView
    {
        return ActServiceShowMvpView(layout_inflater, parent)
    }
}