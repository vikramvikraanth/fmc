package com.kotlintest.app.model.responseModel

data class AboutModel(
    var ApiResponse: apiResponse = apiResponse(),
    var Heading: String = "",
    var HeadingDetails: String = ""
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )
}