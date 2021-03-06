package com.dimfcompany.nashprihodadmin.ui.act_news_add_edit

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.MediaItemsWrapper
import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.ValidationData
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespFile
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderNet
import com.dimfcompany.nashprihodadmin.logic.utils.files.FileManager
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.networking.BaseNetworker
import com.dimfcompany.nashprihodadmin.ui.act_media_carousel.ActCarousel

class ActNewsAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActNewsAddEditMvp.MvpView
    val medias: ArrayList<ObjWithMedia> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNewsMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.white)
        color_nav_bar = getColorMy(R.color.white)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun addMediaObj(obj: ObjWithMedia)
    {
        this.medias.add(obj)
        mvp_view.addMediaObj(obj)
        runActionWithDelay(lifecycleScope, 300,
            {
                mvp_view.scrollImagesToEnd()
            })

    }

    private fun getIdToEditExtra(): Long?
    {
        return intent.getLongExtraMy(Constants.Extras.NEWS_TO_EDIT)
    }

    inner class PresenterImplementer : ActNewsAddEditMvp.Presenter
    {
        override fun clickedRemoveMediaObject(obj: ObjWithMedia)
        {
            BuilderDialogMy()
                    .setViewId(R.layout.la_dialog_simple)
                    .setTitle(getStringMy(R.string.deleting))
                    .setText(getStringMy(R.string.delete_this_media_file))
                    .setBtnOk(BtnAction(getStringMy(R.string.delete),
                        {
                            val pos = medias.indexOf(obj)
                            if (medias.remove(obj))
                            {
                                mvp_view.removeMediaAtPos(pos)
                            }
                        }))
                    .setBtnCancel(BtnAction.getDefaultCancel())
                    .build(this@ActNewsAddEdit)
        }

        override fun clickedMedia()
        {
            checkAndPickImageAndVideo(
                {
                    addMediaObj(it)
                },
                {
                    addMediaObj(it)
                })
        }

        override fun clickedMediaObj(obj: ObjWithMedia)
        {
            val objs_wrapper = MediaItemsWrapper(medias)
            BuilderIntent()
                    .setActivityToStart(ActCarousel::class.java)
                    .addParam(Constants.Extras.MEDIA_OBJECTS, objs_wrapper)
                    .startActivity(this@ActNewsAddEdit)
        }

        override fun clickedSave()
        {
            val title = mvp_view.getEtTitleText()
            val text = mvp_view.getEtTextText()
            val type = TypeNews.initFromPos(mvp_view.getTypePos() ?: 0)

            val news = ModelNews(getIdToEditExtra(), title, text, type, medias)

            val data = ValidationManager.validateNewsAddEdit(news)
            if (!data.is_valid)
            {
                data.show(this@ActNewsAddEdit)
                return
            }

            upload(news)

        }
    }

    private fun upload(news: ModelNews)
    {
        val uploaded_file_ids: ArrayList<Long> = arrayListOf()

        BuilderNet<Any>()
                .setBaseActivity(this)
                .setActionMultiRequests(
                    {
                        for (obj in news.media_objs ?: arrayListOf())
                        {
                            if (obj is ModelFile)
                            {
                                obj.id?.let(
                                    {
                                        uploaded_file_ids.add(it)
                                    })
                            }
                            else if (obj is MyFileItem)
                            {
                                if (obj.type == TypeMedia.VIDEO)
                                {
                                    val uploaded_file_id = BaseNetworker.uploadVideo(obj, api_files).id
                                    if (uploaded_file_id != null)
                                    {
                                        uploaded_file_ids.add(uploaded_file_id)
                                    }
                                }
                            }
                        }
                    })
                .run()
    }

}