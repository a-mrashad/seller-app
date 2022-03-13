package com.mazaj.seller.ui.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
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
import com.mazaj.seller.ui.main.view.MainActivity
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
        viewModel.enableLoginButtonLiveData.observe(this, Observer { binding.btnNext.isEnabled = it })
        viewModel.onLoginSucceededLiveEvent.observe(this, Observer { startActivity(Intent(this, MainActivity::class.java).newTask()) })
        viewModel.loginErrorsLiveEvent.observe(this, Observer { listOf(binding.tiEmail, binding.tiPassword).forEach {
            it.error = " "
        } })
        viewModel.clearErrorsLiveEvent.observe(this, Observer { listOf(binding.tiEmail, binding.tiPassword).forEach {
            showError(View.GONE)
            it.error = ""
        } })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FORGET_PASSWORD_REQUEST_CODE && resultCode == Activity.RESULT_OK) showSnackBar(message = getString(R.string.reset_email_sent))
    }

    companion object { const val FORGET_PASSWORD_REQUEST_CODE = 100 }
}
