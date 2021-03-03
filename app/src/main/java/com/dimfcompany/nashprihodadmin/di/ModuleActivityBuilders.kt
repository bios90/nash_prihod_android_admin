package com.dimfcompany.nashprihodadmin.di

import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirst
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_media_carousel.ActCarousel
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegister
import com.dimfcompany.nashprihodadmin.ui.act_test.ActTest
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleActivityBuilders
{
    @ContributesAndroidInjector(modules = [ModuleActTest::class])
    abstract fun contributeActTest(): ActTest

    @ContributesAndroidInjector(modules = [ModuleActFirst::class])
    abstract fun contributeActFirst(): ActFirst

    @ContributesAndroidInjector(modules = [ModuleActRegister::class])
    abstract fun contributeActRegister(): ActRegister

    @ContributesAndroidInjector(modules = [ModuleActMain::class])
    abstract fun contributeActMain(): ActMain

    @ContributesAndroidInjector(modules = [ModuleActNewsAddEdit::class])
    abstract fun contributeActNewsAddEdit(): ActNewsAddEdit

    @ContributesAndroidInjector(modules = [ModuleActCarousel::class])
    abstract fun contributeActCarousel(): ActCarousel

    @ContributesAndroidInjector(modules = [ModuleActNoticeAddEdit::class])
    abstract fun contributeActNoticeAddEdit():ActNoticeAddEdit
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


@Module
abstract class ModuleActTest
{
    @Binds
    abstract fun bindBaseActivity(act: ActTest): BaseActivity
}

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
    abstract fun bindBaseActivity(act: ActCarousel): BaseActivity
}

@Module
abstract class ModuleActNoticeAddEdit
{
    @Binds
    abstract fun bindBaseActivity(act: ActNoticeAddEdit): BaseActivity
}