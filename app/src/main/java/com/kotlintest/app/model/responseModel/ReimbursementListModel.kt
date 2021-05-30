package com.kotlintest.app.model.responseModel

data class ReimbursementListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var ReimbursementListResponse: ArrayList<ReimbursementResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class ReimbursementResponse(
        var UserId: String = "0",
        var CardNumber: String = "",
        var ClaimID: String = "",
        var LanguageID: String = "",
        var SubmissionDate: String = "",
        var ClaimStatus: String = "",
        var BatchNumber: String = "",
        var MemberName: String = ""
    )
}