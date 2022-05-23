package com.mazaj.seller.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityMainNavigationBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.intro.IntroActivity
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
        binding.tvLogout.setOnClickListener { viewModel.onLogoutClicked() }
        viewModel.onLogoutSucceeded.observe(this) { startActivity(Intent(this, IntroActivity::class.java).newTask()) }
    }

    override fun onNotificationStarted() {
        binding.customNotification.customNotification.visibility = View.VISIBLE
        binding.drawerLayout.visibility = View.GONE
    }

    override fun onNotificationEnded() {
        binding.customNotification.customNotification.visibility = View.GONE
        binding.drawerLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.visibility == View.VISIBLE) super.onBackPressed()
    }
}
