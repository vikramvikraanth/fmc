package com.kotlintest.app.model.responseModel

data class ProviderListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var ProviderCategoryListResponse: ArrayList<ProviderCategoryResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class ProviderCategoryResponse(
        var UserId: String = "0",
        var LanguageID: String = "",
        var ProviderTypeID: String = "0",
        var ProviderTypeName: String = ""
    )
}