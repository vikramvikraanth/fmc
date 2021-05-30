package com.kotlintest.app.view.fragment.medicalProvider

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentMedicalProviderBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.localModel.MedicalFormModel
import com.kotlintest.app.model.responseModel.*
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.bottomsheetFragment.SelectBottomSheetFragment
import com.kotlintest.app.viewModel.MedicalProviderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MedicalProviderFragment : BaseFragment<FragmentMedicalProviderBinding>(),
    View.OnClickListener {

    private val medicalProviderViewModel by viewModel<MedicalProviderViewModel>()


    override fun layoutId(): Int = R.layout.fragment_medical_provider

    private var listCountry : ArrayList<CountryListModel.CountryResponse> = ArrayList()
    private var listcity : ArrayList<CityListModel.CityResponse> = ArrayList()
    private var listState : ArrayList<StateListModel.EmiratesResponse> = ArrayList()
    private var listProvider : ArrayList<ProviderListModel.ProviderCategoryResponse> = ArrayList()
    private var listSpecialListModel : ArrayList<SpecialListModel.SpecialityResponse> = ArrayList()

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        binding.click = this
        listCountry.clear()
        medicalProviderViewModel.response().observe(this,{
            processResponse(it)
        })
        medicalProviderViewModel.getCountryListApi()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reset_btn->{
                medicalProviderViewModel.medicalFormModel = MedicalFormModel()
                binding.viewModel = medicalProviderViewModel

            }
            R.id.city_edt->{
                if(medicalProviderViewModel.medicalFormModel.state.isEmpty()){
                    commonFunction.commonToast("Select your State")
                    return
                }

                showSelectionSheet(listcity as ArrayList<Any>,"Select City")

            }
            R.id.state_edt->{
                if(medicalProviderViewModel.medicalFormModel.countryid.isEmpty()){
                    commonFunction.commonToast("Select your country")
                    return
                }
                showSelectionSheet(listState as ArrayList<Any>,"Select State")

            }
            R.id.country_edt->{

                showSelectionSheet(listCountry as ArrayList<Any>,"Select County")

            }
            R.id.type_edt->{
                if(medicalProviderViewModel.medicalFormModel.cityid.isEmpty()){
                    commonFunction.commonToast("Select your Country")
                    return
                }

                showSelectionSheet(listProvider as ArrayList<Any>,"Select Provider Type")

            }
            R.id.speciality_edt->{
                if(medicalProviderViewModel.medicalFormModel.providerTypeid.isEmpty()){
                    commonFunction.commonToast("Select your Select your Provider Type")
                    return
                }

                showSelectionSheet(listSpecialListModel as ArrayList<Any>,"Select Speciality")

            }
        }
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is CountryListModel->{
                        listCountry.clear()
                        listCountry.addAll(response.data.CountryListResponse)
                    }
                    is CityListModel->{
                        listcity.clear()
                        listcity.addAll(response.data.CityListResponse)
                    }
                    is StateListModel->{
                        listState.clear()
                        listState.addAll(response.data.EmiratesListResponse)
                    }
                    is ProviderListModel->{
                        listProvider.clear()
                        listProvider.addAll(response.data.ProviderCategoryListResponse)
                    }
                    is SpecialListModel->{
                        listSpecialListModel.clear()
                        listSpecialListModel.addAll(response.data.SpecialityListResponse)
                    }
                    is MedicalLocationModel ->{
                        if(response.data.MedicalProviderListResponse.isEmpty()){
                            commonFunction.commonToast(response.data.ApiResponse.Details)
                            return
                        }
                        event.post(NavigateEvent("medical"))
                        moveTOFragment(MedicalProviderListFragment(response.data),R.id.provider_container)
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

    private fun showSelectionSheet(list:ArrayList<Any> , title :String){

        if(bottomSheet!=null && bottomSheet!!.isAdded){
            bottomSheet?.dismiss()
        }


        bottomSheet = SelectBottomSheetFragment(object :Commoninterface{
            override fun onCallback(value: Any) {

                when(value){
                    is CountryListModel.CountryResponse->{
                        medicalProviderViewModel.getStateListApi(value.CountryId)
                        medicalProviderViewModel.medicalFormModel.country = value.CountryName
                        medicalProviderViewModel.medicalFormModel.countryid = value.CountryId
                        binding.viewModel = medicalProviderViewModel
                        bottomSheet?.dismiss()

                    }
                    is CityListModel.CityResponse->{
                        if(listProvider.isEmpty()){
                            medicalProviderViewModel.getProviderListApi()
                        }
                        medicalProviderViewModel.medicalFormModel.cityid = value.CityId
                        medicalProviderViewModel.medicalFormModel.city = value.CityName
                        binding.viewModel = medicalProviderViewModel
                        bottomSheet?.dismiss()

                    }
                    is StateListModel.EmiratesResponse->{
                        medicalProviderViewModel.getCityListApi(value.EmiratesId)

                        medicalProviderViewModel.medicalFormModel.state = value.EmiratesName
                        medicalProviderViewModel.medicalFormModel.stateid = value.EmiratesId
                        binding.viewModel = medicalProviderViewModel
                        bottomSheet?.dismiss()

                    }
                    is SpecialListModel.SpecialityResponse ->{
                        medicalProviderViewModel.medicalFormModel.speciality = value.SpecialityName
                        medicalProviderViewModel.medicalFormModel.specialityid = value.SpecialityId
                        binding.viewModel = medicalProviderViewModel
                        bottomSheet?.dismiss()


                    }
                    is ProviderListModel.ProviderCategoryResponse ->{
                        medicalProviderViewModel.medicalFormModel.providertype = value.ProviderTypeName
                        medicalProviderViewModel.medicalFormModel.providerTypeid = value.ProviderTypeID
                        if(listSpecialListModel.isEmpty()){
                            medicalProviderViewModel.getSpecialityListApi()
                        }
                        binding.viewModel = medicalProviderViewModel
                        bottomSheet?.dismiss()


                    }
                }

            }

        },title,list)
        bottomSheet?.show(fragmentManagers!!,"show_select")
    }


}