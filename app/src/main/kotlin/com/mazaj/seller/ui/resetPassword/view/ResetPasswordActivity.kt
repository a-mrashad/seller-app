package com.mazaj.seller.ui.resetPassword.view

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityResetPasswordBinding
import com.mazaj.seller.extensions.hideKeyboard
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.resetPassword.viewModel.ResetPasswordViewModel
import com.mazaj.seller.ui.shared.network.OnFormSubmitted

class ResetPasswordActivity : BaseActivity(), OnFormSubmitted {
    override val viewModel by lazy { ViewModelProvider(this)[ResetPasswordViewModel::class.java] }
    private val binding by lazy { ActivityResetPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        viewModel.onPasswordResetSuccessfully.observe(this, Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })
        setupOnFormSubmitted()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener { onResetPasswordClicked() }
        binding.edNewPassword.doAfterTextChanged { viewModel.password = it.toString() }
        binding.edConfirmPassword.doAfterTextChanged { viewModel.passwordConfirmation = it.toString() }
        viewModel.enableResetButtonLiveData.observe(this, Observer { binding.btnNext.isEnabled = it })
        binding.edConfirmPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { onResetPasswordClicked() }
            true
        }
    }

    private fun onResetPasswordClicked() {
        hideKeyboard()
        val email = intent.extras?.getString(LoginActivity.EMAIL)
        val token = intent.extras?.getString(LoginActivity.TOKEN)
        viewModel.resetPassword(email, token)
    }
}
