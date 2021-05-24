package com.kotlintest.app.model.responseModel

data class StateListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var EmiratesListResponse: ArrayList<EmiratesResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class EmiratesResponse(
        var UserId: String = "0",
        var LanguageID: String = "",
        var EmiratesId: String = "0",
        var EmiratesName: String = ""
    )
}