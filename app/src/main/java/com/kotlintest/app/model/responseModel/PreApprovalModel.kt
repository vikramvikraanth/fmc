package com.kotlintest.app.model.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PreApprovalModel {
    @SerializedName("ApiResponse")
    @Expose
    var apiResponse: ApiResponse? = null

    @SerializedName("PreApprovalDetailsResponse")
    @Expose
    var preApprovalDetailsResponse: ArrayList<PreApprovalDetailsResponse>? =
        ArrayList()


    class PreApprovalDetailsResponse {
        @SerializedName("LanguageID")
        @Expose
        var languageID: String = ""

        @Expose
        var userId: String = ""

        @SerializedName("Memberid")
        @Expose
        var memberid: String = ""

        @SerializedName("CardNumber")
        @Expose
        var cardNumber: String = ""

        @SerializedName("Date")
        @Expose
        var date: String = ""

        @SerializedName("ProviderName")
        @Expose
        var providerName: String = ""

        @SerializedName("ServiceType")
        @Expose
        var serviceType: Any? = null

        @SerializedName("ServiceName")
        @Expose
        var serviceName: Any? = null

        @SerializedName("ApprovalStatus")
        @Expose
        var approvalStatus: String = ""

        @SerializedName("Service")
        @Expose
        var service: Service =
            Service()

    }
    class Service {
        @SerializedName("ServiceName")
        @Expose
        var serviceName: String = ""

        @SerializedName("ServiceType")
        @Expose
        var serviceType: String = ""

    }
    class ApiResponse {
        @SerializedName("Details")
        @Expose
        var details: String = ""

        @SerializedName("Message")
        @Expose
        var message: String = ""

        @SerializedName("StatusCode")
        @Expose
        var statusCode: String = ""

    }

}
