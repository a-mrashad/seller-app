package com.mazaj.seller.base

interface BaseView {
    val activity: BaseActivity
        get() = this as? BaseActivity ?: (this as BaseFragment).requireActivity() as BaseActivity
    val fragment: BaseFragment
        get() = this as BaseFragment
}
