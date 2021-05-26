package com.kotlintest.app.view.fragment.faq

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentFAQBinding
import com.kotlintest.app.model.responseModel.FaqModel
import com.kotlintest.app.model.responseModel.HealthTipModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.FaqListAdapter
import com.kotlintest.app.viewModel.StaticViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FAQFragment : BaseFragment<FragmentFAQBinding>() {

    private val staticViewModel by viewModel<StaticViewModel>()


    override fun layoutId(): Int = R.layout.fragment_f_a_q

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        staticViewModel.response().observe(this,{
            processResponse(it)
        })
        staticViewModel.getFaqsApi()


    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is FaqModel ->{
                        binding.adapter = FaqListAdapter(response.data.MobFAQ as ArrayList<Any>)
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