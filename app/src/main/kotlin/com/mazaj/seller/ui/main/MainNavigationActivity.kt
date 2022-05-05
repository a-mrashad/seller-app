package com.mazaj.seller.ui.main

import android.os.Bundle
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityMainNavigationBinding
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData

class MainNavigationActivity : BaseActivity(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    val binding by lazy { ActivityMainNavigationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }
}
