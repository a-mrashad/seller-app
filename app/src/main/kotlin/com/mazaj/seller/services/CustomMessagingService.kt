package com.mazaj.seller.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mazaj.seller.utils.NotificationHelperUtils

class CustomMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        // ignore for now
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        if (data.isNullOrEmpty()) {
            val notificationParams = NotificationParams(title = remoteMessage.notification?.title, body = remoteMessage.notification?.body)
            val notificationUtil = NotificationHelperUtils(applicationContext)
            notificationUtil.buildNotificationWithIntent(notificationParams)
        } else {
            val notificationParams = NotificationParams(content = data["content"].orEmpty())
            val notificationUtil = NotificationHelperUtils(applicationContext)
            notificationUtil.buildNotificationWithIntent(notificationParams)
        }
    }
}

data class NotificationParams(val title: String? = null, val body: String? = null, val content: String? = "")
