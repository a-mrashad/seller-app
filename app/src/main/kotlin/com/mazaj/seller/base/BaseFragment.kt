package com.mazaj.seller.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mazaj.seller.ui.shared.ShowMessageView

abstract class BaseFragment : Fragment(), ShowMessageView {
    abstract override val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShowMessageView()
    }
}
