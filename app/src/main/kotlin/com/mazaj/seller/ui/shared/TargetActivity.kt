package com.mazaj.seller.ui.shared

import com.mazaj.seller.ui.intro.IntroActivity
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.main.MainNavigationActivity

enum class TargetActivity(val className: Class<out com.mazaj.seller.base.BaseActivity>) {
    LOGIN(LoginActivity::class.java), HOME(MainNavigationActivity::class.java), INTRO(IntroActivity::class.java)
}
