package com.kotlintest.app.model.responseModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginResponseModel {
    @SerializedName("ApiResponse")
    @Expose
    var apiResponse: ApiResponse? = null

    @SerializedName("UserId")
    @Expose
    var userId: String? = null

    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("LanguageID")
    @Expose
    var languageID: String? = null



class ApiResponse {
        @SerializedName("Details")
        @Expose
        var details: String? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null

        @SerializedName("StatusCode")
        @Expose
        var statusCode: String? = null
    }

}