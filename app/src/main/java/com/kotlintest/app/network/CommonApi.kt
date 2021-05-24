package com.kotlintest.app.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlintest.app.model.localModel.*
import com.kotlintest.app.model.responseModel.*
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

                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobSubmitForgotPasswordResponse").optString("MobSubmitForgotPasswordResult")
                val itemType = object : TypeToken<ArrayList<LoginResponseModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<LoginResponseModel>>(
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
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
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
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
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
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
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
    fun getUserInfoApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetUserInfo xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobGetUserInfo>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getUserInfoApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetUserInfoResponse").optString("MobGetUserInfoResult")
                val itemType = object : TypeToken<ArrayList<UserInfoModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<UserInfoModel>>(
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
    fun getComplaintTypelistApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetComplaintTypeList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobGetComplaintTypeList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getComplaintTypelistApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetComplaintTypeListResponse").optString("MobGetComplaintTypeListResult")
                val itemType = object : TypeToken<ArrayList<ComplaintTypeListModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<ComplaintTypeListModel>>(
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
    fun getComplaintListApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetComplaintList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
                "        </MobGetComplaintList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getComplaintListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetComplaintListResponse").optString("MobGetComplaintListResult")
                val itemType = object : TypeToken<ArrayList<ComplaintListModel>>() {}.type
                val itemList = gson.fromJson<ArrayList<ComplaintListModel>>(
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
    fun getComplainAddApi(complaintModel: ComplaintModel,
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

      val data : UserInfoModel  =  gson.fromJson(sharedHelper.getFromUser("user_info"), UserInfoModel::class.java)
        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobSubmitMemberComplaint xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <CardNumber>"+data.getCardNumber()+"</CardNumber>\n" +
                "            <ComplaintBy>vikram</ComplaintBy>\n" +
                "            <Subject>"+complaintModel.subject+"</Subject>\n" +
                "            <Message>"+complaintModel.complaint+"</Message>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
                "            <DataOfVisit>"+complaintModel.datevistor+"</DataOfVisit>\n" +
                "            <ProviderName>"+complaintModel.providerName+"</ProviderName>\n" +
                "        </MobSubmitMemberComplaint>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getComplainAddApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobSubmitMemberComplaintResponse").optString("MobSubmitMemberComplaintResult")
                val itemList = gson.fromJson(data.toString(), ComplaintResponseModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getCountryListApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetCountryList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobGetCountryList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getCountryListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetCountryListResponse").optString("MobGetCountryListResult")
                val itemList = gson.fromJson(data.toString(), CountryListModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getStateListApi( id :String,
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetStateList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <CountryID>"+id+"</CountryID>\n" +
                "        </MobGetStateList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getStateListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetStateListResponse").optString("MobGetStateListResult")
                val itemList = gson.fromJson(data.toString(), StateListModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getCityListApi(id :String,
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetCityList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <StateID>"+id+"</StateID>\n" +
                "        </MobGetCityList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getCityListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetCityListResponse").optString("MobGetCityListResult")
                val itemList = gson.fromJson(data.toString(), CityListModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getProviderListApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetProviderTypeList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobGetProviderTypeList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getProviderListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetProviderTypeListResponse").optString("MobGetProviderTypeListResult")
                val itemList = gson.fromJson(data.toString(), ProviderListModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getSpecialityListApi(
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobGetSpecialityList xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "        </MobGetSpecialityList>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getSpecialityListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobGetSpecialityListResponse").optString("MobGetSpecialityListResult")
                val itemList = gson.fromJson(data.toString(), SpecialListModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }
    fun getProviderLocationListApi(model:MedicalFormModel,
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {

        val requestBodyText = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <MobSubmitMedicalProviderSearch xmlns=\"http://tempuri.org/\">\n" +
                "            <AdminUserName>"+adminuserName+"</AdminUserName>\n" +
                "            <AdminPassword>"+adminPassword+"</AdminPassword>\n" +
                "            <Deviceuuid>1</Deviceuuid>\n" +
                "            <Devicepushid>1</Devicepushid>\n" +
                "            <Deviceos>1</Deviceos>\n" +
                "            <LanguageId>EN</LanguageId>\n" +
                "            <UserID>"+sharedHelper.getFromUser("user_id")+"</UserID>\n" +
                "            <CountryID>"+model.countryid+"</CountryID>\n" +
                "            <StateID>"+model.state+"</StateID>\n" +
                "            <CityID>"+model.cityid+"</CityID>\n" +
                "            <ProviderTypeID>"+model.providerTypeid+"</ProviderTypeID>\n" +
                "            <SpecialityID>"+model.specialityid+"</SpecialityID>\n" +
                "            <ProviderName>"+model.providertype+"</ProviderName>\n" +
                "            <MemberID>"+sharedHelper.getFromUser("member_id")+"</MemberID>\n" +
                "        </MobSubmitMedicalProviderSearch>\n" +
                "    </Body>\n" +
                "</Envelope>"
        val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        disable.add(api.getProviderLocationListApi(requestBody)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                val xmlToJson = XmlToJson.Builder(it.string()).build()
                println("enter the xml response"+xmlToJson)
                val jondata = JSONObject(xmlToJson.toFormattedString())
                val data =jondata.optJSONObject("s:Envelope").optJSONObject("s:Body").optJSONObject("MobSubmitMedicalProviderSearchResponse").optString("MobSubmitMedicalProviderSearchResult")
                val itemList = gson.fromJson(data.toString(), MedicalLocationModel::class.java)
                response.value = Response.success(itemList)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }


}