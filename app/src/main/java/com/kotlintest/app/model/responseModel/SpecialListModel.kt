package com.kotlintest.app.model.responseModel

data class SpecialListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var SpecialityListResponse: List<SpecialityResponse> = listOf()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class SpecialityResponse(
        var ApiResponse: Any = Any(),
        var UserId: String = "0",
        var LanguageID: String = "",
        var SpecialityId: String = "0",
        var SpecialityName: String = ""
    )
}