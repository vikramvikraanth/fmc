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

    var mCurrentPhotoPath = MutableLiveData<String>()
    @SuppressLint("SimpleDateFormat")
    var destinationFileName: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())+".png"

    init {
        medias.value = ArrayList()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getDataFromMedia() {
        val mediaStoreArgs = Bundle()
        mediaStoreArgs.putBoolean(
            FilePickerConst.EXTRA_SHOW_GIF,
            PickerManager.isShowGif
        )
        mediaStoreArgs.putInt(FilePickerConst.EXTRA_FILE_TYPE, 1)
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