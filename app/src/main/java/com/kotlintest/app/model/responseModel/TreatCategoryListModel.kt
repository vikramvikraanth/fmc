package com.kotlintest.app.model.responseModel

data class TreatCategoryListModel(
    var ApiResponse: apiResponse = apiResponse(),
    var CategoryListResponse: ArrayList<CategoryResponse> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class CategoryResponse(
        var UserId: String = "",
        var LanguageID: String = "",
        var CategoryID: String = "",
        var CategoryName: String = ""
    )
}