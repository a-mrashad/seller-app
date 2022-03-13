package com.mazaj.seller.ui.resetPassword.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityResetPasswordBinding
import com.mazaj.seller.ui.resetPassword.viewModel.ResetPasswordViewModel

class ResetPasswordActivity : BaseActivity() {
    override val viewModel by lazy { ViewModelProvider(this)[ResetPasswordViewModel::class.java] }
    private val binding by lazy { ActivityResetPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
