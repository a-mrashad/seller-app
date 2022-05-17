package com.mazaj.seller.ui.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivitySplashBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.splash.viewModel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override val viewModel by lazy { ViewModelProvider(this)[SplashViewModel::class.java] }
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.navigateTo.observe(this) { startActivity(Intent(this, viewModel.navigateTo.value?.className!!).newTask()) }
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                repository.appPreferences.fcmToken = it.result
            }
            return@addOnCompleteListener
        }
    }
}
