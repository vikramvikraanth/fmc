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
import com.kotlintest.app.databinding.PreapprovalAdapterBinding
import com.kotlintest.app.model.responseModel.PreApprovalModel


class PreAppovalsAdapter(val documentModel: ArrayList<PreApprovalModel.PreApprovalDetailsResponse>) : BaseAdapter<PreApprovalModel.PreApprovalDetailsResponse>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        if(documentModel.isEmpty()){
            return
        }
        binding.data = documentModel[position]


    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.preapproval_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: PreapprovalAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as PreapprovalAdapterBinding
        }
        fun getBinding(): PreapprovalAdapterBinding {
            return databding!!
        }

    }
}