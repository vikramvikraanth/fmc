package com.kotlintest.app.model.responseModel

data class FamilyListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var FamilyDetailsResponse: ArrayList<familyDetailsResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class familyDetailsResponse(
        var UserId: String = "0",
        var Memberid: String = "0",
        var CardNumber: String = "",
        var Name: String = "",
        var LanguageID: String = "",
        var PayerCardNumber: String = ""
    )
}