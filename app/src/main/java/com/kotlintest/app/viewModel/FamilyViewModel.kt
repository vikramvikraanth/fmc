package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.PreApprovalsModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class FamilyViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var model =PreApprovalsModel()

    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getcarDetails() {
        commonApi.ECardApiCall(response, disable)
    }
    fun getpreApprovalsApiCall(model:PreApprovalsModel) {
        commonApi.preApprovalsApiCall(model,response, disable)
    }
    fun getBenefitiesApiCall() {
        commonApi.benefitiesApiCall(response, disable)
    }
    fun getFamilyApi() {
        commonApi.getFamilyApi(response, disable)
    }
}