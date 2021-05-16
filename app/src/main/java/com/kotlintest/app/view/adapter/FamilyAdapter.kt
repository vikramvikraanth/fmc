package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.MainMenuAdapterBinding
import com.kotlintest.app.utility.`interface`.Commoninterface


class FamilyAdapter(val documentModel: ArrayList<String>,val commoninterface: Commoninterface) : BaseAdapter<String>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        holder.itemView.setOnClickListener {
            commoninterface.onCallback(holder.absoluteAdapterPosition)
        }
        if(documentModel.isEmpty()){
            return
        }
        binding.title = get(position)
        binding.description = ""

        when(position){
            0->{
                binding.imageMenu.setImageResource(R.drawable.ic_e_card)
            }
            1->{
                binding.imageMenu.setImageResource(R.drawable.ic_benifits)

            }
            2->{
                binding.imageMenu.setImageResource(R.drawable.ic_pre_approval)

            }

        }


    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.main_menu_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: MainMenuAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as MainMenuAdapterBinding
        }
        fun getBinding(): MainMenuAdapterBinding {
            return databding!!
        }

    }
}