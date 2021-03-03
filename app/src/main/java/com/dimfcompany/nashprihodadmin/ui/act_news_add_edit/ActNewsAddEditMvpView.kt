package com.dimfcompany.nashprihodadmin.ui.act_news_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.getCheckedPosition
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.scrollRight
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActFirstBinding
import com.dimfcompany.nashprihodadmin.databinding.ActNewsAddEditBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemSquareImgBinding
import com.rucode.autopass.logic.utils.images.GlideManager

class ActNewsAddEditMvpView(val layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActNewsAddEditMvp.Presenter>(), ActNewsAddEditMvp.MvpView
{
    val bnd_act_news_add_edit: ActNewsAddEditBinding

    init
    {
        bnd_act_news_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_news_add_edit, parent, false)
        setRootView(bnd_act_news_add_edit.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_news_add_edit.tvAddMedia.setOnClickListener(
            {
                getPresenter().clickedMedia()
            })

        bnd_act_news_add_edit.tvCreate.setOnClickListener(
            {
                getPresenter().clickedSave()
            })
    }

    override fun addMediaObj(obj: ObjWithMedia)
    {
        val bnd_image: ItemSquareImgBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_square_img, bnd_act_news_add_edit.laForMedias, false)
        bnd_act_news_add_edit.laForMedias.addView(bnd_image.root)

        bnd_image.tvPlayVideo.visibility = (obj.type == TypeMedia.VIDEO).toVisibility()

        bnd_image.img.setOnClickListener(
            {
                getPresenter().clickedMediaObj(obj)
            })

        bnd_image.tvRemove.setOnClickListener(
            {
                getPresenter().clickedRemoveMediaObject(obj)
            })

        GlideManager.loadImage(bnd_image.img, obj.preview_url)
    }

    override fun scrollImagesToEnd()
    {
        bnd_act_news_add_edit.imgScrollImages.scrollRight()
    }

    override fun removeMediaAtPos(pos: Int)
    {
        if (pos <= bnd_act_news_add_edit.laForMedias.childCount)
        {
            bnd_act_news_add_edit.laForMedias.removeViewAt(pos)
        }
    }

    override fun getEtTitleText(): String?
    {
        return bnd_act_news_add_edit.etName.getNullableText()
    }

    override fun getEtTextText(): String?
    {
        return bnd_act_news_add_edit.etText.getNullableText()
    }

    override fun getTypePos(): Int?
    {
        return bnd_act_news_add_edit.rgNewsType.getCheckedPosition()
    }
}