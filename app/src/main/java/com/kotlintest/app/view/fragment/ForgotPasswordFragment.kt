package com.kotlintest.app.view.fragment

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentForgotPasswordBinding
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.activity.HomeActivity
import com.kotlintest.app.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(), View.OnClickListener {


    private val loginViewModel by viewModel<LoginViewModel>()


    override fun layoutId(): Int = R.layout.fragment_forgot_password

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click= this
        this.binding.loginViewModel =loginViewModel

        loginViewModel.response().observe(this, {
            processResponse(it)
        })


    }

    override fun onClick(p0: View?) {
        setIntent(HomeActivity::class.java,0)
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> ->{
                        val data : ArrayList<LoginResponseModel> = response.data as ArrayList<LoginResponseModel>
                        data[0].apiResponse!!.message?.let { commonFunction.commonToast(it) }
                        if(data.get(0).apiResponse!!.statusCode.equals("1")){
                           fragmentManagers?.popBackStackImmediate()
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