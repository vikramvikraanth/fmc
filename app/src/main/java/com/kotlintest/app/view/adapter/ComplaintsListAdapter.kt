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
import com.kotlintest.app.model.responseModel.ComplaintListModel


class ComplaintsListAdapter(val documentModel: ArrayList<ComplaintListModel>) : BaseAdapter<ComplaintListModel>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
        binding.data = documentModel[position]

        if(documentModel.isEmpty()){
            return
        }



    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.complaints_list_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: ComplaintsListAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as ComplaintsListAdapterBinding
        }
        fun getBinding(): ComplaintsListAdapterBinding {
            return databding!!
        }

    }
}