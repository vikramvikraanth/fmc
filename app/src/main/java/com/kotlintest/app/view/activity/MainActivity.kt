package com.kotlintest.app.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {


    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
    }

    override fun onClick(p0: View?) {
        setIntent(LoginActivity::class.java,0)

    }
}
