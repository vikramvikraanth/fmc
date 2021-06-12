package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass

data class ComplaintModel(var providerName : String ="",var datevistor :String="" ,var subject:String ="",var complaint : String ="",var complainttype: String="",var complainttypeid: String=""):BaseValidatorClass() {

    fun isValiator() =   isempty(providerName) && isempty(datevistor) && isempty(subject) && isempty(complaint) && isempty(complainttype)

}