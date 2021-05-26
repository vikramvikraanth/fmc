package com.kotlintest.app.model.responseModel

data class FaqModel(
    var ApiResponse: apiResponse = apiResponse(),
    var MobFAQ: ArrayList<mobFAQ> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class mobFAQ(
        var FAQuestion: String = "",
        var FAAnswer: String = "",
        var state : Boolean = false
    )
}