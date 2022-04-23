package com.mazaj.seller.ui.orderDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemOrderItemsBinding
import com.mazaj.seller.repository.networking.models.*

class OrderItemsAdapter(private val items: MutableList<OrderItem>) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder =
        OrderItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_items, parent, false))

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class OrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOrderItemsBinding.bind(itemView)

        fun bind(order: OrderItem) {
            binding.apply {
                tvQuantity.text = "${order.quantity}x"
                tvItemName.text = order.additionalInformation?.enName
                // TODO handle order comment
                handleOrderOptions(order.options)
                handleAddonsView(order.addOns)
                handleVariantsView(order.variants)
            }
        }

        private fun handleOrderOptions(options: MutableList<OrderOptions>?) {
            if (options.isNullOrEmpty()) {
                binding.rvOptions.visibility = View.GONE
                return
            }
            binding.rvOptions.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = options.size
                adapter = OrderOptionsAdapter(options)
            }
            binding.rvOptions.visibility = View.VISIBLE
        }

        private fun handleAddonsView(addOns: MutableList<OrderAddOns>?) {
            if (addOns.isNullOrEmpty()) {
                binding.rvAddOns.visibility = View.GONE
                binding.tvAddOns.visibility = View.GONE
                return
            }
            binding.rvAddOns.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = addOns.size
                adapter = AddOnsOrderAdapter(addOns)
            }
            binding.tvAddOns.visibility = View.VISIBLE
            binding.rvAddOns.visibility = View.VISIBLE
        }

        private fun handleVariantsView(variants: MutableList<OrderVariants>?) {
            if (variants.isNullOrEmpty()) {
                binding.tvVariants.visibility = View.GONE
                binding.rvVariants.visibility = View.GONE
                return
            }
            binding.rvVariants.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = variants.size
                adapter = OrderBoxVariantsAdapter(variants)
            }
            binding.tvVariants.visibility = View.VISIBLE
            binding.rvVariants.visibility = View.VISIBLE
        }
    }
}

class SubscriptionOrderItemsAdapter(
    private val items: MutableList<SubscriptionOrder>
) : RecyclerView.Adapter<SubscriptionOrderItemsAdapter.SubscriptionOrderItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionOrderItemsViewHolder =
        SubscriptionOrderItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_items, parent, false))

    override fun onBindViewHolder(holder: SubscriptionOrderItemsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class SubscriptionOrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOrderItemsBinding.bind(itemView)

        fun bind(order: SubscriptionOrder) {
            binding.apply {
                tvQuantity.text = "${order.quantity}x"
                tvItemName.text = order.title
                // TODO handle order comment
                handleOrderOptions(order.options)
                handleAddonsView(order.addOns)
                handleVariantsView(order.variants)
            }
        }

        private fun handleOrderOptions(options: MutableList<OrderOptions>?) {
            if (options.isNullOrEmpty()) {
                binding.rvOptions.visibility = View.GONE
                return
            }
            binding.rvOptions.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = options.size
                adapter = OrderOptionsAdapter(options)
            }
            binding.rvOptions.visibility = View.VISIBLE
        }

        private fun handleAddonsView(addOns: MutableList<OrderAddOns>?) {
            if (addOns.isNullOrEmpty()) {
                binding.rvAddOns.visibility = View.GONE
                binding.tvAddOns.visibility = View.GONE
                return
            }
            binding.rvAddOns.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = addOns.size
                adapter = AddOnsOrderAdapter(addOns)
            }
            binding.tvAddOns.visibility = View.VISIBLE
            binding.rvAddOns.visibility = View.VISIBLE
        }

        private fun handleVariantsView(variants: MutableList<OrderVariants>?) {
            if (variants.isNullOrEmpty()) {
                binding.tvVariants.visibility = View.GONE
                binding.rvVariants.visibility = View.GONE
                return
            }
            binding.rvVariants.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = variants.size
                adapter = OrderBoxVariantsAdapter(variants)
            }
            binding.tvVariants.visibility = View.VISIBLE
            binding.rvVariants.visibility = View.VISIBLE
        }
    }
}
