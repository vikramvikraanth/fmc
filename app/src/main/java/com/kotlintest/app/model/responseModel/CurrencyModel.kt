package com.kotlintest.app.model.responseModel

 class CurrencyModel(
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
        var CurrencyID: String = "0",
        var CurrencyName: String = ""
    )
}