package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class StaticViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {




    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getFaqsApi() {
        commonApi.getFaqsApi(response, disable)
    }
    fun getHealthTipsApi() {
        commonApi.getHealthTipsApi(response, disable)
    }
    fun getAboutApi() {
        commonApi.getAboutApi(response, disable)
    }

}