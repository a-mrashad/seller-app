package com.mazaj.seller.ui.orderDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemBoxVariantBinding
import com.mazaj.seller.repository.networking.models.OrderVariants

class OrderBoxVariantsAdapter(private val items: MutableList<OrderVariants>) : RecyclerView.Adapter<OrderBoxVariantsAdapter.OrderVariantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVariantsViewHolder =
        OrderVariantsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_box_variant, parent, false))

    override fun onBindViewHolder(holder: OrderVariantsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class OrderVariantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBoxVariantBinding.bind(itemView)

        fun bind(orderVariants: OrderVariants) {
            binding.apply {
                val quantity = orderVariants.quantity ?: orderVariants.details?.quantity
                val name = orderVariants.name ?: orderVariants.details?.name
                itemBoxVariantQuantity.text = "${quantity}x"
                itemBoxVariantName.text = name
            }
        }
    }
}
