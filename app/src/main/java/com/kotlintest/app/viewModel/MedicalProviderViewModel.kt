package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ComplaintModel
import com.kotlintest.app.model.localModel.MedicalFormModel
import com.kotlintest.app.model.responseModel.MedicalLocationModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.view.adapter.LocationListAdapter

class MedicalProviderViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var medicalFormModel = MedicalFormModel()
    var isvalid = MutableLiveData<Boolean>()
    var listdata = ArrayList<MedicalLocationModel.MedicalProviderResponse>()
    var adapter : LocationListAdapter?=null
    var searchTitle :String =""
    var temp = ArrayList<MedicalLocationModel.MedicalProviderResponse>()

    fun onUsernameTextChanged(complaintmodel: Any) {
        when(complaintmodel){
            is MedicalFormModel ->{
                isvalid.postValue(complaintmodel.isValidation())
            //    if(complaintmodel.speciality.isEmpty())
              //  isvalid.postValue(complaintmodel.providerTypeid!="2")
            }
            is String ->{
                response.value = Response.success(complaintmodel)
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
        if(id.providerTypeid!="2"){
            id.specialityid ="0"
        }
        commonApi.getProviderLocationListApi(id,response,disable)
    }
    fun getCityListApi(id:String){
        commonApi.getCityListApi(id,response,disable)
    }





}