package com.kotlintest.app.view.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivityLoginBinding
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.model.responseModel.UserInfoModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.fragment.BenefitsFragment
import com.kotlintest.app.view.fragment.ForgotPasswordFragment
import com.kotlintest.app.view.fragment.RegisterFragment
import com.kotlintest.app.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {

    private val loginViewModel by viewModel<LoginViewModel>()
    override fun layoutId(): Int = R.layout.activity_login

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        this.binding.click =this
        this.binding.loginViewModel =loginViewModel

        loginViewModel.response().observe(this, {
            processResponse(it)
        })

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.forgotpassword_txt->{
                moveTOFragment(ForgotPasswordFragment(),R.id.container_layout)
            }
            R.id.login_btn->{

            }
            R.id.register_lyt->{
                moveTOFragment(RegisterFragment(),R.id.container_layout)

            }
        }
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                     is ArrayList<*> ->{
                         when(response.data[0]){
                             is LoginResponseModel ->{
                                 val data : ArrayList<LoginResponseModel> = response.data as ArrayList<LoginResponseModel>
                                 data[0].apiResponse!!.message?.let { commonFunction.commonToast(it) }
                                 if(data.get(0).apiResponse!!.statusCode.equals("1")){
                                     data[0].userId?.let { sharedHelper.putInUser("user_id", it) }
                                     loginViewModel.getUserInfoApi()
                                 }
                             }
                             is UserInfoModel -> {
                                 val data : ArrayList<UserInfoModel> = response.data as ArrayList<UserInfoModel>
                                 sharedHelper.putInUser("member_id", data[0].getMemberID())
                                 commonFunction.modelToGson(data.get(0))?.let {
                                     sharedHelper.putInUser(
                                         "user_info",
                                         it
                                     )
                                 }
                                 sharedHelper.putInUser("milsec",System.currentTimeMillis().toString())
                                 setIntent(HomeActivity::class.java,3)

                             }
                         }

                     }

                }
            }
            Status.LOADING -> {
                commonFunction.showLoader(activity)

            }
            Status.DISMISS -> {
                commonFunction.dismissLoader()

            }

        }

    }

}