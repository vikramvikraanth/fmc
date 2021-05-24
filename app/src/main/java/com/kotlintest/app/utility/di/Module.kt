package com.kotlintest.app.utility.di

import android.annotation.SuppressLint
import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotlintest.app.BuildConfig
import com.kotlintest.app.network.ApiInterface
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import com.kotlintest.app.utility.rx.SchedulersFacade
import com.kotlintest.app.viewModel.ComplaintViewModel
import com.kotlintest.app.viewModel.FamilyViewModel
import com.kotlintest.app.viewModel.LoginViewModel
import com.kotlintest.app.viewModel.MedicalProviderViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.strategy.Strategy
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

val viewModelModule = module {

    viewModel { LoginViewModel(get(),get()) }
    viewModel { FamilyViewModel(get(),get()) }
    viewModel { ComplaintViewModel(get(),get()) }
    viewModel { MedicalProviderViewModel(get(),get()) }
}

val apiModule = module {

    fun provideUserApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    single { provideUserApi(get()) }
}
val netModule = module {

    fun getRxJavaRetrofit(build: OkHttpClient.Builder): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(
                SimpleXmlConverterFactory.createNonStrict(
                    Persister(AnnotationStrategy())
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(build.build())
            .build()
    }

    fun getUnsafeOkHttpClient(sharedHelper: SharedHelper): OkHttpClient.Builder? {
        return try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            println("emter access token" + sharedHelper.getFromUser("token"))
            val interceptor =
                Interceptor { chain: Interceptor.Chain ->
                    val newRequest = chain.request().newBuilder()
                      //  .addHeader("Content-Type", "application/xml")
                        //.addHeader("Accept-Charset", "utf-8")
                        .build()
                    chain.proceed(newRequest)
                }
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG)
                logging.level = HttpLoggingInterceptor.Level.BODY
            else
                logging.level = HttpLoggingInterceptor.Level.NONE

            builder.addInterceptor(logging)
            builder.addInterceptor(interceptor)
            builder.retryOnConnectionFailure(true)
            builder.writeTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS).build()
            builder.sslSocketFactory(
                sslSocketFactory,
                (trustAllCerts[0] as X509TrustManager)
            )
            builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    single { getRxJavaRetrofit(get()) }
    single { getUnsafeOkHttpClient(get()) }
}

val repositoryModule = module {
    single { SharedHelper(get()) }
    single { SchedulersFacade() }
    single { CommonFunction(get()) }
    fun CommonApiCALL(
        application: Application,
        sharedHelper: SharedHelper,
        api: ApiInterface,
        schedulersFacade: SchedulersFacade
    ): CommonApi {
        return CommonApi(application, sharedHelper, api, schedulersFacade)
    }
    single { CommonApiCALL(get(), get(), get(), get()) }
}