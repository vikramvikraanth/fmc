package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentPreApprovalsBinding
import com.kotlintest.app.model.responseModel.PreApprovalModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.PreAppovalsAdapter
import com.kotlintest.app.viewModel.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PreApprovalsFragment : BaseFragment<FragmentPreApprovalsBinding>() {

    private val familyViewModel by viewModel<FamilyViewModel>()


    override fun layoutId(): Int = R.layout.fragment_pre_approvals

    var data : ArrayList<PreApprovalModel.PreApprovalDetailsResponse> = ArrayList()
    var adapter : PreAppovalsAdapter ?= null

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        data.clear()
        adapter =PreAppovalsAdapter(data)
        binding.adapter = adapter
        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getpreApprovalsApiCall()
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is PreApprovalModel ->{
                        response.data.preApprovalDetailsResponse?.let { data.addAll(it) }
                        binding.isvisible =data.isEmpty()

                            adapter?.notifyDataSetChanged()
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