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

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobGetBenefitDetails")
    @POST("MobileApi.svc/")
    fun getBeneitiesApi(@Body body: RequestBody): Observable<ResponseBody>

    @Headers("SOAPAction:http://tempuri.org/IMobileApi/MobSubmitLogOut")
    @POST("MobileApi.svc/")
    fun getLogoutApi(@Body body: RequestBody): Observable<ResponseBody>
}