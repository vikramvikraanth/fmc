package com.kotlintest.app.view.fragment.reimbursement

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentReimbursementBinding
import com.kotlintest.app.model.responseModel.CountryListModel
import com.kotlintest.app.model.responseModel.CurrencyModel
import com.kotlintest.app.model.responseModel.TreatCategoryListModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.bottomsheetFragment.SelectBottomSheetFragment
import com.kotlintest.app.viewModel.ReimbursementViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class ReimbursementFragment : BaseFragment<FragmentReimbursementBinding>(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private var listCountry : ArrayList<CountryListModel.CountryResponse> = ArrayList()
    private var listcategory : ArrayList<TreatCategoryListModel.CategoryResponse> = ArrayList()
    private var listcurrency : ArrayList<CurrencyModel.CountryResponse> = ArrayList()

    private val reimbursementViewModel by viewModel<ReimbursementViewModel>()

    var dpd: DatePickerDialog? =null


    override fun layoutId(): Int = R.layout.fragment_reimbursement

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        binding.click = this
        binding.viewModel =reimbursementViewModel

        reimbursementViewModel.response().observe(this,{
            processResponse(it)
        })
        reimbursementViewModel.getTreatCategoryListApi()
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.category_edt->{
                showSelectionSheet(listcategory as ArrayList<Any>,"Select Category")
            }
            R.id.country_edt->{
                if(reimbursementViewModel.reimbursementformModel.category.isEmpty()){
                    commonFunction.commonToast("Select your Category")
                    return
                }
                showSelectionSheet(listCountry as ArrayList<Any>,"Select Country")


            }
            R.id.currency_edt->{

                if(reimbursementViewModel.reimbursementformModel.countryid.isEmpty()){
                    commonFunction.commonToast("Select your Category")
                    return
                }
                showSelectionSheet(listcurrency as ArrayList<Any>,"Select Currency")

            }
            R.id.date_edit->{
                datePicker()
            }
        }
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is CountryListModel->{
                        listCountry.clear()
                        listCountry.addAll(response.data.CountryListResponse)
                    }
                    is TreatCategoryListModel ->{
                        listcategory.clear()
                        listcategory.addAll(response.data.CategoryListResponse)
                    }
                    is CurrencyModel ->{
                        listcurrency.clear()
                        listcurrency.addAll(response.data.CountryListResponse)
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
    private fun showSelectionSheet(list:ArrayList<Any> , title :String){

        if(bottomSheet!=null && bottomSheet!!.isAdded){
            bottomSheet?.dismiss()
        }


        bottomSheet = SelectBottomSheetFragment(object : Commoninterface {
            override fun onCallback(value: Any) {

                when(value){
                    is CountryListModel.CountryResponse->{
                        reimbursementViewModel.getCurrencyListApi()
                        reimbursementViewModel.reimbursementformModel.country = value.CountryName
                        reimbursementViewModel.reimbursementformModel.countryid = value.CountryId
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is TreatCategoryListModel.CategoryResponse->{
                        reimbursementViewModel.getCountryListApi()
                        reimbursementViewModel.reimbursementformModel.category = value.CategoryName
                        reimbursementViewModel.reimbursementformModel.categoryid = value.CategoryID
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is CurrencyModel.CountryResponse->{
                        reimbursementViewModel.reimbursementformModel.currency = value.CurrencyName
                        reimbursementViewModel.reimbursementformModel.currencyid = value.CurrencyID
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }

                }

            }

        },title,list)
        bottomSheet?.show(fragmentManagers!!,"show_select")
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
        val date = "$dayOfMonth/$datemonth/$year"
        reimbursementViewModel.reimbursementformModel.serviceperiod = date
        binding.viewModel =reimbursementViewModel

    }
}