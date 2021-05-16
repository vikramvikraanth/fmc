package com.kotlintest.app.baseClass

 open class BaseValidatorClass {

    protected fun isValidEmail(email :String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    protected fun isLenghtValidLimint(legnt:Int,value: String) : Boolean = value.length > legnt

     protected fun isempty(value :String) : Boolean = value.isNotBlank()

     protected fun isCompare(first :String, second: String) : Boolean = first.equals(second)
}