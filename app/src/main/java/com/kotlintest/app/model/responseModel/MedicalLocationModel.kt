package com.kotlintest.app.model.responseModel

data class MedicalLocationModel(
    var ApiResponse: apiResponse = apiResponse(),
    var MedicalProviderListResponse: ArrayList<MedicalProviderResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class MedicalProviderResponse(
        var ApiResponse: Any = Any(),
        var UserId: String = "0",
        var LanguageID: String = "",
        var ProviderName: String = "",
        var ProviderLocation: String = "",
        var ProviderPhone: String = "",
        var Latitude: String = "0",
        var Longitute: String = "0",
        var CountryID: String = "0",
        var EmiratesID: String = "0",
        var CityID: String = "0",
        var ProviderCategoryID: String = "0",
        var SpecialityID: String = "0"
    )
}