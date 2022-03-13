package com.mazaj.seller.ui.forgetPassword.view

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityForgetPasswordBinding
import com.mazaj.seller.extensions.hideKeyboard
import com.mazaj.seller.ui.forgetPassword.viewModel.ForgetPasswordViewModel
import com.mazaj.seller.ui.shared.network.OnFormSubmitted

class ForgetPasswordActivity : BaseActivity(), OnFormSubmitted {
    override val viewModel by lazy { ViewModelProvider(this)[ForgetPasswordViewModel::class.java] }
    private val binding by lazy { ActivityForgetPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
        setupOnFormSubmitted()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            hideKeyboard()
            viewModel.onForgetPasswordClicked()
        }
        binding.edEmail.doAfterTextChanged { viewModel.email = it.toString() }
        binding.edEmail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                viewModel.onForgetPasswordClicked()
            }
            true
        }
    }

    private fun setObservers() {
        viewModel.enableLoginButtonLiveData.observe(this, Observer { binding.btnNext.isEnabled = it })
        viewModel.onForgetPasswordSucceeded.observe(this, Observer { showCheckYourMailDialog() })
        viewModel.forgetPasswordErrorsLiveEvent.observe(this, Observer { binding.tiEmail.error = getString(it) })
        viewModel.clearErrorsLiveEvent.observe(this, Observer { binding.tiEmail.error = "" })
    }

    private fun showCheckYourMailDialog() {
        CheckEmailBottomSheet().show(supportFragmentManager, "")
    }
}
