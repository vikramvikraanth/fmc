package com.kotlintest.app.model.responseModel

data class CityListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var CityListResponse: List<CityResponse> = listOf()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class CityResponse(
        var ApiResponse: Any = Any(),
        var UserId: String = "0",
        var LanguageID: String = "",
        var CityId: String = "0",
        var CityName: String = ""
    )
}