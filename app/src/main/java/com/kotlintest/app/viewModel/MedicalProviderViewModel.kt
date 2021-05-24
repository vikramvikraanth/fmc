package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ComplaintModel
import com.kotlintest.app.model.localModel.MedicalFormModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class MedicalProviderViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var medicalFormModel = MedicalFormModel()
    var isvalid = MutableLiveData<Boolean>();
    fun onUsernameTextChanged(complaintmodel: Any) {
        when(complaintmodel){
            is ComplaintModel ->{
                isvalid.postValue(complaintmodel.isValiator())
            }
        }


    }

    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getCountryListApi(){
        commonApi.getCountryListApi(response,disable)
    }
    fun getSpecialityListApi(){
        commonApi.getSpecialityListApi(response,disable)
    }

    fun getProviderListApi(){
        commonApi.getProviderListApi(response,disable)
    }
    fun getStateListApi(id:String){
        commonApi.getStateListApi(id,response,disable)
    }
    fun getProviderLocationListApi(id:MedicalFormModel){
        commonApi.getProviderLocationListApi(id,response,disable)
    }
    fun getCityListApi(id:String){
        commonApi.getCityListApi(id,response,disable)
    }





}