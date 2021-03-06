package com.dimfcompany.nashprihodadmin.ui.act_service_text_add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActServiceTextAddBinding

class ActServiceTextAddMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceTextAddMvp.Presenter>(), ActServiceTextAddMvp.MvpView
{
    val bnd_act_service_text_add: ActServiceTextAddBinding
    init
    {
        bnd_act_service_text_add = DataBindingUtil.inflate(layoutInflater, R.layout.act_service_text_add, parent, false)
        setRootView(bnd_act_service_text_add.root)
        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_service_text_add.tvAddServiceText.setOnClickListener(
                {
                    getPresenter().clickedAdd()
                }
        )
    }

    override fun getEtTextTitle(): String?
    {
        return bnd_act_service_text_add.etTitleServiceText.setText("Тропарь воскресный, глас 5-й").toString()
    }

    override fun getEtTextContent(): String?
    {
        return bnd_act_service_text_add.etContentServiceText.setText("Собезначальное Слово Отцу и Духови, / от Девы рождшееся на спасение наше, / воспоим, вернии, и поклонимся, / яко благоволи плотию взыти на крест, / и смерть претерпети, / и воскресити умершия / славным воскресением Своим.").toString()
    }
}