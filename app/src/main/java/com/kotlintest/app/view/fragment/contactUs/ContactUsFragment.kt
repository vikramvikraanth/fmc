package com.kotlintest.app.view.fragment.contactUs

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentContactUsBinding
import com.kotlintest.app.model.responseModel.ContactUsModel
import com.kotlintest.app.model.responseModel.FaqModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.ContactUsAdapter
import com.kotlintest.app.view.adapter.FaqListAdapter
import com.kotlintest.app.viewModel.StaticViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {

    private val staticViewModel by viewModel<StaticViewModel>()



    override fun layoutId(): Int = R.layout.fragment_contact_us

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        staticViewModel.response().observe(this,{
            processResponse(it)
        })
        staticViewModel.getContactusListApi()
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ContactUsModel ->{
                        binding.adapter = ContactUsAdapter(response.data.MobConatactus)
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