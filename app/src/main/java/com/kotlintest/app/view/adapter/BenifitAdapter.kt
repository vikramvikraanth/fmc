package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.BenefitAdapterBinding
import com.kotlintest.app.databinding.MainMenuAdapterBinding
import com.kotlintest.app.model.localModel.BenefitiesListModel


class BenifitAdapter(val documentModel: ArrayList<BenefitiesListModel>) : BaseAdapter<BenefitiesListModel>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        binding.title = documentModel[position].title
        binding.adapter = documentModel[position].iPDeduction?.let { BenifitstepAdapter(it) }
        if(documentModel.isEmpty()){
            return
        }



    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.benefit_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: BenefitAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as BenefitAdapterBinding
        }
        fun getBinding(): BenefitAdapterBinding {
            return databding!!
        }

    }
}