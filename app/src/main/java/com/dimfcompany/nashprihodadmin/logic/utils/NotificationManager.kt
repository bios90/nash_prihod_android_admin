package com.dimfcompany.nashprihodadmin.logic.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.AppClass
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.ui.ActDummyPushes
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.google.firebase.messaging.RemoteMessage
import java.io.Serializable

enum class TypePush
{
    NEW_USER_REGISTER;
}

class MyPush(val type: TypePush,
             val new_user_id: Long? = null,
             val new_user_name: String? = null)
    : Serializable
{
    companion object
    {
        fun initFromRemoteMessage(remote: RemoteMessage): MyPush?
        {
            val type_str = remote.getString("type") ?: return null
            val type = initEnumFromString<TypePush>(type_str) ?: return null
            val new_registered_user_id = remote.getLong("user_id")
            val new_registered_user_full_name = remote.getString("user_full_name")

            return MyPush(type, new_registered_user_id, new_registered_user_full_name)
        }
    }

    fun getTitle(): String
    {
        return when (type)
        {
            TypePush.NEW_USER_REGISTER -> "Новый пользователь"
        }
    }

    fun getText(): String
    {
        return when (type)
        {
            TypePush.NEW_USER_REGISTER -> "${new_user_name} ожидает одобрения"
        }
    }
}

class NotificationManager
{
    companion object
    {
        fun notify(remoteMessage: RemoteMessage)
        {
            val my_push = MyPush.initFromRemoteMessage(remoteMessage) ?: return
            if (SharedPrefsManager.pref_current_user.get().value == null)
            {
                return
            }

            val notification = getNotification(my_push)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                showHighNotification(notification)
            }
            else
            {
                showLowNotification(notification)
            }
        }

        fun getNotification(my_push: MyPush): Notification
        {
            val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val builder = NotificationCompat.Builder(AppClass.app, getStringMy(R.string.notify_chanel_nash_prihod))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(AppClass.app.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(my_push.getTitle())
                    .setContentText(my_push.getText())
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(getIntentFromPush(my_push))
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setSound(sound)

            return builder.build()
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun showHighNotification(notification: Notification)
        {
            val notificationChannel = NotificationChannel(getStringMy(R.string.notify_chanel_nash_prihod), getStringMy(R.string.notify_chanel_nash_prihod), NotificationManager.IMPORTANCE_HIGH)

            val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val att = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

            notificationChannel.description = getStringMy(R.string.notify_chanel_nash_prihod)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(sound, att)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(false)

            val notificationManager = AppClass.app.getSystemService(android.app.NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
            notificationManager?.notify((0..999).random(), notification)
        }

        private fun showLowNotification(notification: Notification)
        {
            val notificationManagerCompat = NotificationManagerCompat.from(AppClass.app)
            notificationManagerCompat.notify((0..999).random(), notification)
        }

        fun getIntentFromPush(my_push: MyPush): PendingIntent
        {
            val intent = Intent(AppClass.app, ActDummyPushes::class.java)
            intent.putExtra(Constants.Extras.MY_PUSH, my_push)

            val pendingIntent = PendingIntent.getActivity(AppClass.app, (0..999).random(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }
    }
}