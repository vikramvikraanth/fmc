package com.kotlintest.app.model.responseModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class BenefitiesModel {

    @SerializedName("ApiResponse")
    @Expose
    var apiResponse: ApiResponse =
        ApiResponse()

    @SerializedName("LanguageID")
    @Expose
    var languageID: String? = null

    @SerializedName("UserId")
    @Expose
    var userId: String? = null

    @SerializedName("Memberid")
    @Expose
    var memberid: String? = null

    @SerializedName("CardNumber")
    @Expose
    var cardNumber: String? = null

    @SerializedName("Age")
    @Expose
    var age: String? = null

    @SerializedName("PolicyNumber")
    @Expose
    var policyNumber: String? = null

    @SerializedName("EffectiveDate")
    @Expose
    var effectiveDate: String? = null

    @SerializedName("ExpiryDate")
    @Expose
    var expiryDate: String? = null

    @SerializedName("TotalLimit")
    @Expose
    var totalLimit: String? = null

    @SerializedName("PH_Limit")
    @Expose
    var pHLimit: String? = null

    @SerializedName("Co_Insurance")
    @Expose
    var coInsurance: String? = null

    @SerializedName("Op_Deduction")
    @Expose
    var opDeduction: ArrayList<IPDeduction>? = ArrayList()

    @SerializedName("IP_Deduction")
    @Expose
    var iPDeduction: ArrayList<IPDeduction>? = ArrayList()

    @SerializedName("PolicyHolderName")
    @Expose
    var policyHolderName: String? = null
    class IPDeduction {
        @SerializedName("DeductableRemarks")
        @Expose
        var deductableRemarks: String? = null

        @SerializedName("DeductableFlat")
        @Expose
        var deductableFlat: String? = null

        @SerializedName("DeductablePer")
        @Expose
        var deductablePer: String? = null

        @SerializedName("DeductableType")
        @Expose
        var deductableType: String? = null

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
        var statusCode: String? = ""

    }

}