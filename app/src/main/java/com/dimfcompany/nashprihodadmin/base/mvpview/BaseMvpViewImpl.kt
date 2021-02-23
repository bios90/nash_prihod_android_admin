package com.dimfcompany.nashprihodadmin.base.mvpview

import android.view.View

abstract class BaseMvpViewImpl<Presenter> : BaseMvpView<Presenter>
{
    private lateinit var view: View
    private var presenter: Presenter? = null

    protected fun <T : View> findViewById(id: Int): T
    {
        return getRootView().findViewById(id)
    }

    override fun getRootView(): View
    {
        return view
    }

    override fun setRootView(view: View)
    {
        this.view = view
    }

    override fun getPresenter(): Presenter
    {
        return this.presenter!!
    }

    override fun registerPresenter(presenter: Presenter)
    {
        this.presenter = presenter
    }

    override fun unregisterPresenter(presenter: Presenter)
    {
        if (this.presenter == presenter)
        {
            this.presenter = null
        }
    }
}