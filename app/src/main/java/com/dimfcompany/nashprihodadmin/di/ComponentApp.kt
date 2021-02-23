package com.dimfcompany.nashprihodadmin.di

import com.dimfcompany.nashprihodadmin.base.AppClass
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules =
[
    AndroidInjectionModule::class,
    ModuleActivityBuilders::class,
    ModuleNetworking::class
])
interface ComponentApp : AndroidInjector<AppClass>
{

}