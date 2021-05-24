package com.kotlintest.app.view.fragment.complaints

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentComplaintsBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.responseModel.ComplaintResponseModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.viewModel.ComplaintViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ComplaintsFragment : BaseFragment<FragmentComplaintsBinding>(),
    DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private val complaintViewModel by viewModel<ComplaintViewModel>()

    var dpd: DatePickerDialog? =null


    override fun layoutId(): Int = R.layout.fragment_complaints

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        binding.viewModel = complaintViewModel

        complaintViewModel.response().observe(this,{
            processResponse(it)
        })
        binding.click = this


    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ComplaintResponseModel ->{
                        commonFunction.commonToast(response.data.ApiResponse.Details)
                        event.post(NavigateEvent("update_complaint"))
                        fragmentManagers?.popBackStackImmediate()
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
        dpd!!.show(fragmentManagers!!, "Datepickerdialog")

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val datemonth= 1+monthOfYear
        val date = "$datemonth/$dayOfMonth/$year"
        complaintViewModel.complaintmodel.datevistor = date
        binding.viewModel =complaintViewModel


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reset_btn->{
                complaintViewModel.complaintmodel.complaint =""
                complaintViewModel.complaintmodel.datevistor =""
                complaintViewModel.complaintmodel.providerName=""
                complaintViewModel.complaintmodel.subject=""
                binding.viewModel =complaintViewModel
            }
            R.id.visit_date_edt->{
                datePicker()
            }
        }

    }
}