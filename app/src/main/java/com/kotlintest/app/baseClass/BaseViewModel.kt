package com.kotlintest.app.baseClass
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlintest.app.network.Response
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    protected var disable: CompositeDisposable = CompositeDisposable()
    protected val response = MutableLiveData<Response>()

    init {
        disable = CompositeDisposable()
    }



    protected fun requestBody(value: String): RequestBody {

        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }



    protected fun createMultiPartImage(value: String, key: String): MultipartBody.Part? {
        var multipartBody: MultipartBody.Part? = null

        if (value.isEmpty()) {
            return multipartBody
        }
        if (value.startsWith("http") || value.startsWith("https")) {
            multipartBody = MultipartBody.Part.createFormData(
                key,
                "",
                RequestBody.create(getMimeType(File(value).toString())!!.toMediaTypeOrNull(), "")
            )
        } else {
            multipartBody = MultipartBody.Part.createFormData(
                key,
                File(value).getName(),
                RequestBody.create(
                    getMimeType(File(value).toString())!!.toMediaTypeOrNull(),
                    File(value)
                )
            )

        }
        return multipartBody

    }



    fun getMimeType(url: String): String? {
        var type: String? = ""
        val extension = MimeTypeMap.getFileExtensionFromUrl(url.replace(" ", "%20"))
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        if (type == null) {
            return "jpg"
        }
        return type
    }

    override fun onCleared() {
        super.onCleared()
        try {
            disable.clear()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }


}