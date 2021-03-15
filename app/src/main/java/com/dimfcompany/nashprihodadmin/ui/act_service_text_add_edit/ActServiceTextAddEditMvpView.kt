package com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActServiceTextAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg

class ActServiceTextAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceTextAddEditMvp.Presenter>(), ActServiceTextAddEditMvp.MvpView
{
    val bnd_act_service_text_add: ActServiceTextAddEditBinding

    init
    {
        bnd_act_service_text_add = DataBindingUtil.inflate(layoutInflater, R.layout.act_service_text_add_edit, parent, false)
        setRootView(bnd_act_service_text_add.root)
        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_service_text_add.tvSave.setOnClickListener(
            {
                getPresenter().clickedAdd()
            }
        )
    }

    override fun bindServiceText(service_text: ModelServiceText)
    {
        bnd_act_service_text_add.etTitle.setText(service_text.title)
        bnd_act_service_text_add.etText.setText(service_text.text)
    }

    override fun getEtTextTitle(): String?
    {
        return bnd_act_service_text_add.etTitle.getNullableText()
    }

    override fun getEtTextContent(): String?
    {
        return bnd_act_service_text_add.etText.getNullableText()
    }

}