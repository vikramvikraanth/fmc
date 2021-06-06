package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass
import com.kotlintest.app.baseClass.BaseViewModel

data class MedicalFormModel(var city:String ="",var cityid:String= "",var country:String="",var countryid:String ="",var state: String="",var stateid:String="",var providertype:String="",var providerTypeid:String ="",var speciality:String="",var specialityid:String =""):BaseValidatorClass() {

    fun  isValidation() = isempty(city) && isempty(country) && isempty(state) && isempty(providertype) &&   providerTypeid !="2" || isempty(speciality)
}