package com.kotlintest.app.model.localModel

import com.kotlintest.app.baseClass.BaseValidatorClass

data class ReimbursementformModel(var category :String ="",var  categoryid:String ="",var country:String="",var countryid:String="",var currency:String="",var currencyid:String="",var amount:String=""
                                  ,var serviceperiod :String ="",var beneficiary :String ="",var bankname :String ="",var accountnumber :String ="",var ibannumber :String ="",var accounttype :String ="",var accounttypeid :String ="",var swiftcode :String ="",var listImage: ArrayList<ListImage> = ArrayList()):
    BaseValidatorClass() {

    fun isValiator() =   isempty(category) && isempty(country) &&isempty(currency) &&isempty(amount) && isempty(serviceperiod)&&listImage.isNotEmpty()  && isempty(ibannumber) && isempty(accountnumber) && isempty(bankname)&&isempty(beneficiary)&&isempty(swiftcode)&&isempty(accounttype)

    data class ListImage(var title :String ="",var FileTypeID:String="",var FileContent:String="",var FileName :String="",var MIMEType:String=""){

    }
}