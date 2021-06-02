package com.kotlintest.app.appControl

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.kotlintest.app.BuildConfig
import com.kotlintest.app.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.kotlintest.app.utility.di.apiModule
import com.kotlintest.app.utility.di.netModule
import com.kotlintest.app.utility.di.repositoryModule
import com.kotlintest.app.utility.di.viewModelModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        //logging module
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(getString(R.string.app_font))
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        startKoin {
            androidContext(this@AppController)
            androidLogger(Level.DEBUG)
            modules(listOf( repositoryModule, netModule, apiModule, viewModelModule))
        }
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

    }



    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }
    }
}