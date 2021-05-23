package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ComplaintModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class ComplaintViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var complaintmodel = MutableLiveData<ComplaintModel>()
    var isvalid = MutableLiveData<Boolean>();
    fun onUsernameTextChanged(loginParamModel: Any) {
        when(loginParamModel){
            is ComplaintModel ->{
                isvalid.postValue(loginParamModel.isValiator())
            }

        }

    }

    fun response(): MutableLiveData<Response> {

        return response
    }

    fun complaintTypeListApi(){
        commonApi.getComplaintTypelistApi(response,disable)
    }

    fun getComplaintListApi(){
        commonApi.getComplaintListApi(response,disable)
    }
    fun getComplainAddApi(data : ComplaintModel){
        commonApi.getComplainAddApi(data,response,disable)
    }

    fun resetChange (data : ComplaintModel){
        data.complaint =""
        data.datevistor =""
        data.providerName=""
        data.subject=""
    }


}