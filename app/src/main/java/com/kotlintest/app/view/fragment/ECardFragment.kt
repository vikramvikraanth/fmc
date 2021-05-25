package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentECardBinding
import com.kotlintest.app.model.localModel.EcardLocalModel
import com.kotlintest.app.model.responseModel.EcardModel
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.activity.HomeActivity
import com.kotlintest.app.view.adapter.EcardAdapter
import com.kotlintest.app.viewModel.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ECardFragment : BaseFragment<FragmentECardBinding>() {
    private val familyViewModel by viewModel<FamilyViewModel>()


    override fun layoutId(): Int = R.layout.fragment_e_card

    var list  = ArrayList<EcardLocalModel>()
    var adapter : EcardAdapter?=null

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getcarDetails()
        list.add(EcardLocalModel("Front Picture",""))
        list.add(EcardLocalModel("Back Picture",""))
        adapter = EcardAdapter(list)
        binding.adapter = adapter
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is EcardModel ->{
                        list.get(0).imagePath = response.data.FmcEcardFront
                        list.get(1).imagePath= response.data.FmcEcardBack
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