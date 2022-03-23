package com.mazaj.seller.ui.orderDetails.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityOrderDetailsBinding
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel

class OrderDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }
    override val viewModel by lazy { ViewModelProvider(this)[OrderDetailsViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() = binding.apply {
        activityOrderDetailBack
    }

}