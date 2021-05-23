package com.kotlintest.app.model.responseModel

data class ComplaintResponseModel(
    var ComplaintNo: String = "",
    var ApiResponse: apiResponse = apiResponse()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )
}