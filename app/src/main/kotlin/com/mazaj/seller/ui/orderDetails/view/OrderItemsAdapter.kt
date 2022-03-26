package com.mazaj.seller.ui.orderDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemOrderItemsBinding
import com.mazaj.seller.repository.networking.models.OrderAddOns
import com.mazaj.seller.repository.networking.models.OrderItem
import com.mazaj.seller.repository.networking.models.OrderVariants

class OrderItemsAdapter(private val items: MutableList<OrderItem>) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>() {
    private lateinit var addOnsOrderAdapter: AddOnsOrderAdapter
    private lateinit var orderBoxVariantsAdapter: OrderBoxVariantsAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder =
        OrderItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_items, parent, false))

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class OrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOrderItemsBinding.bind(itemView)

        fun bind(order: OrderItem) {
            binding.apply {
                itemOrderItemsQuantity.text = "X ${order.quantity}"
                itemOrderItemsName.text = order.additionalInformation?.enName
                // TODO handle order comment
                // TODO handle options
                handleAddonsView(order.addOns)
                handleVariantsView(order.variants)
            }
        }

        private fun handleAddonsView(addOns: MutableList<OrderAddOns>?) {
            if (addOns.isNullOrEmpty()) {
                binding.rvAddOns.visibility = View.GONE
                binding.tvAddOns.visibility = View.GONE
                return
            }

            addOnsOrderAdapter = AddOnsOrderAdapter(addOns)
            binding.rvAddOns.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = addOns.size
                adapter = addOnsOrderAdapter
            }
            binding.rvAddOns.visibility = View.VISIBLE
        }

        private fun handleVariantsView(variants: MutableList<OrderVariants>?) {
            if (variants.isNullOrEmpty()) {
                binding.rvVariants.visibility = View.GONE
                return
            }
            orderBoxVariantsAdapter = OrderBoxVariantsAdapter(variants)
            binding.rvVariants.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                (layoutManager as LinearLayoutManager).initialPrefetchItemCount = variants.size
                adapter = orderBoxVariantsAdapter
            }
            binding.rvVariants.visibility = View.VISIBLE
        }
    }
}
