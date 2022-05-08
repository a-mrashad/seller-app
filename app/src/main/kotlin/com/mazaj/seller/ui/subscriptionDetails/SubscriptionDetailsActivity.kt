package com.mazaj.seller.ui.subscriptionDetails

import android.content.res.ColorStateList
import android.os.Bundle
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
import com.mazaj.seller.repository.networking.models.SubscriptionsDetailsResponse
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData
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
        val deliveryJob = details?.subscriptions?.filter {
            it.isCurrent == true
        }?.getOrNull(0) ?: details?.subscriptions?.getOrNull(0) ?: return this
        tvOrderNumber.text = deliveryJob.subscriptionNo
        tvTypeSubscription.visibility = View.VISIBLE
        val orderPickupDate = deliveryJob.deliveryAt?.minus(DateTime.now().millis)?.millis?.div(Constants.MINUTE)
        val orderPickupRemainingMinutes = if (orderPickupDate ?: -1 < 0) 0 else orderPickupDate
        tvOrderDate.text =
            StringBuilder().append("Pickup in $orderPickupRemainingMinutes ")
                .append(getString(R.string.minute))
                .append(OrderDetailsActivity.PIPE)
                .append(deliveryJob.deliveryAt?.toString(OrderDetailsActivity.DTF))
                .toString()
        if (deliveryJob.deliveryAt?.minus(DateTime.now().millis)?.millis ?: 0 < Constants.MINUTE.toLong()) {
            tvOrderDate.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@SubscriptionDetailsActivity, R.color.light_red))
            tvOrderDate.setTextColor(ContextCompat.getColor(this@SubscriptionDetailsActivity, R.color.white))
        }
        tabLayout.getTabAt(0)?.text = "No. of items ${details?.itemsCount} items"
        tabLayout.getTabAt(1)?.text = "No. of days ${details?.subscriptions?.size} days"
        handleSubscriptionButton(details)
    }

    private fun handleSubscriptionButton(order: SubscriptionsDetailsResponse?) {
        binding.tvDecline.visibility = if (order?.isAccepted != true) View.VISIBLE else View.GONE
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
