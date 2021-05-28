package com.kotlintest.app.model.responseModel

data class ReimbursementTypeListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var ReimbursementFileType: ArrayList<reimbursementFileType> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class reimbursementFileType(
        var FileTypeShortName: String = "",
        var FileTypeID: String = ""
    )
}