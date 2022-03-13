package com.mazaj.seller.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.databinding.ActivityIntroBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.repository.preferences.AppPreferences
import com.mazaj.seller.ui.shared.TargetActivity

class IntroActivity : BaseActivity() {
    override val viewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }
    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener {
            AppPreferences.isFirstTime = false
            startActivity(Intent(this, TargetActivity.LOGIN.className).newTask())
        }
    }
}
