package com.kotlintest.app.viewModel

import android.annotation.SuppressLint
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.model.localModel.ForgotPasswordModel
import com.kotlintest.app.model.localModel.LoginModel
import com.kotlintest.app.model.localModel.RegisterModel
import com.kotlintest.app.model.localModel.ReimbursementformModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ReimbursementViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {
    var isvalid = MutableLiveData<Boolean>();
    var mCurrentPhotoPath = MutableLiveData<String>()
    @SuppressLint("SimpleDateFormat")
    var destinationFileName: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())+".png"
    var reimbursementformModel = ReimbursementformModel()
    fun onUsernameTextChanged(loginParamModel: Any) {
        when(loginParamModel){
            is ReimbursementformModel ->{
                isvalid.postValue(loginParamModel.isValiator())
            }

        }

    }


    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getTreatCategoryListApi() {
        commonApi.getTreatCategoryListApi(response, disable)
    }
    fun getReimburseMentDocumentFileTypeListApi() {
        commonApi.getReimburseMentDocumentFileTypeListApi(response, disable)
    }
    fun getCurrencyListApi() {
        commonApi.getCurrencyListApi(response, disable)
    }
    fun getReimbursementListApi() {
        commonApi.getReimbursementListApi(response, disable)
    }
    fun getReimbursementFormApi(model:ReimbursementformModel) {
        commonApi.getReimbursementFormApi(model,response, disable)
    }

    fun getCountryListApi(){
        commonApi.getCountryListApi(response,disable)
    }
    fun createImageFile(): File {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String
        val image: File
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        imageFileName = "JPEG_" + timeStamp + "_"
        storageDir.mkdirs(); // make sure you call mkdirs() and not mkdir()
        image = File.createTempFile(
            imageFileName, // prefix
            ".jpg", // suffix
            storageDir      // directory
        )
        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = "file:" + image.absolutePath
        mCurrentPhotoPath.value = /*"file:" +*/ image.absolutePath
        return image
    }

}