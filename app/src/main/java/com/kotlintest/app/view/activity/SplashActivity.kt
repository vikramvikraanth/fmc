package com.kotlintest.app.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity<ActivitySplashBinding>() {



    protected fun callIntent(){
     //   sharedHelper.putInUser("user_id", "2754")
        if(sharedHelper.getFromUser("user_id").isEmpty()){
            setIntent(MainActivity::class.java,3)
        }else{
            setIntent(HomeActivity::class.java,3)
        }

    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        disposable.add(Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {  t->
                run {
                    Timber.d(t)
                }
            }
            .subscribe { aLong ->
                callIntent()
            })
    }

}
