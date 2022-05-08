package com.mazaj.seller.ui.subscriptionDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemDayBinding
import com.mazaj.seller.repository.networking.models.SubscriptionsJobDetails

class SubscriptionDaysAdapter(private val days: List<SubscriptionsJobDetails>) : RecyclerView.Adapter<SubscriptionDaysAdapter.DaysViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionDaysAdapter.DaysViewHolder =
        DaysViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false))

    override fun onBindViewHolder(holder: SubscriptionDaysAdapter.DaysViewHolder, position: Int) = holder.bind(days[position])

    override fun getItemCount() = days.size

    inner class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDayBinding.bind(itemView)

        fun bind(job: SubscriptionsJobDetails) {
            binding.tvDate.text = job.deliveryAt?.toString("EE MMMM dd, YYYY")
            binding.ivCheck.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    if (job.isDelivered) R.drawable.ic_baseline_green_check_circle_24 else R.drawable.ic_baseline_grey_check_circle_24
                )
            )
        }
    }
}
