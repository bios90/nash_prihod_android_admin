package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.akcsl.base.LoadBehavior
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.extensions.Optional
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.base.extensions.runRepeatingAction
import com.dimfcompany.nashprihodadmin.base.isVisibleNow
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataNotes
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderAlerter
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_filter_notes.ActFilterNotes
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_note_add.ActNoteAdd
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

class TabNotes(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNotesMvp.MvpView
    val composite_disposable = act_main.composite_disposable
    val base_networker = act_main.base_networker
    var job_notes_reloading: Job? = null
    val bs_notes: BehaviorSubject<FeedDisplayInfo<ModelNote>> = BehaviorSubject.create()
    val bs_notes_status: BehaviorSubject<Optional<TypeNoteStatus>> = BehaviorSubject.create()
    val ps_to_reload_notes: PublishSubject<Any> = PublishSubject.create()
    val bs_filter_data_notes: BehaviorSubject<FilterDataNotes> = BehaviorSubject.createDefault(FilterDataNotes())

    init
    {
        mvp_view = act_main.view_factory.getLaNotesMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }

    private fun setEvents()
    {
        act_main.lifecycle.addObserver(LcObserver())

        BusMainEvents.bs_current_tab
                .distinctUntilChanged()
                .mainThreaded()
                .subscribe(
                    {
                        if (it == TypeTab.NOTES)
                        {
                            setNotesReloadingJob()
                        }
                        else
                        {
                            cancelNotesReloadingJob()
                        }
                    })
                .disposeBy(composite_disposable)

        BusMainEvents.ps_note_inserted
                .mainThreaded()
                .subscribe(
                    {
                        var text = "Записка подана"
                        if (it.donation_sum != null && it.donation_sum!! > 0)
                        {
                            text += "\nБлагодарим за пожертвование"
                        }

                        BuilderAlerter.getGreenBuilder(text)
                                .show(act_main)
                    })
                .disposeBy(composite_disposable)

        ps_to_reload_notes
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .mainThreaded()
                .subscribe(
                    {
                        val filted_data = bs_filter_data_notes.value
                        base_networker.getNotes(filted_data?.status, null, filted_data?.search_text,
                            {
                                bs_notes.onNext(FeedDisplayInfo(it, LoadBehavior.UPDATE))
                            })
                    })
                .disposeBy(composite_disposable)

        bs_notes
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNotes(it)
                    })
                .disposeBy(composite_disposable)

        bs_filter_data_notes
                .mainThreaded()
                .subscribe(
                    {
                        ps_to_reload_notes.onNext(Any())
                    })
                .disposeBy(composite_disposable)
    }

    private fun setNotesReloadingJob()
    {
        if (job_notes_reloading?.isActive == true)
        {
            return
        }

        if (BusMainEvents.bs_current_tab.value != TypeTab.NOTES || !act_main.isVisibleNow())
        {
            return
        }

        job_notes_reloading = runRepeatingAction(act_main.lifecycleScope, 20000,
            {
                ps_to_reload_notes.onNext(Any())
            }, 999)
    }

    private fun cancelNotesReloadingJob()
    {
        job_notes_reloading?.cancel()
    }

    inner class PresenterImplementer : LaNotesMvp.Presenter
    {
        override fun swipedToRefresh()
        {
            ps_to_reload_notes.onNext(Any())
        }

        override fun clickedAddNotes()
        {
            BuilderIntent()
                    .setActivityToStart(ActNoteAdd::class.java)
                    .startActivity(act_main)
        }

        override fun clickedNote(note: ModelNote)
        {
            val note_id = note.id ?: return
            BuilderIntent()
                    .setActivityToStart(ActNoteShow::class.java)
                    .addParam(Constants.Extras.NOTE_ID, note_id)
                    .startActivity(act_main)
        }

        override fun clickedFilterNotes()
        {
            BuilderIntent()
                    .setActivityToStart(ActFilterNotes::class.java)
                    .addParam(Constants.Extras.FILTER_DATA_NOTES, bs_filter_data_notes.value!!)
                    .setOkAction(
                        {
                            val filter_data = it?.getSerializableExtra(Constants.Extras.FILTER_DATA_NOTES) as? FilterDataNotes
                            if (filter_data != null)
                            {
                                bs_filter_data_notes.onNext(filter_data)
                            }
                        })
                    .startActivity(act_main)
        }
    }

    inner class LcObserver : LifecycleObserver
    {
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun pause()
        {
            cancelNotesReloadingJob()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun resume()
        {
            setNotesReloadingJob()
        }
    }
}