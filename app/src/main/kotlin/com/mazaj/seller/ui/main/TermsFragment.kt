package com.mazaj.seller.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mazaj.seller.BuildConfig
import com.mazaj.seller.Constants
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.FragmentAboutBinding
import com.mazaj.seller.ui.main.viewModel.MainViewModel

class TermsFragment : BaseFragment() {
    override val viewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.webview.loadUrl("${BuildConfig.BASE_URL}${Constants.PATH}pages/terms-and-conditions")
        return binding.root
    }
}
