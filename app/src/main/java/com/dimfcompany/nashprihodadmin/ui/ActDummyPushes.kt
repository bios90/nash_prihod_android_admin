package com.dimfcompany.nashprihodadmin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.makeClearAllPrevious
import com.dimfcompany.nashprihodadmin.logic.utils.MyPush
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirst
import java.lang.RuntimeException

class ActDummyPushes : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val push_extra = intent.getSerializableExtra(Constants.Extras.MY_PUSH) as? MyPush
        if (push_extra == null)
        {
            throw RuntimeException("*** Error no push extra passed ***")
        }

        BusMainEvents.bs_push_clicked.onNext(push_extra)

        if (isTaskRoot)
        {
            val intent = Intent(this, ActFirst::class.java)
            intent.makeClearAllPrevious()
            startActivity(intent)
        }
        else
        {
            finish()
        }
    }
}