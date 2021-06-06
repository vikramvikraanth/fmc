package com.kotlintest.app.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.astrology.app.utility.imagePicker.Files.FilePickerConst
import com.astrology.app.utility.imagePicker.Files.PickerManager
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.imagePicker.Files.Media
import com.kotlintest.app.utility.imagePicker.Files.PhotoDirectory
import com.kotlintest.app.utility.imagePicker.Interface.FileResultCallback
import com.kotlintest.app.utility.imagePicker.MediaStoreHelper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FilePickerViewModel constructor(var application: Application) : BaseViewModel() {

    var medias = MutableLiveData(ArrayList<Media>())



    init {
        medias.value = ArrayList()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getDataFromMedia(type :Int) {
        val mediaStoreArgs = Bundle()
        mediaStoreArgs.putBoolean(
            FilePickerConst.EXTRA_SHOW_GIF,
            PickerManager.isShowGif
        )
        mediaStoreArgs.putInt(FilePickerConst.EXTRA_FILE_TYPE, type)
        application.let {
            MediaStoreHelper.getDirs(it.contentResolver, mediaStoreArgs,
                object : FileResultCallback<PhotoDirectory> {
                    override fun onResultCallback(files: List<PhotoDirectory>) {
                        for (i in files.indices) {
                            medias.value!!.addAll(files[i].medias)
                        }
                        response.value = Response.success(medias)
                    }

                })
        }
    }



}