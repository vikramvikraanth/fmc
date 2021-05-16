package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentECardBinding
import com.kotlintest.app.model.responseModel.EcardModel
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.activity.HomeActivity
import com.kotlintest.app.viewModel.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ECardFragment : BaseFragment<FragmentECardBinding>() {
    private val familyViewModel by viewModel<FamilyViewModel>()


    override fun layoutId(): Int = R.layout.fragment_e_card

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getcarDetails()
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is EcardModel ->{
                        binding.front = response.data.FmcEcardFront
                        binding.back = response.data.FmcEcardBack
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