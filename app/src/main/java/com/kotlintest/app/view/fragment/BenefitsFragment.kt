package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentBenefitsBinding
import com.kotlintest.app.model.localModel.BenefitiesListModel
import com.kotlintest.app.model.responseModel.BenefitiesModel
import com.kotlintest.app.model.responseModel.EcardModel
import com.kotlintest.app.model.responseModel.UserInfoModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.BenifitAdapter
import com.kotlintest.app.viewModel.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BenefitsFragment : BaseFragment<FragmentBenefitsBinding>() {

    private val familyViewModel by viewModel<FamilyViewModel>()

    var benefitiesListModel: ArrayList<BenefitiesListModel>  = ArrayList()

    override fun layoutId(): Int = R.layout.fragment_benefits

    var adapter: BenifitAdapter ? =null

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        benefitiesListModel.clear()
        adapter =BenifitAdapter(benefitiesListModel)
        binding.adapter = adapter
        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getBenefitiesApiCall()
        val data =  commonFunction.gsonToModel(sharedHelper.getFromUser("user_info"),
            UserInfoModel::class.java)
        binding.userInfo = data as UserInfoModel?

    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> ->{
                        val data :BenefitiesModel = response.data[0] as BenefitiesModel
                        binding.data = data
                        benefitiesListModel.add(BenefitiesListModel(getString(R.string.op_deuctible),data.opDeduction))
                        benefitiesListModel.add(BenefitiesListModel(getString(R.string.ip_decutbles),data.iPDeduction))
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