package com.kotlintest.app.viewModel

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.CommonFunction


class StaticViewModel(var commonApi: CommonApi, var commonFunction: CommonFunction)  : BaseViewModel() {

    var url ="https://www.fmcnetwork.net/privacy-policy"


    fun response(): MutableLiveData<Response> {

        return response
    }

    fun getFaqsApi() {
        commonApi.getFaqsApi(response, disable)
    }
    fun getHealthTipsApi() {
        commonApi.getHealthTipsApi(response, disable)
    }
    fun getAboutApi() {
        commonApi.getAboutApi(response, disable)
    }
    fun getContactusListApi() {
        commonApi.getContactusListApi(response, disable)
    }
    private class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)

        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
        }
    }

    fun getWebViewClient(): WebViewClient? {
        return Client()
    }


}