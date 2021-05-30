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
import com.kotlintest.app.databinding.ReimbursementAdapterBinding
import com.kotlintest.app.model.responseModel.PreApprovalModel
import com.kotlintest.app.model.responseModel.ReimbursementListModel
import com.kotlintest.app.utility.`interface`.Commoninterface


class ReimbursementAdapter(val documentModel: ArrayList<ReimbursementListModel.ReimbursementResponse>, val commoninterface: Commoninterface) : BaseAdapter<ReimbursementListModel.ReimbursementResponse>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        binding.data = documentModel[position]
        holder.itemView.setOnClickListener {
            commoninterface.onCallback(holder.absoluteAdapterPosition)
        }
        if(documentModel.isEmpty()){
            return
        }

    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.reimbursement_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: ReimbursementAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as ReimbursementAdapterBinding
        }
        fun getBinding(): ReimbursementAdapterBinding {
            return databding!!
        }

    }
}