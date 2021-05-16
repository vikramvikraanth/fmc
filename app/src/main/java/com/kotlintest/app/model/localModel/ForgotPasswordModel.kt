package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass

data class ForgotPasswordModel(
    var email: String = "",
    var cardNumber: String = "",

):BaseValidatorClass(){

    fun isValiator() =  isValidEmail(email) && isempty(cardNumber)
}