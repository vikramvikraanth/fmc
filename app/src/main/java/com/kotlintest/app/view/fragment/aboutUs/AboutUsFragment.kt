package com.kotlintest.app.view.fragment.aboutUs

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentAboutUsBinding
import com.kotlintest.app.model.responseModel.AboutModel
import com.kotlintest.app.model.responseModel.HealthTipModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.FaqListAdapter
import com.kotlintest.app.viewModel.StaticViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>() {

    private val staticViewModel by viewModel<StaticViewModel>()


    override fun layoutId(): Int = R.layout.fragment_about_us

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        staticViewModel.response().observe(this,{
            processResponse(it)
        })
        staticViewModel.getAboutApi()

    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is AboutModel ->{
                        binding.title =response.data.Heading
                        binding.description =response.data.HeadingDetails
                    }

                }
            }
            Status.LOADING -> {
                commonFunction.showLoader(activity)

            }
            Status.DISMISS -> {
                commonFunction.dismissLoader()

            }

        }

    }

}