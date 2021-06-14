package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentWebviewBinding
import com.kotlintest.app.viewModel.StaticViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WebviewFragment : BaseFragment<FragmentWebviewBinding>() {

    private val staticViewModel by viewModel<StaticViewModel>()


    override fun layoutId(): Int = R.layout.fragment_webview

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.viewModel = staticViewModel
        binding.toolbar.setNavigationOnClickListener {
            fragmentManagers?.popBackStackImmediate()
        }

    }


}