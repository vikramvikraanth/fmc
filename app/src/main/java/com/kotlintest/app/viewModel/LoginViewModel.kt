package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class LoginViewModel(var commonApi: CommonApi,var commonFunction: CommonFunction)  : BaseViewModel() {

    var loginModel = LoginModel()
    var forgotPasswordModel = ForgotPasswordModel()
    var registerModel = RegisterModel()

    var isvalid = MutableLiveData<Boolean>();

    fun onUsernameTextChanged(loginParamModel: Any) {
        when(loginParamModel){
            is LoginModel ->{
                isvalid.postValue(loginParamModel.isValiator())
            }
            is ForgotPasswordModel ->{
                isvalid.postValue(loginParamModel.isValiator())
            }
            is RegisterModel ->{
                isvalid.postValue(loginParamModel.isValiator())
            }
        }

    }
    fun response(): MutableLiveData<Response> {

        return response
    }

    fun loginApiCall(loginRegisterModel: LoginModel) {
        commonApi.loginApiCall(response, disable, loginRegisterModel)
    }
    fun registerApiCall(loginRegisterModel: RegisterModel) {
        commonApi.registerApiCall(response, disable, loginRegisterModel)
    }
    fun forgotPassswordApiCall(loginRegisterModel: ForgotPasswordModel) {
        commonFunction.commonToast("Thank you for submiting")
       // commonApi.forgotPasswordApiCall(response, disable, loginRegisterModel)
    }
    fun logoutApiCall() {

        commonApi.logoutApiCall(response, disable)
    }
}