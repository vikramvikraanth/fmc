package com.kotlintest.app.model.responseModel

data class ComplaintTypeListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var ComplaintTypeListResponse: ArrayList<ComplaintTypeResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class ComplaintTypeResponse(
        var UserId: String = "0",
        var LanguageID: String = "",
        var ComplaintTypeId: String = "0",
        var ComplaintName: String = ""
    )
}