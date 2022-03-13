package com.mazaj.seller.ui.shared

import com.mazaj.seller.ui.intro.IntroActivity
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.main.view.MainActivity

enum class TargetActivity(val className: Class<out com.mazaj.seller.base.BaseActivity>) {
    LOGIN(LoginActivity::class.java), HOME(MainActivity::class.java), INTRO(IntroActivity::class.java)
}
