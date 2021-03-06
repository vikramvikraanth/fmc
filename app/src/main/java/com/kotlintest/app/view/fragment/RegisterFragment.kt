package com.kotlintest.app.view.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentRegisterBinding
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.model.responseModel.UserInfoModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.activity.HomeActivity
import com.kotlintest.app.view.bottomsheetFragment.GenderFragment
import com.kotlintest.app.viewModel.LoginViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private val loginViewModel by viewModel<LoginViewModel>()
    var dpd: DatePickerDialog? =null

    override fun layoutId(): Int = R.layout.fragment_register
    var data :String=""

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
        this.binding.loginViewModel =loginViewModel
        data.length
        binding.istateid = false
        loginViewModel.response().observe(this, {
            processResponse(it)
        })
        binding.radioButton.isChecked = true
        binding.radioButton.setOnClickListener {
            binding.istateid = false
            loginViewModel.registerModel.value!!.cardNumber =""
            this.binding.loginViewModel =loginViewModel
        }
        binding.radioButton2.setOnClickListener {
            binding.istateid = true
            loginViewModel.registerModel.value!!.cardNumber =""
            this.binding.loginViewModel =loginViewModel

        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.register_btn -> {
                setIntent(HomeActivity::class.java, 0)
            }
            R.id.login_lyt -> {
                fragmentManagers?.popBackStackImmediate()
            }
            R.id.date_edit -> {
                datePicker()
            }
            R.id.gender_edit -> {
                binding.cardNumberTxt.clearFocus()
                activity.window.getDecorView().clearFocus()

                bottomSheet = GenderFragment(object : Commoninterface {
                    override fun onCallback(value: Any) {
                        //loginViewModel.registerModel.value!!.gender = value as String
                       // loginViewModel.registerModel.value = loginViewModel.registerModel.value
                        binding.genderEdit.setText(value as String)

                    }

                })
                bottomSheet!!.show(fragmentManagers!!, "gender")
            }
            R.id.termc_condition_txt->{
                moveTOFragment(WebviewFragment(),R.id.register_container)
            }
        }
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> -> {
                        when (response.data[0]) {
                            is LoginResponseModel -> {
                                val data: ArrayList<LoginResponseModel> =
                                    response.data as ArrayList<LoginResponseModel>
                                data[0].apiResponse!!.details?.let { commonFunction.commonToast(it) }
                                if (data.get(0).apiResponse!!.statusCode.equals("1")) {
                                    data[0].userId?.let { sharedHelper.putInUser("user_id", it) }
                                    loginViewModel.getUserInfoApi()
                                }
                            }
                            is UserInfoModel -> {
                                val data: ArrayList<UserInfoModel> =
                                    response.data as ArrayList<UserInfoModel>
                                sharedHelper.putInUser("member_id", data[0].getMemberID())

                                commonFunction.modelToGson(data.get(0))?.let {
                                    sharedHelper.putInUser(
                                        "user_info",
                                        it
                                    )
                                }
                                sharedHelper.putInUser(
                                    "milsec",
                                    System.currentTimeMillis().toString()
                                )
                                setIntent(HomeActivity::class.java, 3)

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


    private fun datePicker(){
        val now: Calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR),  // Initial year selection
            now.get(Calendar.MONTH),  // Initial month selection
            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        )
        if(dpd!=null && dpd!!.isAdded){
            dpd!!.dismiss()
        }
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR]
        cal[Calendar.MONTH]
        cal[Calendar.DAY_OF_MONTH]
        dpd?.setOkText(getString(R.string.ok))
        dpd?.setCancelText(getString(R.string.cancel))
        dpd?.maxDate = cal
        dpd!!.show(fragmentManagers!!, "Datepickerdialog")

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val datemonth= 1+monthOfYear
        var dates =""
        var datemonths =""
        if(dayOfMonth>9){
            dates = dayOfMonth.toString()
        }else{
            dates="0"+dayOfMonth
        }
        if(datemonth>9){
            datemonths = datemonth.toString()
        }else{
            datemonths="0"+datemonth
        }
        val date = "$dates-$datemonths-$year"
        loginViewModel.registerModel.value!!.dob = date
        binding.loginViewModel =loginViewModel

    }
}