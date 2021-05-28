package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class ReimbursementViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {




    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getTreatCategoryListApi() {
        commonApi.getTreatCategoryListApi(response, disable)
    }
    fun getReimburseMentDocumentFileTypeListApi() {
        commonApi.getReimburseMentDocumentFileTypeListApi(response, disable)
    }
    fun getCurrencyListApi() {
        commonApi.getCurrencyListApi(response, disable)
    }
    fun getReimbursementListApi() {
        commonApi.getReimbursementListApi(response, disable)
    }

    fun getCountryListApi(){
        commonApi.getCountryListApi(response,disable)
    }

}