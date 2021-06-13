package com.kotlintest.app.view.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentPreApprovalsBinding
import com.kotlintest.app.model.responseModel.PreApprovalModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.PreAppovalsAdapter
import com.kotlintest.app.viewModel.FamilyViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class PreApprovalsFragment : BaseFragment<FragmentPreApprovalsBinding>(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private val familyViewModel by viewModel<FamilyViewModel>()

    private var isfrom = false


    override fun layoutId(): Int = R.layout.fragment_pre_approvals

    var data : ArrayList<PreApprovalModel.PreApprovalDetailsResponse> = ArrayList()
    var adapter : PreAppovalsAdapter ?= null
    var dpd: DatePickerDialog? =null

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        data.clear()
        adapter =PreAppovalsAdapter(data)
        binding.adapter = adapter
        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getpreApprovalsApiCall(familyViewModel.model)
        binding.click = this
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is PreApprovalModel ->{
                        response.data.preApprovalDetailsResponse?.let { data.addAll(it) }
                        binding.isvisible =data.isEmpty()

                            adapter?.notifyDataSetChanged()
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.start_edt->{
                isfrom = true
                datePicker()
            }
            R.id.end_edt->{
                if(familyViewModel.model.start.isEmpty()){
                    commonFunction.commonToast("Select start date first")
                    return
                }
                isfrom = false
                datePicker()
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

        dpd?.maxDate = cal
        dpd!!.show(fragmentManagers!!, "Datepickerdialog")

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val datemonth= 1+monthOfYear
        val date = "$dayOfMonth/$datemonth/$year"

        if(isfrom){
            familyViewModel.model.start = date
            binding.startEdt.setText(date)

        }else{
            familyViewModel.model.end = date
            binding.endEdt.setText(date)
        }
        if(familyViewModel.model.start.isNotEmpty() && familyViewModel.model.end.isNotEmpty()){
            familyViewModel.getpreApprovalsApiCall(familyViewModel.model)
        }
    }
}