package com.kotlintest.app.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivitySplashBinding
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.model.responseModel.UserInfoModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.viewModel.LoginViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val loginViewModel by viewModel<LoginViewModel>()


    protected fun callIntent(){
      sharedHelper.putInUser("user_id", "2754")
        if(sharedHelper.getFromUser("user_id").isEmpty()){
            setIntent(MainActivity::class.java,3)
        }else{

            loginViewModel.getUserInfoApi()
        }



    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.isvisible = false
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

        loginViewModel.response().observe(this,{
            processResponse(it)
        })
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> ->{
                        val data : ArrayList<UserInfoModel> = response.data as ArrayList<UserInfoModel>
                        commonFunction.modelToGson(data.get(0))?.let {
                            sharedHelper.putInUser("user_info",
                                it
                            )
                            sharedHelper.putInUser("member_id", data[0].getMemberID())
                            setIntent(HomeActivity::class.java,3)
                        }

                    }

                }
            }
            Status.LOADING -> {

                binding.isvisible = true

            }
            Status.DISMISS -> {
                binding.isvisible = false


            }

        }

    }
}
