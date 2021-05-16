package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass

data class RegisterModel(
    var email: String = "",
    var Password: String = "",
    var confirmPassword: String = "",
    var cardNumber: String = "",
    var countrycode: String = "+91",
    var mobile: String = "",
    var dob: String = "",
    var gender:String =""

):BaseValidatorClass(){

    fun isValiator() =  isValidEmail(email) && isempty(Password) && isempty(cardNumber) &&isempty(mobile) &&isempty(dob) && isempty(gender)&&isCompare(Password,confirmPassword)
}