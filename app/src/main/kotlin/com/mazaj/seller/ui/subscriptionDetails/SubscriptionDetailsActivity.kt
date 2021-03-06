package com.mazaj.seller.ui.subscriptionDetails

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.Constants
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivitySubscriptionDetailsBinding
import com.mazaj.seller.extensions.showDeclineReasonDialog
import com.mazaj.seller.extensions.toHoursOrMinutes
import com.mazaj.seller.repository.networking.models.SubscriptionsDetailsResponse
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData
import com.view.circulartimerview.CircularTimerListener
import com.view.circulartimerview.TimeFormatEnum
import java.util.concurrent.TimeUnit
import org.joda.time.DateTime

class SubscriptionDetailsActivity : BaseActivity(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[OrderDetailsViewModel::class.java] }
    private val binding by lazy { ActivitySubscriptionDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupOnFetchingData()
        binding.viewPager.adapter = PageAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        setListeners()
        setObservers()
    }

    private fun setListeners() = binding.apply {
        icBack.setOnClickListener { onBackPressed() }
        tvDecline.setOnClickListener { activity.showDeclineReasonDialog { viewModel.declineOrder(it) } }
    }

    private fun setObservers() {
        viewModel.subscriptionOrderDetailsLiveData.observe(this) { setHeaderDetails(it) }
    }

    private fun setHeaderDetails(details: SubscriptionsDetailsResponse?) = binding.apply {
        tvOrderNumber.text = details?.orderNumber ?: ""
        tvTypeSubscription.visibility = View.VISIBLE
        val orderPickupRemainingMinutes = details?.deliveryAt?.minus(DateTime.now().millis)?.millis?.toHoursOrMinutes()
        val spannablePickupIn = SpannableString("Pickup in | ")
        spannablePickupIn.setSpan(
            TextAppearanceSpan(this@SubscriptionDetailsActivity, R.style.h2_14), 0, spannablePickupIn.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val spannableRemainingTime = SpannableString("$orderPickupRemainingMinutes ${details?.deliveryAt?.toString(OrderDetailsActivity.DTF)}")
        spannableRemainingTime.setSpan(StyleSpan(Typeface.BOLD), 0, spannableRemainingTime.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableRemainingTime.setSpan(
            TextAppearanceSpan(this@SubscriptionDetailsActivity, R.style.h2_14),
            0, spannableRemainingTime.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val builder = SpannableStringBuilder().apply {
            append(spannablePickupIn)
            append(spannableRemainingTime)
        }
        tvOrderDate.text = builder
        if (details?.deliveryAt?.minus(DateTime.now().millis)?.millis ?: 0 < Constants.MINUTE) {
            tvOrderDate.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@SubscriptionDetailsActivity, R.color.light_red))
            tvOrderDate.setTextColor(ContextCompat.getColor(this@SubscriptionDetailsActivity, R.color.white))
        }
        tabLayout.getTabAt(0)?.text = "No. of items - ${details?.itemsCount} items"
        tabLayout.getTabAt(1)?.text = "No. of days - ${details?.subscriptions?.size} days"
        handleSubscriptionButton(details)
        handleTimer(details?.timeToAutoDecline?.millis?.minus(DateTime.now().millis) ?: 2 * Constants.MINUTE)
    }

    private fun handleTimer(timeToFinish: Long) = binding.apply {
        timerView.progress = 0f
        timerView.setCircularTimerListener(object : CircularTimerListener {
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return String.format(
                    "%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTimeInMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs))
                )
            }

            override fun onTimerFinished() {
                timerView.setText("0:00")
            }
        }, timeToFinish, TimeFormatEnum.MILLIS)

        timerView.startTimer()
    }

    private fun handleSubscriptionButton(order: SubscriptionsDetailsResponse?) {
        binding.tvDecline.visibility = if (order?.isAccepted != true) View.VISIBLE else View.GONE
        binding.timerView.visibility = if (order?.isAccepted != true) View.VISIBLE else View.GONE
    }

    override fun onNotificationStarted() {
        binding.customNotification.customNotification.visibility = View.VISIBLE
        binding.layout.visibility = View.GONE
    }

    override fun onNotificationEnded() {
        binding.customNotification.customNotification.visibility = View.GONE
        binding.layout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (binding.layout.visibility == View.VISIBLE) super.onBackPressed()
    }
}

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int) = if (position == 0) SubscriptionDetailsFragment() else SubscriptionDaysFragment()

    override fun getPageTitle(position: Int) = ""
}
