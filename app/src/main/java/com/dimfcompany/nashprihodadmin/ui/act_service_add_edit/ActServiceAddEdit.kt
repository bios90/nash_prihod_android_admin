package com.dimfcompany.nashprihodadmin.ui.act_service_add_edit

import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespServiceSingle
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.addDays
import com.dimfcompany.nashprihodadmin.logic.utils.builders.*
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.networking.BaseNetworker
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit.ActServiceTimeAddEdit
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import kotlin.collections.ArrayList

class ActServiceAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActServiceAddEditMvp.MvpView
    val bs_times: BehaviorSubject<ArrayList<ModelServiceTime>> = BehaviorSubject.createDefault(arrayListOf())
    val bs_texts: BehaviorSubject<ArrayList<ModelServiceText>> = BehaviorSubject.createDefault(arrayListOf())
    val bs_date: BehaviorSubject<Date> = BehaviorSubject.createDefault(Date().addDays(1))
    val bs_service_to_edit: BehaviorSubject<ModelService> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActTimetableDayAddEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        checkExtra()
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

    private fun setEvents()
    {
        bs_date
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindServiceDate(it)
                    })
                .disposeBy(composite_disposable)

        bs_service_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        it.date?.let(
                            {
                                bs_date.onNext(it)
                            })

                        mvp_view.bindServiceText(it.title)

                        it.service_times?.forEach(
                            {
                                handleTimeAddEdit(it, null)
                            })

                        it.service_texts?.forEach(
                            {
                                handleTextAddEdit(it, null)
                            })

                        mvp_view.bindBtnSaveText(getStringMy(R.string.save))
                    })
                .disposeBy(composite_disposable)
    }

    private fun toAddEditText(text_to_edit: ModelServiceText?)
    {
        BuilderIntent().setActivityToStart(ActServiceTextAddEdit::class.java)
                .addParam(Constants.Extras.MODEL_SERVICE_TEXT, text_to_edit)
                .setOkAction(
                    {
                        val text_extra = it?.getSerializableExtra(Constants.Extras.MODEL_SERVICE_TEXT) as? ModelServiceText

                        if (text_extra != null)
                        {
                            handleTextAddEdit(text_extra, text_to_edit)
                        }
                    })
                .startActivity(this@ActServiceAddEdit)
    }

    private fun toAddEditTime(time_to_edit: ModelServiceTime?)
    {
        BuilderIntent().setActivityToStart(ActServiceTimeAddEdit::class.java)
                .addParam(Constants.Extras.MODEL_SERVICE_TIME, time_to_edit)
                .setOkAction(
                    {
                        val time_extra = it?.getSerializableExtra(Constants.Extras.MODEL_SERVICE_TIME) as? ModelServiceTime

                        if (time_extra != null)
                        {
                            handleTimeAddEdit(time_extra, time_to_edit)
                        }
                    })
                .startActivity(this@ActServiceAddEdit)
    }

    private fun handleTimeAddEdit(time_result: ModelServiceTime, time_to_edit: ModelServiceTime?)
    {
        if (time_to_edit == null)
        {
            bs_times.value?.add(time_result)
            mvp_view.addServiceTime(time_result)
        }
        else
        {
            val pos = bs_times.value?.indexOf(time_to_edit)
            if (pos != null && pos > -1)
            {
                bs_times.value?.set(pos, time_result)
                mvp_view.updateServiceTime(time_result, pos)
            }
        }
    }

    private fun handleTextAddEdit(text_result: ModelServiceText, text_to_edit: ModelServiceText?)
    {
        if (text_to_edit == null)
        {
            bs_texts.value?.add(text_result)
            mvp_view.addServiceText(text_result)
        }
        else
        {
            val pos = bs_texts.value?.indexOf(text_to_edit)
            if (pos != null && pos > -1)
            {
                bs_texts.value?.set(pos, text_result)
                mvp_view.updateServiceText(text_result, pos)
            }
        }
    }

    inner class PresenterImplementer : ActServiceAddEditMvp.Presenter
    {
        override fun clickedAddServiceTime()
        {
            toAddEditTime(null)
        }

        override fun clickedAddServiceText()
        {
            toAddEditText(null)
        }

        override fun clickedServiceTime(service_time: ModelServiceTime)
        {
            BuilderDialogBottom()
                    .addBtn(BtnAction(getStringMy(R.string.edit),
                        {
                            toAddEditTime(service_time)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.delete),
                        {
                            val pos = bs_times.value?.indexOf(service_time)

                            if (pos != null && pos > -1)
                            {
                                bs_times.value?.removeAt(pos)
                                mvp_view.removeServiceTimeAtPos(pos)
                            }
                        }))
                    .show(supportFragmentManager)
        }

        override fun clickedServiceText(service_text: ModelServiceText)
        {
            BuilderDialogBottom()
                    .addBtn(BtnAction(getStringMy(R.string.edit),
                        {
                            toAddEditText(service_text)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.delete),
                        {
                            val pos = bs_texts.value?.indexOf(service_text)

                            if (pos != null && pos > -1)
                            {
                                bs_texts.value?.removeAt(pos)
                                mvp_view.removeServiceTextAtPos(pos)
                            }
                        }))
                    .show(supportFragmentManager)
        }

        override fun clickedDate()
        {
            BuilderDateDialog()
                    .setDateMin(Date())
                    .setTitle("Выберите дату службы")
                    .setActionSuccess(
                        {
                            Log.e("PresenterImplementer", "clickedDate: Got date ${it.formatToString()}")
                            bs_date.onNext(it)
                        })
                    .show(supportFragmentManager)
        }

        override fun clickedSave()
        {
            val service_id_to_edit = bs_service_to_edit.value?.id
            val title = mvp_view.getEtTitleText()
            val date = bs_date.value
            val times = bs_times.value

            val data = ValidationManager.validateServiceAddEdit(title, date, times)
            if (!data.is_valid)
            {
                data.showErrors(this@ActServiceAddEdit)
                return
            }


            BuilderNet<Any>()
                    .setBaseActivity(this@ActServiceAddEdit)
                    .setActionMultiRequests(
                        {
                            val times_ids = BaseNetworker.insertServiceTimes(bs_times.value ?: arrayListOf(), api_services)
                            val text_ids = BaseNetworker.insertServiceTexts(bs_texts.value ?: arrayListOf(), api_services)

                            val service_times_ids = times_ids.joinToString("-")
                            val service_texts_ids = text_ids.joinToString("-")

                            val service = api_services.uspertService(
                                service_id_to_edit,
                                title!!,
                                date!!.formatToString(DateManager.FORMAT_FOR_SERVER_LARAVEL),
                                service_times_ids,
                                service_texts_ids)
                                    .toObjOrThrow(RespServiceSingle::class.java)
                                    .addParseCheckerForObj(
                                        {
                                            return@addParseCheckerForObj it.service?.id != null
                                        })
                                    .service


                            BusMainEvents.ps_service_add_or_edit.onNext(service!!.id!!)
                            finish()
                        })
                    .run()
        }
    }

    private fun checkExtra()
    {
        val service_id = intent.getLongExtraMy(Constants.Extras.SERVICE_TO_EDIT)
        if (service_id != null)
        {
            base_networker.getServiceById(service_id,
                {
                    bs_service_to_edit.onNext(it)
                })
        }
    }
}