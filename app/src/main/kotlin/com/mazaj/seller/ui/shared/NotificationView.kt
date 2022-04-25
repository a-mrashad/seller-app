package com.mazaj.seller.ui.shared

import android.content.Intent
import android.view.View
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseView
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.databinding.CustomNotificationBinding
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.ID_KEY
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.SUBSCRIPTION_ID_KEY
import com.view.circulartimerview.CircularTimerListener
import com.view.circulartimerview.TimeFormatEnum
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import org.joda.time.DateTime

interface NotificationView : BaseView {

    val viewModel: BaseViewModel
    val customNotificationView: View?
        get() = activity.findViewById(R.id.customNotification)

    fun setupNotificationView(order: Order?) {
        customNotificationView?.let {
            val notificationBinding = CustomNotificationBinding.bind(it)
            setData(notificationBinding, order!!)
            showNotificationView()
        }
    }

    fun setData(notificationBinding: CustomNotificationBinding?, order: Order) {
        notificationBinding?.apply {
            tvItemsCount.text = "${order.itemsCount} items"
            tvTitle.text = order.orderNumber
            if (order.type == 2) {
                tvOrderType.visibility = View.GONE
                tvSubscriptionType.visibility = View.VISIBLE
            } else {
                tvOrderType.visibility = View.VISIBLE
                tvSubscriptionType.visibility = View.GONE
            }
            val timeToFinish = order.timeToAutoDecline?.millis?.minus(DateTime.now().millis) ?: 0L
            val date = order.dateString?.split(" ")?.getOrNull(0)
            tvOrderDateDay.text = date?.let { DateTime.parse(it).toString("EEEE") }
            val timeFormatted = SimpleDateFormat("hh:mm:ss", Locale.US).parse(order.dateString?.split(" ")?.getOrNull(1) ?: "")
            val time = timeFormatted?.let { SimpleDateFormat("hh:mm a", Locale.US).format(it) }.toString()
            tvOrderDateTime.text = time
            progressCircular.progress = 0f
            progressCircular.setCircularTimerListener(object : CircularTimerListener {
                override fun updateDataOnTick(remainingTimeInMs: Long): String {
                    return String.format(
                        "%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTimeInMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs))
                    )
                }

                override fun onTimerFinished() {
                    progressCircular.setText("0:00")
                    hideNotificationView()
                }
            }, timeToFinish, TimeFormatEnum.MILLIS)

            progressCircular.startTimer()

            btnViewOrderDetail.setOnClickListener {
                hideNotificationView()
                activity.startActivity(Intent(activity.applicationContext, OrderDetailsActivity::class.java).apply {
                    putExtra(if (order.type == 1) ID_KEY else SUBSCRIPTION_ID_KEY, order.id)
                })
            }
        }
    }

    fun onNotificationStarted() {}

    fun onNotificationEnded() {}

    private fun showNotificationView() = activity.apply {
        customNotificationView?.visibility = View.VISIBLE
        onNotificationStarted()
    }

    private fun hideNotificationView() = activity.apply {
        customNotificationView?.visibility = View.GONE
        onNotificationEnded()
    }
}
