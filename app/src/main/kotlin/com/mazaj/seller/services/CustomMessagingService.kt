package com.mazaj.seller.services

import android.content.Intent
import android.media.MediaPlayer
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mazaj.seller.Constants.NOTIFICATION_EVENT_NAME
import com.mazaj.seller.Constants.NOTIFICATION_ORDER_KEY
import com.mazaj.seller.R
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.repository.preferences.AppPreferences
import com.mazaj.seller.utils.AppState
import com.mazaj.seller.utils.NotificationHelperUtils
import org.joda.time.DateTime

class CustomMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        AppPreferences.fcmToken = token
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.notification_sound)
        mediaPlayer.start()
        val data: Map<String, String> = remoteMessage.data
        if (data.isNullOrEmpty()) {
            handleNullDataNotification(remoteMessage)
            return
        }
        if (AppState.isAppOnForeground(baseContext)) {
            handleInAppNotification(remoteMessage.data)
            return
        }
        val notificationParams = NotificationParams(content = data["content"].orEmpty())
        val notificationUtil = NotificationHelperUtils(applicationContext)
        notificationUtil.buildNotificationWithIntent(notificationParams)
    }

    private fun handleNullDataNotification(remoteMessage: RemoteMessage) {
        val notificationParams = NotificationParams(
            title = remoteMessage.notification?.title,
            body = remoteMessage.notification?.body
        )
        val notificationUtil = NotificationHelperUtils(applicationContext)
        notificationUtil.buildNotificationWithIntent(notificationParams)
        return
    }

    private fun handleInAppNotification(data: MutableMap<String, String>) {
        if (AppPreferences.token == null) return
        val orderId: Long? = data["id"]?.toLong()
        val orderNumber: String? = data["order_number"]
         val type: Int? = data["type"]?.toInt()
        val itemsCount: Int? = data["items_count"]?.toInt()
        // val deliveryType: Int? = data["delivery_type"]?.toInt()
        val deliveryAt: String? = data["delivery_at"]
        val timeToAutoDecline: String? = data["time_to_auto_decline"]
        val order = Order(
            id = orderId ?: 0L,
            orderId = orderId,
            orderNumber = orderNumber ?: "",
            type = type ?: 1,
            itemsCount = itemsCount ?: 0,
            deliveryAt = DateTime.now(),
            dateString = deliveryAt,
            timeToAutoDecline = DateTime.parse(timeToAutoDecline)
        )
        showNotificationView(order)
    }

    private fun showNotificationView(orderJson: Order) {
        val eventIntent = Intent(NOTIFICATION_EVENT_NAME).apply {
            putExtra(NOTIFICATION_ORDER_KEY, orderJson)
        }
        this.sendBroadcast(eventIntent)
    }
}

data class NotificationParams(val title: String? = null, val body: String? = null, val content: String? = "")
