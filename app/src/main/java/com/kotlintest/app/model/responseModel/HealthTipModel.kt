package com.kotlintest.app.model.responseModel

data class HealthTipModel(
    var ApiResponse: apiResponse = apiResponse(),
    var MobHealthTips: List<MobHealthTip> = listOf()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class MobHealthTip(
        var HT_Heading: String = "",
        var HT_Description: String = "",
        var state : Boolean = false

    )
}