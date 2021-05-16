package com.kotlintest.app.model.localModel

import com.kotlintest.app.model.responseModel.BenefitiesModel

data class BenefitiesListModel(var title : String = "", var iPDeduction: ArrayList<BenefitiesModel.IPDeduction>? = ArrayList()) {
}