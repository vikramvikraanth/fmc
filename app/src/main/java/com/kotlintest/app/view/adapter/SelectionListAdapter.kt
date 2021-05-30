package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.ComplaintsListAdapterBinding
import com.kotlintest.app.databinding.SelectionListAdapterBinding
import com.kotlintest.app.model.responseModel.*
import com.kotlintest.app.utility.`interface`.Commoninterface


class SelectionListAdapter(val documentModel: ArrayList<Any>,var commoninterface: Commoninterface) : BaseAdapter<Any>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
        when(documentModel.get(position)){
            is CountryListModel.CountryResponse->{
                val countryResponse : CountryListModel.CountryResponse = documentModel[position] as CountryListModel.CountryResponse
                binding.title  =countryResponse.CountryName

            }
            is CityListModel.CityResponse->{
                val countryResponse : CityListModel.CityResponse = documentModel[position] as CityListModel.CityResponse
                binding.title  =countryResponse.CityName
            }
            is TreatCategoryListModel.CategoryResponse->{
                val countryResponse : TreatCategoryListModel.CategoryResponse = documentModel[position] as TreatCategoryListModel.CategoryResponse
                binding.title  =countryResponse.CategoryName
            }
            is CurrencyModel.CountryResponse->{
                val countryResponse : CurrencyModel.CountryResponse = documentModel[position] as CurrencyModel.CountryResponse
                binding.title  =countryResponse.CurrencyName
            }
            is StateListModel.EmiratesResponse->{
                val countryResponse : StateListModel.EmiratesResponse = documentModel[position] as StateListModel.EmiratesResponse
                binding.title  =countryResponse.EmiratesName
            }
            is SpecialListModel.SpecialityResponse ->{
                val countryResponse : SpecialListModel.SpecialityResponse = documentModel[position] as SpecialListModel.SpecialityResponse
                binding.title  =countryResponse.SpecialityName
            }
            is ProviderListModel.ProviderCategoryResponse ->{
                val countryResponse : ProviderListModel.ProviderCategoryResponse = documentModel[position] as ProviderListModel.ProviderCategoryResponse
                binding.title  =countryResponse.ProviderTypeName
            }
            is FamilyListModel.familyDetailsResponse->{
                val countryResponse : FamilyListModel.familyDetailsResponse = documentModel[position] as FamilyListModel.familyDetailsResponse
                binding.title  =countryResponse.Name
            }
        }

        holder.itemView.setOnClickListener {
            commoninterface.onCallback(documentModel[holder.absoluteAdapterPosition])
        }

        if(documentModel.isEmpty()){
            return
        }



    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.selection_list_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: SelectionListAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as SelectionListAdapterBinding
        }
        fun getBinding(): SelectionListAdapterBinding {
            return databding!!
        }

    }
}