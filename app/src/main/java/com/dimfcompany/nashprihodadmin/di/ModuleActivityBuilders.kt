package com.dimfcompany.nashprihodadmin.di

import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.ui.ActPayment
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsers
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirst
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullScreen
import com.dimfcompany.nashprihodadmin.ui.act_filter_notes.ActFilterNotes
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_news_show.ActNewsShow
import com.dimfcompany.nashprihodadmin.ui.act_note_add.ActNoteAdd
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShow
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit.ActProfileAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegister
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit.ActServiceTimeAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_service_add_edit.ActServiceAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEdit
import com.dimfcompany.nashprihodadmin.ui.act_user_show.ActUserShow
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleActivityBuilders
{
//    @ContributesAndroidInjector(modules = [ModuleActTest::class])
//    abstract fun contributeActTest(): ActTest

    @ContributesAndroidInjector(modules = [ModuleActFirst::class])
    abstract fun contributeActFirst(): ActFirst

    @ContributesAndroidInjector(modules = [ModuleActRegister::class])
    abstract fun contributeActRegister(): ActRegister

    @ContributesAndroidInjector(modules = [ModuleActMain::class])
    abstract fun contributeActMain(): ActMain

    @ContributesAndroidInjector(modules = [ModuleActNewsAddEdit::class])
    abstract fun contributeActNewsAddEdit(): ActNewsAddEdit

    @ContributesAndroidInjector(modules = [ModuleActCarousel::class])
    abstract fun contributeActCarousel(): ActCarouselFullScreen

    @ContributesAndroidInjector(modules = [ModuleActNoticeAddEdit::class])
    abstract fun contributeActNoticeAddEdit(): ActNoticeAddEdit

    @ContributesAndroidInjector(modules = [ModuleActServiceTextAddEdit::class])
    abstract fun contributeActServiceTextAddEdit(): ActServiceTextAddEdit

    @ContributesAndroidInjector(modules = [ModuleActTimeAddEdit::class])
    abstract fun contributeActTimeAddEdit(): ActServiceTimeAddEdit

    @ContributesAndroidInjector(modules = [ModuleActProfileAddEdit::class])
    abstract fun contributeActProfileAddEdit(): ActProfileAddEdit

    @ContributesAndroidInjector(modules = [ModuleActTimetableDayAddEdit::class])
    abstract fun contributeActTimetableDayAddEdit(): ActServiceAddEdit

    @ContributesAndroidInjector(modules = [ModuleActFilterUsers::class])
    abstract fun contributeActFilterUsers(): ActFilterUsers

    @ContributesAndroidInjector(modules = [ModuleActNewsShow::class])
    abstract fun contributeActNewsShow(): ActNewsShow

    @ContributesAndroidInjector(modules = [ModuleActNoteAdd::class])
    abstract fun contributeActNoteAdd(): ActNoteAdd

    @ContributesAndroidInjector(modules = [ModuleActPayment::class])
    abstract fun contributeActPayment(): ActPayment

    @ContributesAndroidInjector(modules = [ModuleActNoteShow::class])
    abstract fun contributeActNoteShow(): ActNoteShow

    @ContributesAndroidInjector(modules = [ModuleActUserEdit::class])
    abstract fun contributeActUserEdit(): ActUserEdit

    @ContributesAndroidInjector(modules = [ModuleActUserShow::class])
    abstract fun contributeActUserShow(): ActUserShow

    @ContributesAndroidInjector(modules = [ModuleActFilterNotes::class])
    abstract fun contributeActFilterNotes(): ActFilterNotes
}

@Module
abstract class ModuleActFirst
{
    @Binds
    abstract fun bindBaseActivity(act: ActFirst): BaseActivity
}

@Module
abstract class ModuleActRegister
{
    @Binds
    abstract fun bindBaseActivity(act: ActRegister): BaseActivity
}

@Module
abstract class ModuleActMain
{
    @Binds
    abstract fun bindBaseActivity(act: ActMain): BaseActivity
}


//@Module
//abstract class ModuleActTest
//{
//    @Binds
//    abstract fun bindBaseActivity(act: ActTest): BaseActivity
//}

@Module
abstract class ModuleActNewsAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActNewsAddEdit): BaseActivity
}

@Module
abstract class ModuleActCarousel
{
    @Binds
    abstract fun bindBaseActivity(act: ActCarouselFullScreen): BaseActivity
}

@Module
abstract class ModuleActNoticeAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActNoticeAddEdit): BaseActivity
}

@Module
abstract class ModuleActServiceTextAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActServiceTextAddEdit): BaseActivity
}

@Module
abstract class ModuleActTimeAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActServiceTimeAddEdit): BaseActivity
}

@Module
abstract class ModuleActProfileAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActProfileAddEdit): BaseActivity
}

@Module
abstract class ModuleActTimetableDayAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActServiceAddEdit): BaseActivity
}

@Module
abstract class ModuleActFilterUsers
{
    @Binds
    abstract fun bindBaseActivity(act: ActFilterUsers): BaseActivity
}

@Module
abstract class ModuleActNewsShow
{
    @Binds
    abstract fun bindBaseActivity(act: ActNewsShow): BaseActivity
}

@Module
abstract class ModuleActNoteAdd
{
    @Binds
    abstract fun bindBaseActivity(act: ActNoteAdd): BaseActivity
}

@Module
abstract class ModuleActPayment
{
    @Binds
    abstract fun bindBaseActivity(act: ActPayment): BaseActivity
}

@Module
abstract class ModuleActNoteShow
{
    @Binds
    abstract fun bindBaseActivity(act: ActNoteShow): BaseActivity
}

@Module
abstract class ModuleActUserEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActUserEdit): BaseActivity
}

@Module
abstract class ModuleActUserShow
{
    @Binds
    abstract fun bindBaseActivity(act: ActUserShow): BaseActivity
}

@Module
abstract class ModuleActFilterNotes
{
    @Binds
    abstract fun bindBaseActivity(act: ActFilterNotes): BaseActivity
}