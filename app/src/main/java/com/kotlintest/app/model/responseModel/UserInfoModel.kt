package com.kotlintest.app.model.responseModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserInfoModel {

    @SerializedName("MemberID")
    @Expose
    private var memberID: String = ""

    @SerializedName("ApiResponse")
    @Expose
    private var apiResponse: ApiResponse = ApiResponse()

    @SerializedName("Name")
    @Expose
    private var name: String = ""

    @SerializedName("CardNumber")
    @Expose
    private var cardNumber: String = ""

    @SerializedName("LanguageID")
    @Expose
    private var languageID: String = ""

    @SerializedName("Dob")
    @Expose
    private var dob: String = ""

    @SerializedName("Gender")
    @Expose
    private var gender: String = ""

    @SerializedName("Email")
    @Expose
    private var email: String = ""

    @SerializedName("PayerCardNumber")
    @Expose
    private var payerCardNumber: String = ""

    fun getMemberID(): String {
        return memberID
    }

    fun setMemberID(memberID: String) {
        this.memberID = memberID
    }

    fun getApiResponse(): ApiResponse? {
        return apiResponse
    }

    fun setApiResponse(apiResponse: ApiResponse) {
        this.apiResponse = apiResponse
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getCardNumber(): String? {
        return cardNumber
    }

    fun setCardNumber(cardNumber: String) {
        this.cardNumber = cardNumber
    }

    fun getLanguageID(): String? {
        return languageID
    }

    fun setLanguageID(languageID: String) {
        this.languageID = languageID
    }

    fun getDob(): String? {
        return dob
    }

    fun setDob(dob: String) {
        this.dob = dob
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String) {
        this.gender = gender
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPayerCardNumber(): String? {
        return payerCardNumber
    }

    fun setPayerCardNumber(payerCardNumber: String) {
        this.payerCardNumber = payerCardNumber
    }


    class ApiResponse {
        @SerializedName("Details")
        @Expose
        var details: String = ""

        @SerializedName("Message")
        @Expose
        var message: String? = ""

        @SerializedName("StatusCode")
        @Expose
        var statusCode: String? = ""
    }

}