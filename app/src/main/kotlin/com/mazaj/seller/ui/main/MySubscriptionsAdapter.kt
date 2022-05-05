package com.mazaj.seller.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemSubscriptionsBinding
import com.mazaj.seller.repository.networking.models.MySubscriptionData
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.ACCEPTED_SUBSCRIPTION_STATUS
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity

class MySubscriptionsAdapter(
    private val items: MutableList<MySubscriptionData>,
    private val onRequestClicked: (Long, String) -> (Unit)
) : RecyclerView.Adapter<MySubscriptionsAdapter.MySubscriptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySubscriptionViewHolder =
        MySubscriptionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_subscriptions, parent, false))

    override fun onBindViewHolder(holder: MySubscriptionViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class MySubscriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSubscriptionsBinding.bind(itemView)
        fun bind(item: MySubscriptionData) {
            val subscription = item.subscriptions.find { it.isCurrent == true } ?: item.subscriptions.first()
            binding.tvOrderId.text = subscription.subscriptionNo
            binding.tvItemsCount.text = "${item.itemsCount} items"
            binding.tvPickupDate.text = subscription.deliveryAt?.toString("hh:mm a")
            binding.tvAccepted.visibility = if (item.status == ACCEPTED_SUBSCRIPTION_STATUS) View.VISIBLE else View.GONE
            binding.tvCompleted.visibility = if (item.status == ACCEPTED_SUBSCRIPTION_STATUS) View.GONE else View.VISIBLE
            binding.btnViewSubscriptionDetails.setOnClickListener { onRequestClicked(subscription.id, OrderDetailsActivity.SUBSCRIPTION_ID_KEY) }
        }
    }
}
