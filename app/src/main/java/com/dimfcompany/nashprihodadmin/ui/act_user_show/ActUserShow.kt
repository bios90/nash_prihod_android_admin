package com.dimfcompany.nashprihodadmin.ui.act_user_show

import android.os.Bundle
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.akcsl.base.LoadBehavior
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.extensions.Optional
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullScreen
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShow
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEdit
import com.dimfcompany.nashprihodadmin.ui.act_user_edit.ActUserEditMvp
import io.reactivex.subjects.BehaviorSubject
import java.lang.RuntimeException
import java.util.*

class ActUserShow : BaseActivity()
{
    lateinit var mvp_view: ActUserShowMvp.MvpView
    val bs_user_to_edit: BehaviorSubject<ModelUser> = BehaviorSubject.create()
    val bs_user_notes: BehaviorSubject<FeedDisplayInfo<ModelNote>> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActUserShowMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        loadUser()
        loadUserNotes()
    }

    private fun loadUser()
    {
        base_networker.getUserById(getUserIdExtra(),
            {
                bs_user_to_edit.onNext(it)
            })
    }

    private fun loadUserNotes()
    {
        base_networker.getNotes(null, getUserIdExtra(), null,
            {
                val info = FeedDisplayInfo(it, LoadBehavior.UPDATE)
                bs_user_notes.onNext(info)
            })
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
        bs_user_notes
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNotes(it)
                    })
                .disposeBy(composite_disposable)

        bs_user_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindUser(it)
                        mvp_view.hideOverlay()
                    })
                .disposeBy(composite_disposable)

        BusMainEvents.ps_user_updated
                .mainThreaded()
                .subscribe(
                    {
                        if (it == getUserIdExtra())
                        {
                            loadUser()
                        }
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : ActUserShowMvp.Presenter
    {
        override fun clickedEdit()
        {
            val user_id = bs_user_to_edit.value?.id ?: return

            BuilderIntent()
                    .setActivityToStart(ActUserEdit::class.java)
                    .addParam(Constants.Extras.USER_ID, user_id)
                    .startActivity(this@ActUserShow)
        }

        override fun clickedChangeStatus(status: TypeUserStatus)
        {
            val user_id = bs_user_to_edit.value?.id ?: return
            base_networker.changeUserStatus(user_id, status,
                {
                    bs_user_to_edit.onNext(it)
                })
        }

        override fun clickedNote(note: ModelNote)
        {
            val note_id = note.id ?: return
            BuilderIntent()
                    .setActivityToStart(ActNoteShow::class.java)
                    .addParam(Constants.Extras.NOTE_ID, note_id)
                    .startActivity(this@ActUserShow)
        }

        override fun clickedAvatar()
        {
            val media = bs_user_to_edit.value?.avatar ?: return
            val objs_wrapper = MediaItemsWrapper(arrayListOf(media))

            BuilderIntent()
                    .setActivityToStart(ActCarouselFullScreen::class.java)
                    .addParam(Constants.Extras.MEDIA_OBJECTS, objs_wrapper)
                    .addParam(Constants.Extras.MEDIA_OBJECTS_START_POS, 0)
                    .startActivity(this@ActUserShow)
        }
    }


    private fun getUserIdExtra(): Long
    {
        val user_id = intent.getLongExtraMy(Constants.Extras.USER_ID)
        if (user_id == null)
        {
            throw RuntimeException("*** Error no user_id ***")
        }

        return user_id
    }
}