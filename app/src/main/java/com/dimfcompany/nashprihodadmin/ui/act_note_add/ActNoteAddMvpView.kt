package com.dimfcompany.nashprihodadmin.ui.act_note_add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.EtFortmatterHelper
import com.dimfcompany.nashprihodadmin.base.extensions.getCheckedPosition
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.getTypeFaceFromResource
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActNoteAddBinding
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams

class ActNoteAddMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActNoteAddMvp.Presenter>(), ActNoteAddMvp.MvpView
{
    val bnd_act_note_add: ActNoteAddBinding

    init
    {
        bnd_act_note_add = DataBindingUtil.inflate(layoutInflater, R.layout.act_note_add, parent, false)
        setRootView(bnd_act_note_add.root)
        setListeners()

        setSeekBar()
//        EtFortmatterHelper.addEtCommasFormatter(bnd_act_note_add.etText)
    }

    private fun setListeners()
    {
        bnd_act_note_add.tvSave.setOnClickListener(
            {
                getPresenter().clickedSave()
            })

        bnd_act_note_add.sbDonation.onSeekChangeListener = object : OnSeekChangeListener
        {
            override fun onSeeking(seekParams: SeekParams?)
            {
                val pos = seekParams?.progress ?: return
                getPresenter().priceSelected(pos)
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?)
            {
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?)
            {
            }
        }
    }

    private fun setSeekBar()
    {
        val texts = arrayOf("0", "50", "100", "150", "200", "250", "300", "500", "750", "1k")
        bnd_act_note_add.sbDonation.customTickTextsTypeface(getTypeFaceFromResource(R.font.rubik_reg))
        bnd_act_note_add.sbDonation.customTickTexts(texts)
    }

    override fun bindPriceText(text: String)
    {
        bnd_act_note_add.tvPrice.text = text
    }

    override fun getEtNamesText(): String?
    {
        return bnd_act_note_add.etNames.getNullableText()
    }

    override fun getForHealthPos(): Int
    {
        return bnd_act_note_add.rgMode.getCheckedPosition()!!
    }
}