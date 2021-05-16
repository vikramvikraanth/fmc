package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass

data class LoginModel(
    var UserName: String = "",
    var Password: String = "",

):BaseValidatorClass(){

    fun isValiator() =  isValidEmail(UserName) && isempty(Password)
}