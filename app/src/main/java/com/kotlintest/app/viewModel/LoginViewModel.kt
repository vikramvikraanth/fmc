package com.kotlintest.app.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction

class LoginViewModel(var commonApi: CommonApi,var commonFunction: CommonFunction,var application: Application)  : BaseViewModel() {

    var loginModel = LoginModel()
    var forgotPasswordModel = ForgotPasswordModel()
    var registerModel = MutableLiveData(RegisterModel())

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
        if(loginRegisterModel.Password.equals(loginRegisterModel.confirmPassword)){
            commonApi.registerApiCall(response, disable, loginRegisterModel)
        }else{
            commonFunction.commonToast(application.getString(R.string.password_mis))
        }

    }
    fun forgotPassswordApiCall(loginRegisterModel: ForgotPasswordModel) {
       // commonFunction.commonToast("Thank you for submiting")
        commonApi.forgotPasswordApiCall(response, disable, loginRegisterModel)
    }
    fun logoutApiCall() {

        commonApi.logoutApiCall(response, disable)
    }
    fun getUserInfoApi() {

        commonApi.getUserInfoApi(response, disable)
    }
}