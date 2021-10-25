package com.dimfcompany.nashprihodadmin.ui.act_note_show

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getLongExtraMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_note_add.ActNoteAddMvp
import com.dimfcompany.nashprihodadmin.ui.act_user_show.ActUserShow
import io.reactivex.subjects.BehaviorSubject

class ActNoteShow : BaseActivity()
{
    lateinit var mvp_view: ActNoteShowMvp.MvpView
    val bs_note_id_to_load: BehaviorSubject<Long> = BehaviorSubject.create()
    val bs_note: BehaviorSubject<ModelNote> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNoteShowMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        checkExtra()
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun setEvents()
    {
        bs_note_id_to_load
                .mainThreaded()
                .subscribe(
                    {
                        base_networker.getNoteById(it,
                            {
                                bs_note.onNext(it)
                            })
                    })
                .disposeBy(composite_disposable)

        bs_note
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNote(it)

                        if (it.status == TypeNoteStatus.WAITS_FOR_READING)
                        {
                            val note_id = it.id ?: return@subscribe
                            sendIsReadingStatus(note_id)
                        }
                    })
                .disposeBy(composite_disposable)
    }

    private fun sendIsReadingStatus(note_id: Long)
    {
        val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: return
        base_networker.changeNoteStatus(note_id, TypeNoteStatus.IS_READING, user_id,
            {
                bs_note.onNext(it)
            })
    }


    private fun sendReadedStatus(note_id: Long, status: TypeNoteStatus)
    {
        val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: return
        base_networker.changeNoteStatus(note_id, status, user_id,
            {
                bs_note.onNext(it)
            })
    }

    private fun checkExtra()
    {
        val note_id = intent.getLongExtraMy(Constants.Extras.NOTE_ID)
        bs_note_id_to_load.onNext(note_id!!)
    }

    inner class PresenterImplementer : ActNoteShowMvp.Presenter
    {
        override fun clickedReaded()
        {
            val note = bs_note.value ?: return
            val note_id = note.id ?: return
            if (note.for_health == true)
            {
                sendReadedStatus(note_id, TypeNoteStatus.READED)
            }
            else
            {
                if (note.status == TypeNoteStatus.READED)
                {
                    sendReadedStatus(note_id,TypeNoteStatus.READED2)
                }
                else
                {
                    sendReadedStatus(note_id,TypeNoteStatus.READED)
                }
            }
        }

        override fun clickedUser()
        {
            val user_id = bs_note.value?.user?.id ?: return

            BuilderIntent()
                    .setActivityToStart(ActUserShow::class.java)
                    .addParam(Constants.Extras.USER_ID, user_id)
                    .startActivity(this@ActNoteShow)
        }
    }
}