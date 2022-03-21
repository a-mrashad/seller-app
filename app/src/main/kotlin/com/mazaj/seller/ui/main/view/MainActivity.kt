package com.mazaj.seller.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.databinding.ActivityMainBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.main.viewModel.MainViewModel

class MainActivity : com.mazaj.seller.base.BaseActivity() {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.logout.setOnClickListener { viewModel.onLogoutClicked() }
    }

    private fun setObservers() {
        viewModel.onLogoutSucceeded.observe(this, Observer { startActivity(Intent(this, LoginActivity::class.java).newTask()) })
        viewModel.newOrdersLiveData.observe(this, Observer {

        })
        viewModel.acceptedOrdersLiveData.observe(this, Observer {

        })
        viewModel.readyOrdersLiveData.observe(this, Observer {

        })
    }
}
