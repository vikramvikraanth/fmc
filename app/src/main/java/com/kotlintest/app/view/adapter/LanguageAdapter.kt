package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.LanguageAdapterBinding
import com.kotlintest.app.databinding.MainMenuAdapterBinding
import com.kotlintest.app.model.localModel.LanguageModel
import com.kotlintest.app.utility.`interface`.Commoninterface


class LanguageAdapter(val documentModel: ArrayList<LanguageModel>, val commoninterface: Commoninterface,var previouseSelect :Int) : BaseAdapter<LanguageModel>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        if(documentModel.isEmpty()){
            return
        }
        binding.state = previouseSelect ==position
        binding.title = get(position)
        holder.itemView.setOnClickListener {
            previouseSelect = holder.absoluteAdapterPosition
            notifyDataSetChanged()
            commoninterface.onCallback(previouseSelect)
        }


    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.language_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: LanguageAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as LanguageAdapterBinding
        }
        fun getBinding(): LanguageAdapterBinding {
            return databding!!
        }

    }
}