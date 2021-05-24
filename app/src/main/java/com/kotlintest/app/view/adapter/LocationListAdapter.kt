package com.kotlintest.app.view.adapter

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.BenefitAdapterBinding
import com.kotlintest.app.databinding.LocationListAdapterBinding
import com.kotlintest.app.databinding.MainMenuAdapterBinding
import com.kotlintest.app.model.localModel.BenefitiesListModel
import com.kotlintest.app.model.responseModel.MedicalLocationModel


class LocationListAdapter(var documentModel: ArrayList<MedicalLocationModel.MedicalProviderResponse>) : BaseAdapter<MedicalLocationModel.MedicalProviderResponse>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
        binding.data = documentModel[position]

        if(documentModel.isEmpty()){
            return
        }



    }
    fun isfilter( data: ArrayList<MedicalLocationModel.MedicalProviderResponse>){
        documentModel  = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.location_list_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: LocationListAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as LocationListAdapterBinding
        }
        fun getBinding(): LocationListAdapterBinding {
            return databding!!
        }

    }
}