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
import com.kotlintest.app.model.responseModel.FaqModel
import com.kotlintest.app.model.responseModel.HealthTipModel


class FaqListAdapter(val documentModel: ArrayList<Any>) : BaseAdapter<Any>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)

        if(documentModel.isEmpty()){
            return
        }
        when(documentModel[position]){
            is FaqModel.mobFAQ->{
                val data = documentModel[position] as FaqModel.mobFAQ
                binding.title = data.FAQuestion
                binding.description = data.FAAnswer
            }
            is HealthTipModel.MobHealthTip->{
                val data = documentModel[position] as HealthTipModel.MobHealthTip
                binding.title = data.HT_Heading
                binding.description = data.HT_Description
            }
        }
        binding.titleTxt.setOnClickListener {
            when(documentModel[holder.absoluteAdapterPosition]){
                is FaqModel.mobFAQ->{
                    val data = documentModel[holder.absoluteAdapterPosition] as FaqModel.mobFAQ
                    data.state = !data.state
                    binding.state = data.state
                    notifyItemChanged(holder.absoluteAdapterPosition)
                }
                is HealthTipModel.MobHealthTip->{
                    val data = documentModel[holder.absoluteAdapterPosition] as HealthTipModel.MobHealthTip
                    data.state = !data.state
                    binding.state = data.state
                    notifyItemChanged(holder.absoluteAdapterPosition)
                }
            }
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