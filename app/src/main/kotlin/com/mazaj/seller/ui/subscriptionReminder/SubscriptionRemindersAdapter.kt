package com.mazaj.seller.ui.subscriptionReminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemSubscriptionReminderBinding
import com.mazaj.seller.repository.networking.models.SubscriptionReminder

class SubscriptionRemindersAdapter(
    private val reminders: List<SubscriptionReminder>
) : RecyclerView.Adapter<SubscriptionRemindersAdapter.ReminderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionRemindersAdapter.ReminderViewHolder =
        ReminderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_subscription_reminder, parent, false))

    override fun onBindViewHolder(holder: SubscriptionRemindersAdapter.ReminderViewHolder, position: Int) = holder.bind(reminders[position])

    override fun getItemCount() = reminders.size

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSubscriptionReminderBinding.bind(itemView)

        fun bind(reminder: SubscriptionReminder) {
            binding.apply {
                tvItemsNum.text = reminder.itemsNumber.toString()
                tvOrderNum.text = reminder.orderNumber
                tvOrderPickupTime.text = reminder.pickupTime
            }
        }
    }
}
