package com.mazaj.seller.ui.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.databinding.ActivitySplashBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.splash.viewModel.SplashViewModel

class SplashActivity : com.mazaj.seller.base.BaseActivity() {
    override val viewModel by lazy { ViewModelProvider(this)[SplashViewModel::class.java] }
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.navigateTo.observe(this, Observer { startActivity(Intent(this, viewModel.navigateTo.value?.className!!).newTask()) })
    }
}
