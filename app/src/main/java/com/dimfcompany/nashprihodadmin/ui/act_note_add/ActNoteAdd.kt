package com.dimfcompany.nashprihodadmin.ui.act_note_add

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.getRandomString
import com.dimfcompany.nashprihodadmin.ui.ActPayment
import io.reactivex.subjects.BehaviorSubject

class ActNoteAdd : BaseActivity()
{
    lateinit var mvp_view: ActNoteAddMvp.MvpView
    val bs_price: BehaviorSubject<Int> = BehaviorSubject.createDefault(100)
    val prices = arrayListOf(0, 50, 100, 150, 200, 250, 300, 500, 750, 1000)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNoteAddMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
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
        bs_price
                .mainThreaded()
                .subscribe(
                    {
                        val price_text = "$it р."
                        mvp_view.bindPriceText(price_text)
                    })
                .disposeBy(composite_disposable)
    }

    private fun createNote(names: ArrayList<String>, is_for_health: Boolean, donation_sum: Int?, donation_id: String?)
    {
        val note = ModelNote()
        note.names = names.joinToString("*")
        note.for_health = is_for_health
        note.donation_sum = donation_sum?.toDouble()
        note.donation_id = donation_id

        base_networker.insertNote(note,
            {
                finish()
                BusMainEvents.ps_note_inserted.onNext(it)
            })
    }

    inner class PresenterImplementer : ActNoteAddMvp.Presenter
    {
        override fun clickedSave()
        {
            val names = mvp_view.getEtNamesText()
                    ?.removeRepeatingSpaces()
                    ?.split(" ")
                    ?.filter({ it.length > 2 })
                    ?.toCollection(ArrayList())
            val is_for_health = mvp_view.getForHealthPos() == 0

            val data = ValidationManager.validateNoteAdd(names)
            if (!data.is_valid)
            {
                data.showErrors(this@ActNoteAdd)
                return
            }

            val sum = bs_price.value ?: return


            if (sum > 0)
            {
                val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: "1"
                val donation_id = "usr${user_id}_" + String.getRandomString(16)

                BuilderIntent()
                        .setActivityToStart(ActPayment::class.java)
                        .addParam(Constants.Extras.DONATION_SUM, sum)
                        .addParam(Constants.Extras.DONATION_ID, donation_id)
                        .setOkAction(
                            {
                                createNote(names!!, is_for_health, sum, donation_id)
                            })
                        .startActivity(this@ActNoteAdd)
            }
            else
            {
                createNote(names!!, is_for_health, null, null)
            }
        }

        override fun priceSelected(pos: Int)
        {
            val price = prices.getOrNull(pos) ?: return
            bs_price.onNext(price)
        }
    }
}