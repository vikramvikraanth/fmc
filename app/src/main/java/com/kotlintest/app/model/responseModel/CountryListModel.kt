package com.kotlintest.app.model.responseModel

data class CountryListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var CountryListResponse: ArrayList<CountryResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class CountryResponse(
        var UserId: String = "0",
        var LanguageID: String = "",
        var CountryId: String = "0",
        var CountryName: String = ""
    )
}