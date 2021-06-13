package com.kotlintest.app.view.bottomsheetFragment

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import com.astrology.app.baseClass.BaseBottomSheetFragment
import com.kotlintest.app.R
import com.kotlintest.app.databinding.FragmentSelectBottomSheetBinding
import com.kotlintest.app.model.responseModel.*
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.SelectionListAdapter
import java.util.*
import kotlin.collections.ArrayList


class SelectBottomSheetFragment(
    var commoninterface: Commoninterface,
    var title: String,
    var dataList: ArrayList<Any>
) : BaseBottomSheetFragment<FragmentSelectBottomSheetBinding>() {

    override fun layoutId(): Int = R.layout.fragment_select_bottom_sheet

    var adapter: SelectionListAdapter? = null
    var temp: ArrayList<Any> = ArrayList()
    var listdata: ArrayList<Any> = ArrayList()
    var strName: String = ""
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.title = title
        listdata.addAll(dataList)
        adapter = SelectionListAdapter(listdata, commoninterface)
        binding.adapter = adapter
        binding.searchEdt.addTextChangedListener(passwordWatcher)
        temp = ArrayList()
    }

    private val passwordWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (temp.isNotEmpty()) {
                temp.clear()
            }
            if (s.isEmpty()) {
                adapter?.changeAdapterValue(dataList)
                return
            }
            for (item in (dataList as ArrayList<*>).indices){
                when (dataList[item]) {
                    is CountryListModel.CountryResponse -> {
                        val countryResponse: CountryListModel.CountryResponse = dataList[item] as CountryListModel.CountryResponse
                        strName = countryResponse.CountryName

                    }
                    is CityListModel.CityResponse -> {
                        val countryResponse: CityListModel.CityResponse = dataList[item] as CityListModel.CityResponse
                        strName = countryResponse.CityName
                    }
                    is TreatCategoryListModel.CategoryResponse -> {
                        val countryResponse: TreatCategoryListModel.CategoryResponse = dataList[item] as TreatCategoryListModel.CategoryResponse
                        strName = countryResponse.CategoryName
                    }
                    is CurrencyModel.CountryResponse -> {
                        val countryResponse: CurrencyModel.CountryResponse = dataList[item] as CurrencyModel.CountryResponse
                        strName = countryResponse.CurrencyName
                    }
                    is StateListModel.EmiratesResponse -> {
                        val countryResponse: StateListModel.EmiratesResponse = dataList[item] as StateListModel.EmiratesResponse
                        strName = countryResponse.EmiratesName
                    }
                    is SpecialListModel.SpecialityResponse -> {
                        val countryResponse: SpecialListModel.SpecialityResponse = dataList[item] as SpecialListModel.SpecialityResponse
                        strName = countryResponse.SpecialityName
                    }
                    is ProviderListModel.ProviderCategoryResponse -> {
                        val countryResponse: ProviderListModel.ProviderCategoryResponse = dataList[item] as ProviderListModel.ProviderCategoryResponse
                        strName = countryResponse.ProviderTypeName
                    }
                    is FamilyListModel.familyDetailsResponse -> {
                        val countryResponse: FamilyListModel.familyDetailsResponse = dataList[item] as FamilyListModel.familyDetailsResponse
                        strName = countryResponse.Name
                    }
                    is ReimbursementTypeListModel.reimbursementFileType -> {
                        val countryResponse: ReimbursementTypeListModel.reimbursementFileType =
                            dataList[item] as ReimbursementTypeListModel.reimbursementFileType
                        strName = countryResponse.FileTypeShortName
                    }
                    is ComplaintTypeListModel.ComplaintTypeResponse -> {
                        val countryResponse: ComplaintTypeListModel.ComplaintTypeResponse = dataList[item] as ComplaintTypeListModel.ComplaintTypeResponse
                        strName = countryResponse.ComplaintName
                    }
                    is String-> {
                        strName = dataList[item] as String
                    }
                }
                if (strName.toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))) {
                    temp.add(dataList[item])
                }
                adapter?.changeAdapterValue(temp)

            }



        }
    }
}