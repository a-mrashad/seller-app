package com.mazaj.seller.ui.subscriptionReminder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivitySubscriptionReminderBinding
import com.mazaj.seller.ui.shared.network.OnFetchingData

class SubscriptionReminderActivity : BaseActivity(), OnFetchingData {
    private val binding by lazy { ActivitySubscriptionReminderBinding.inflate(layoutInflater) }
    override val viewModel by lazy { ViewModelProvider(this)[SubscriptionReminderViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupOnFetchingData()
        fillData()
        setObservers()
    }

    private fun fillData() = binding.apply {
        tvOrdersCount.text = "${intent.getStringExtra("total_orders")} Orders"
        tvNotificationTime.text = intent.getStringExtra("reminder_duration")
    }

    private fun setObservers() {
        viewModel.subscriptionsLiveData.observe(this) {
            binding.rvReminders.layoutManager = LinearLayoutManager(this)
            binding.rvReminders.adapter = SubscriptionRemindersAdapter(it)
        }
    }
}
