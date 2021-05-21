package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.FaqListAdapterBinding
import com.kotlintest.app.model.localModel.FaqModel


class FaqListAdapter(val documentModel: ArrayList<FaqModel>) : BaseAdapter<FaqModel>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)

        if(documentModel.isEmpty()){
            return
        }
        binding.data = documentModel.get(position)
        binding.titleTxt.setOnClickListener {
            documentModel.get(holder.absoluteAdapterPosition).state = ! documentModel.get(holder.absoluteAdapterPosition).state
            binding.data = documentModel.get(holder.absoluteAdapterPosition)
        }



    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.faq_list_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: FaqListAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as FaqListAdapterBinding
        }
        fun getBinding(): FaqListAdapterBinding {
            return databding!!
        }

    }
}