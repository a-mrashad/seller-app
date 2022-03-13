package com.mazaj.seller.ui.forgetPassword.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mazaj.seller.R
import com.mazaj.seller.databinding.BottomSheetCheckEmailBinding
import com.mazaj.seller.ui.forgetPassword.viewModel.ForgetPasswordViewModel

class CheckEmailBottomSheet : BottomSheetDialogFragment() {
    private val binding by lazy { BottomSheetCheckEmailBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(requireActivity())[ForgetPasswordViewModel::class.java] }
    private val countDownTimer = object: CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.tvSendEmailAgain.text = getString(R.string.send_email_again_seconds, millisUntilFinished/1000)
        }

        override fun onFinish() {
            binding.tvSendEmailAgain.isEnabled = true
            binding.tvSendEmailAgain.text = getString(R.string.send_email_again)
            binding.tvSendEmailAgain.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_white)
            val scale = (resources.displayMetrics.density*15) + 0.5f
            binding.tvSendEmailAgain.setPadding(0, scale.toInt(), 0, scale.toInt())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        setListeners()
    }

    private fun initLayout() {
        binding.tvHint.text = getString(R.string.sent_reset_password_email_hint, viewModel.email)
    }

    private fun setListeners() {
        dialog!!.setOnShowListener { dialog ->
            val sheetDialog = dialog as BottomSheetDialog
            val bottomSheetInternal = sheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheetInternal).isDraggable = false
        }
        binding.icClose.setOnClickListener {
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
        }
        binding.btnNext.setOnClickListener { openEmailApp() }
        binding.tvChangeEmailAddress.setOnClickListener { dialog!!.dismiss() }
        binding.tvResendEmail.setOnClickListener {
            binding.layoutCheckEmail.visibility = View.GONE
            binding.layoutResendEmail.visibility = View.VISIBLE
            resetCountdownTimer()
        }
        binding.tvSendEmailAgain.setOnClickListener {
            viewModel.onForgetPasswordClicked()
            dialog!!.dismiss()
        }
    }

    private fun openEmailApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        startActivity(intent)
        startActivity(Intent.createChooser(intent, getString(R.string.choose_email_client)))
    }

    private fun resetCountdownTimer() {
        binding.tvSendEmailAgain.isEnabled = false
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}