package com.kotlintest.app.model.responseModel

data class EcardModel(
    var ApiResponse: apiResponse = apiResponse(),
    var LanguageID: String = "",
    var UserId: String = "0",
    var Memberid: String = "0",
    var CardNumber: String = "",
    var FmcEcardFront: String = "",
    var FmcEcardBack: String = ""
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )
}