package com.kotlintest.app.model.responseModel

data class ReimbursementsModel(
    var ApiResponse: apiResponse = apiResponse(),
    var ReimbursementID: Int = 0,
    var BatchNumber: String = ""
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )
}