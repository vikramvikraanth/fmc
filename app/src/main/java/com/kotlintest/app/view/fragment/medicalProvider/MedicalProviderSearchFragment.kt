package com.kotlintest.app.view.fragment.medicalProvider

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentMedicalProviderSearchBinding
import com.kotlintest.app.model.responseModel.MedicalLocationModel
import com.kotlintest.app.view.adapter.LocationListAdapter
import com.kotlintest.app.viewModel.MedicalProviderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MedicalProviderSearchFragment(var data: MedicalLocationModel?) : BaseFragment<FragmentMedicalProviderSearchBinding>() {

    private val medicalProviderViewModel by viewModel<MedicalProviderViewModel>()

    override fun layoutId(): Int = R.layout.fragment_medical_provider_search

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        medicalProviderViewModel.listdata.addAll(data!!.MedicalProviderListResponse)
        medicalProviderViewModel.adapter = LocationListAdapter(medicalProviderViewModel.listdata)
        binding.adapter = medicalProviderViewModel.adapter

        medicalProviderViewModel.response().observe(this,{
            when(it.status){
                Status.SUCCESS ->{
                    val data :String = it.data as String
                    if(data.isEmpty()){
                        medicalProviderViewModel.adapter!!.isfilter(medicalProviderViewModel.listdata)
                        return@observe
                    }
                    medicalProviderViewModel.temp.clear()
                    medicalProviderViewModel.listdata.forEach {
                        if(data.contains(it.ProviderName) || data.contains(it.ProviderLocation)){
                            medicalProviderViewModel.temp.add(it)
                        }
                    }
                    medicalProviderViewModel.adapter!!.isfilter(medicalProviderViewModel.temp)
                }
                else -> {

                }
            }
        })

    }


}