package com.dimfcompany.nashprihodadmin.base.mvpview

import android.view.View

interface BaseMvpView<Presenter>
{
    fun getRootView(): View
    fun setRootView(view: View)

    fun getPresenter(): Presenter
    fun registerPresenter(presenter: Presenter)
    fun unregisterPresenter(presenter: Presenter)
}