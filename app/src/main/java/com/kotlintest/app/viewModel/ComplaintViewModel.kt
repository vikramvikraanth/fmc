package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ComplaintModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class ComplaintViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var complaintmodel = ComplaintModel()
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