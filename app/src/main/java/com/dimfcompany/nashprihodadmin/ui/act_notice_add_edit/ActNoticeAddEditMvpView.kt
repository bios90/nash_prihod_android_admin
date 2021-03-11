package com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActNoticeAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice

class ActNoticeAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActNoticeAddEditMvp.Presenter>(), ActNoticeAddEditMvp.MvpView
{
    val bnd_act_notice_add_edit: ActNoticeAddEditBinding

    init
    {
        bnd_act_notice_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_notice_add_edit, parent, false)
        setRootView(bnd_act_notice_add_edit.root)
        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_notice_add_edit.tvSave.setOnClickListener(
            {
                getPresenter().clickedSave()
            })
    }

    override fun bindNotice(notice: ModelNotice)
    {
        bnd_act_notice_add_edit.etTitle.setText(notice.title)
        bnd_act_notice_add_edit.etText.setText(notice.text)

        bnd_act_notice_add_edit.tvSave.text = getStringMy(R.string.save)
    }

    override fun getEtTitleText(): String?
    {
        return bnd_act_notice_add_edit.etTitle.getNullableText()
    }

    override fun getEtTextText(): String?
    {
        return bnd_act_notice_add_edit.etText.getNullableText()
    }
}