package com.kotlintest.app.network

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitLogIn")
    @POST("MobileApi.svc/")
    fun getLoginApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitForgotPassword")
    @POST("MobileApi.svc/")
    fun getForgotPassword(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobUserRegistration")
    @POST("MobileApi.svc/")
    fun getRegisterApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetFMCECard")
    @POST("MobileApi.svc/")
    fun getEcardDetails(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetPreApprovalDetails")
    @POST("MobileApi.svc/")
    fun getPreApprovalsApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetFamilyDetails")
    @POST("MobileApi.svc/")
    fun getFamilyApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetBenefitDetails")
    @POST("MobileApi.svc/")
    fun getBeneitiesApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetComplaintList")
    @POST("MobileApi.svc/")
    fun getComplaintTypelistApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetUserInfo")
    @POST("MobileApi.svc/")
    fun getUserInfoApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetComplaintList")
    @POST("MobileApi.svc/")
    fun getComplaintListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitMemberComplaint")
    @POST("MobileApi.svc/")
    fun getComplainAddApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetCountryList")
    @POST("MobileApi.svc/")
    fun getCountryListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetStateList")
    @POST("MobileApi.svc/")
    fun getStateListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetCityList")
    @POST("MobileApi.svc/")
    fun getCityListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetProviderTypeList")
    @POST("MobileApi.svc/")
    fun getProviderListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetSpecialityList")
    @POST("MobileApi.svc/")
    fun getSpecialityListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitMedicalProviderSearch")
    @POST("MobileApi.svc/")
    fun getProviderLocationListApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobHealthTips")
    @POST("MobileApi.svc/")
    fun getHealthTipsApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobFAQ")
    @POST("MobileApi.svc/")
    fun getFaqsApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobAboutUs")
    @POST("MobileApi.svc/")
    fun getAboutApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitLogOut")
    @POST("MobileApi.svc/")
    fun getLogoutApi(@Body body: RequestBody): Observable<ResponseBody>
}