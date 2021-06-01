package com.kotlintest.app.model.responseModel

data class ContactUsModel(
    var ApiResponse: apiResponse = apiResponse(),
    var MobConatactus: ArrayList<MobConatactu> = ArrayList()
) {
    data class apiResponse(
        var Details: String = "",
        var Message: String = "",
        var StatusCode: String = ""
    )

    data class MobConatactu(
        var ContactUsHeading: String = "",
        var Address_Building: String = "",
        var Address_Flat: String = "",
        var Address_Street_1: String = "",
        var Address_Street_2: String = "",
        var Address_LandMark: String = "",
        var Address_City: String = "",
        var Address_Country: String = "",
        var Address_PO: String = "",
        var ContactNo: String = "",
        var EMail: String = "",
        var Latitude: String = "",
        var Longitude: String = "",
        var RouteMap: String = ""
    )
}