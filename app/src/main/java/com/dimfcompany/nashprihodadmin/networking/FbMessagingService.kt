package com.dimfcompany.nashprihodadmin.networking

import android.util.Log
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class FbMessagingService : FirebaseMessagingService()
{
    override fun onNewToken(new_token: String)
    {
        super.onNewToken(new_token)
        Log.e("FbMessagingService", "Got new token $new_token")
        SharedPrefsManager.pref_fb_token.asConsumer().accept(new_token.asOptional())
        subscribeToTopic()
    }

    private fun subscribeToTopic()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("ak_news_admins")
                .addOnSuccessListener(
                    {
                    })
    }
}