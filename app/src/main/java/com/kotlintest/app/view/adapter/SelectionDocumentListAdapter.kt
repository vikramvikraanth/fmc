package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.SelectDocumentListAdapterBinding
import com.kotlintest.app.model.localModel.ReimbursementformModel
import com.kotlintest.app.utility.`interface`.Commoninterface


class SelectionDocumentListAdapter(
    val documentModel: ArrayList<ReimbursementformModel.ListImage>,
    val commoninterface: Commoninterface
) : BaseAdapter<ReimbursementformModel.ListImage>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
        var count = position+1
        binding.title = count.toString()+", "+documentModel[holder.absoluteAdapterPosition].title+ " "+count+"."+documentModel[holder.absoluteAdapterPosition].MIMEType
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
                .inflate(R.layout.select_document_list_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: SelectDocumentListAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as SelectDocumentListAdapterBinding
        }
        fun getBinding(): SelectDocumentListAdapterBinding {
            return databding!!
        }

    }
}