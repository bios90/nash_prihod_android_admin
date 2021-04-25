package com.dimfcompany.nashprihodadmin.networking

import android.util.Log
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.utils.NotificationManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FbMessagingService : FirebaseMessagingService()
{
    override fun onNewToken(new_token: String)
    {
        super.onNewToken(new_token)
        Log.e("FbMessagingService", "Got new token $new_token")
        SharedPrefsManager.pref_fb_token.asConsumer().accept(new_token.asOptional())
        subscribeToTopic()
    }

    override fun onMessageReceived(p0: RemoteMessage)
    {
        super.onMessageReceived(p0)
        NotificationManager.notify(p0)
    }

    private fun subscribeToTopic()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("nash_prihod_admins")
                .addOnSuccessListener(
                    {
                        Log.e("FbMessagingService", "subscribeToTopic: Success register on topic")
                    })
    }
}