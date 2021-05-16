package com.kotlintest.app.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlintest.app.model.localModel.*
import com.kotlintest.app.model.responseModel.BenefitiesModel
import com.kotlintest.app.model.responseModel.EcardModel
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.model.responseModel.PreApprovalModel
import com.kotlintest.app.network.ResponseParse.ForgotPasswordReponse
import com.kotlintest.app.network.ResponseParse.LogingetReponse
import com.kotlintest.app.utility.SharedHelper
import com.kotlintest.app.utility.rx.SchedulersFacade
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject


class CommonApi constructor(
    var application: Application,
    val sharedHelper: SharedHelper,
    val api: ApiInterface,
    val schedulersFacade: SchedulersFacade
) {
    var gson = Gson()

    var adminuserName ="ADMIN"
    var adminPassword ="FMC4MOB"
    var CONTENT_TYPE = "text/xml; charset=utf-8"
    var namespace ="xmlns=\"http://tempuri.org/\""
    var namespace1 ="xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/"

    fun loginApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable,
        model: LoginModel
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobSubmitLogIn xmlns=\"http://tempuri.org/\">\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <UserName>"+model.UserName+"</UserName>\n" +
                "            <Password>"+model.Password+"</Password>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "        </MobSubmitLogIn>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)

        disable.add(api.getLoginApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val itemType = object : TypeToken<ArrayList<LoginResponseModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<LoginResponseModel>>(
                    LogingetReponse(it),
                    itemType
                )
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun forgotPasswordApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable,
        model: ForgotPasswordModel
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobSubmitForgotPassword xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <Email>"+model.email+"</Email>\n" +
                "            <CardNumber>"+model.cardNumber+"</CardNumber>\n" +
                "        </MobSubmitForgotPassword>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)

        disable.add(api.getForgotPassword(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val itemType = object : TypeToken<ArrayList<LoginResponseModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<LoginResponseModel>>(
                    ForgotPasswordReponse(it),
                    itemType
                )
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun logoutApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobSubmitLogOut xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobSubmitLogOut>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)

        disable.add(api.getLogoutApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobSubmitLogOutResponse").optString("MobSubmitLogOutResult")
                val itemType = object : TypeToken<ArrayList<LoginResponseModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<LoginResponseModel>>(data.toString(), itemType)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun registerApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable,
        model: RegisterModel
    ) {
        var gendertype =  "1"
        if(model.gender.toLowerCase().equals("male")){
            gendertype ="1"
        }else{
            gendertype ="0"
        }


        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobUserRegistration xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <CardNumber>"+model.cardNumber+"</CardNumber>\n" +
                "            <Password>"+model.Password+"</Password>\n" +
                "            <Email>"+model.email+"</Email>\n" +
                "            <Mobilenumber>"+model.countrycode+model.mobile+"</Mobilenumber>\n" +
                "            <DOB>"+model.dob+"</DOB>\n" +
                "            <Gender>"+gendertype+"</Gender>\n" +
                "        </MobUserRegistration>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)

        disable.add(api.getRegisterApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({

                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobUserRegistrationResponse").optString("MobUserRegistrationResult")
                val itemType = object : TypeToken<ArrayList<LoginResponseModel>>() {}.type
                 val itemList = gson.fromJson<ArrayList<LoginResponseModel>>(data.toString(), itemType)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
fun ECardApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetFMCECard xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <MemberID>2078342</MemberID>\n" +
                "        </MobGetFMCECard>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getEcardDetails(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({

                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetFMCECardResponse").optString("MobGetFMCECardResult")
                 val itemList = gson.fromJson(data.toString(), EcardModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun preApprovalsApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetPreApprovalDetails xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <MemberID>2078342</MemberID>\n" +
                "            <FromDate>01/01/2019</FromDate>\n" +
                "            <ToDate>01/04/2021</ToDate>\n" +
                "        </MobGetPreApprovalDetails>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getPreApprovalsApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetPreApprovalDetailsResponse").optString("MobGetPreApprovalDetailsResult")
                 val itemList = gson.fromJson(data.toString(), PreApprovalModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun benefitiesApiCall(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetBenefitDetails xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <MemberID>2078342</MemberID>\n" +
                "        </MobGetBenefitDetails>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getBeneitiesApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetBenefitDetailsResponse").optString("MobGetBenefitDetailsResult")
                val itemType = object : TypeToken<ArrayList<BenefitiesModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<BenefitiesModel>>(
                    data,
                    itemType
                )
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }


}