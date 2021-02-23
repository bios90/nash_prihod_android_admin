package com.dimfcompany.nashprihodadmin.base.enums

import androidx.appcompat.app.AppCompatActivity
import com.dimfcompany.nashprihodadmin.R

enum class TypeActivityAnim
{
    FADE;

    fun animateWithActivity(activity: AppCompatActivity)
    {
        when (this)
        {
            FADE ->
            {
                activity.overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in)
            }
        }
    }
}