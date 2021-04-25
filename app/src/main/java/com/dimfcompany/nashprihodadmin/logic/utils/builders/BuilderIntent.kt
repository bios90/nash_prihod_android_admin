package com.dimfcompany.nashprihodadmin.logic.utils.builders

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.IntentCompat
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.dimfcompany.nashprihodadmin.base.enums.TypeActivityAnim
import com.dimfcompany.nashprihodadmin.base.extensions.myPutExtra
import com.github.florent37.inlineactivityresult.kotlin.coroutines.startForResult
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.request.RequestFabric
import java.lang.RuntimeException

class BuilderIntent()
{
    enum class TypeSlider
    {
        BOTTOM_UP
    }

    private var class_to_start: Class<out AppCompatActivity>? = null
    private var ok_lambda: ((Intent?) -> Unit)? = null
    private var cancel_lambda: ((Intent?) -> Unit)? = null
    private var params: ArrayList<Pair<String, Any?>> = ArrayList()
    private var flags: Int? = null
    private var on_start_action: (() -> Unit)? = null
    private var type_anim: TypeActivityAnim? = null
    private var slider: TypeSlider? = null
    private var transition_pairs: HashMap<View, String> = hashMapOf()

    fun setActivityToStart(act_class: Class<out AppCompatActivity>): BuilderIntent
    {
        class_to_start = act_class
        return this
    }

    fun setOkAction(action: (Intent?) -> Unit): BuilderIntent
    {
        ok_lambda = action
        return this
    }

    fun setCancelAction(action: (Intent?) -> Unit): BuilderIntent
    {
        cancel_lambda = action
        return this
    }

    fun addParam(param: Pair<String, Any?>): BuilderIntent
    {
        params.add(param)
        return this
    }


    fun addParam(name: String, obj: Any?): BuilderIntent
    {
        params.add(Pair(name, obj))
        return this
    }

    fun setFlags(flags: Int): BuilderIntent
    {
        this.flags = flags
        return this
    }

    fun addOnStartAction(action: () -> Unit): BuilderIntent
    {
        this.on_start_action = action
        return this
    }

    fun addActivityAnim(anim: TypeActivityAnim?): BuilderIntent
    {
        this.type_anim = anim
        return this
    }

    fun setSlider(slider: TypeSlider?): BuilderIntent
    {
        this.slider = slider
        return this
    }

    fun addTransition(view: View, name: String): BuilderIntent
    {
        this.transition_pairs.put(view, name)
        return this
    }

    fun startActivity(activity_from: AppCompatActivity)
    {
        if (class_to_start == null)
        {
            throw RuntimeException("***** Error no class to Start!!!! ****")
        }

        val intent = Intent(activity_from, class_to_start)

        params.forEach(
            { pair ->

                pair.second?.let(
                    { value ->
                        intent.myPutExtra(pair.first, value)
                    })
            })

        flags?.let(
            {
                intent.flags = it
            })

        var transition_options: Bundle? = null
        if (!transition_pairs.isNullOrEmpty())
        {
            val pairs = transition_pairs.map(
                {
                    return@map androidx.core.util.Pair(it.key, it.value)
                })
                    .toTypedArray()
            transition_options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity_from, *pairs).toBundle()
        }


        if (ok_lambda != null || cancel_lambda != null)
        {
            val request = RequestFabric.create(intent, transition_options)
            activity_from.startForResult(request)
            { result ->

                ok_lambda?.invoke(result.data)
            }.onFailed(
                { result ->

                    cancel_lambda?.invoke(result.data)
                })

        }
        else
        {
            activity_from.startActivity(intent, transition_options)
        }

        slider?.let(
            {
                when (it)
                {
                    TypeSlider.BOTTOM_UP -> Animatoo.animateSlideUp(activity_from)
                }
            })

        on_start_action?.let({ it.invoke() })
        type_anim?.let(
            {
                it.animateWithActivity(activity_from)
            })
    }
}