package com.mazaj.seller.ui.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityLoginBinding
import com.mazaj.seller.extensions.hideKeyboard
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.extensions.showError
import com.mazaj.seller.extensions.showSnackBar
import com.mazaj.seller.ui.forgetPassword.view.ForgetPasswordActivity
import com.mazaj.seller.ui.login.viewModel.LoginViewModel
import com.mazaj.seller.ui.main.MainNavigationActivity
import com.mazaj.seller.ui.resetPassword.view.ResetPasswordActivity
import com.mazaj.seller.ui.shared.network.OnFormSubmitted

class LoginActivity : BaseActivity(), OnFormSubmitted {
    override val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
        setupOnFormSubmitted()
        handleResetPassword()
    }

    private fun setListeners() {
        binding.tvForgetPassword.setOnClickListener {
            startActivityForResult(Intent(this, ForgetPasswordActivity::class.java), FORGET_PASSWORD_REQUEST_CODE)
        }
        binding.btnNext.setOnClickListener {
            hideKeyboard()
            viewModel.onLoginClicked()
        }
        binding.edEmail.doAfterTextChanged { viewModel.email = it.toString() }
        binding.edPassword.doAfterTextChanged { viewModel.password = it.toString() }
        binding.edPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                viewModel.onLoginClicked()
            }
            true
        }
    }

    private fun setObservers() {
        viewModel.enableLoginButtonLiveData.observe(this) { binding.btnNext.isEnabled = it }
        viewModel.onLoginSucceededLiveEvent.observe(this) { startActivity(Intent(this, MainNavigationActivity::class.java).newTask()) }
        viewModel.loginErrorsLiveEvent.observe(this) { listOf(binding.tiEmail, binding.tiPassword).forEach { it.error = " " } }
        viewModel.clearErrorsLiveEvent.observe(this) {
            listOf(binding.tiEmail, binding.tiPassword).forEach {
                showError(View.GONE)
                it.error = ""
            }
        }
    }

    private fun handleResetPassword() {
        intent.data?.let {
            startActivityForResult(Intent(this, ResetPasswordActivity::class.java).apply {
                putExtra(TOKEN, it.getQueryParameter(TOKEN))
                putExtra(EMAIL, it.getQueryParameter(EMAIL))
            }, RESET_PASSWORD_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FORGET_PASSWORD_REQUEST_CODE && resultCode == Activity.RESULT_OK) showSnackBar(message = getString(R.string.reset_email_sent))
        if (requestCode == RESET_PASSWORD_REQUEST_CODE && resultCode == Activity.RESULT_OK) showSnackBar(message = getString(R.string.successful_reset))
    }

    companion object {
        const val FORGET_PASSWORD_REQUEST_CODE = 100
        const val RESET_PASSWORD_REQUEST_CODE = 200
        const val TOKEN = "token"
        const val EMAIL = "email"
    }
}
