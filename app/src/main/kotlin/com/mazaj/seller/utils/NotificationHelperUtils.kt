package com.mazaj.seller.utils

import com.mazaj.seller.BuildConfig
import com.mazaj.seller.R
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.main.view.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.mazaj.seller.extensions.toRGB
import com.mazaj.seller.repository.preferences.AppPreferences
import com.mazaj.seller.services.NotificationParams
import kotlin.time.seconds

class NotificationHelperUtils(context: Context?) : ContextWrapper(context) {
    private var notificationManager: NotificationManager? = null

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, showBadge: Boolean = true, importance: Int = IMPORTANCE_HIGH) {
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            setDefaultConfig()
            setShowBadge(showBadge)
        }
        getNotificationManager()!!.createNotificationChannel(channel)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun NotificationChannel.setDefaultConfig() {
        enableLights(true)
        enableVibration(true)
        lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        lightColor = Color.parseColor("#ebb84b")
        vibrationPattern = VIBRATION_PATTERN
    }

    private fun getNotificationManager(): NotificationManager? {
        if (notificationManager == null) { notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
        return notificationManager
    }

    fun buildNotificationWithIntent(notificationParams: NotificationParams) {
        val intents = buildIntents(notificationParams)
        val requestCode = System.currentTimeMillis().toInt()

        val notificationBuilder = NotificationCompat.Builder(this, MODULE_CHANNEL_ID)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setTicker(getString(R.string.app_name))
            .setContentTitle(getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(notificationParams.body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationParams.title))
            .setAutoCancel(true)
            .setContentIntent(PendingIntent.getActivities(this, requestCode, intents, PendingIntent.FLAG_IMMUTABLE))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(PRIORITY_MAX)
            .setWhen(System.currentTimeMillis())
            .setVibrate(VIBRATION_PATTERN)
            .setLights(Color.BLUE, ON_OFF_LIGHTS.toLongMilliseconds().toInt(), ON_OFF_LIGHTS.toLongMilliseconds().toInt())
            .setNumber(1)
            .setColor(R.color.primaryColor.toRGB(applicationContext))
            .setGroup(GROUP_KEY)

        val notificationId = System.currentTimeMillis().toInt()
        val notification = notificationBuilder.build()

        getNotificationManager()?.notify(notificationId, notification)
        displayNotificationGroup(intents, requestCode)
    }

    private fun buildIntents(params: NotificationParams): Array<Intent?> {
        params.content
        val intentList = mutableListOf(Intent(applicationContext, MainActivity::class.java).newTask())
        return intentList.toTypedArray()
    }

    private fun displayNotificationGroup(intents: Array<Intent?>, requestCode: Int) {
        val notificationBuilder = NotificationCompat.Builder(this, MODULE_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setGroup(GROUP_KEY)
            .setGroupSummary(true)
            .setPriority(PRIORITY_MAX)
            .setAutoCancel(false)
        setContentIntentForNotificationGroup(notificationBuilder, intents, requestCode)
        notificationManager!!.notify(Int.MAX_VALUE, notificationBuilder.build())
    }

    private fun setContentIntentForNotificationGroup(notificationBuilder: NotificationCompat.Builder, intents: Array<Intent?>, requestCode: Int) {
        val pendingIntent = PendingIntent.getActivities(this, requestCode, intents, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT )
        notificationBuilder.setContentIntent(pendingIntent)
    }

    companion object {
        private const val MODULE_CHANNEL_ID = "Mazaj-Seller"
        const val UPLOADING_CHANNEL_ID = "uploading_channel_${BuildConfig.APPLICATION_ID}"
        private const val GROUP_KEY = "notification_group"
        private val VIBRATION_PATTERN = longArrayOf(500, 500, 500, 500, 500)
        private val ON_OFF_LIGHTS = 3.seconds
    }

    init {
        AppPreferences.setup(context!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelName = BuildConfig.APPLICATION_ID, channelId = MODULE_CHANNEL_ID)
            createNotificationChannel(channelName = UPLOADING_CHANNEL_ID, channelId = UPLOADING_CHANNEL_ID, importance = IMPORTANCE_LOW)
        }
    }
}
