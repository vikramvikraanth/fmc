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


class MainMenuAdapter(val documentModel: ArrayList<String>,val commoninterface: Commoninterface) : BaseAdapter<String>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        if(documentModel.isEmpty()){
            return
        }
        binding.title = get(position)
        holder.itemView.setOnClickListener {
            commoninterface.onCallback(holder.adapterPosition)
        }
        when(position){
            0->{
                binding.imageMenu.setImageResource(R.drawable.ic_family)
                binding.description = "3 Features"
            }
            1->{
                binding.imageMenu.setImageResource(R.drawable.ic_benifits)
                binding.description = ""
            }
            2->{
                binding.imageMenu.setImageResource(R.drawable.ic_medical)
                binding.description = ""
            }
            3->{
                binding.imageMenu.setImageResource(R.drawable.ic_pre_approval)
                binding.description = ""
            }
            4->{
                binding.imageMenu.setImageResource(R.drawable.ic_reimburse)
                binding.description = ""

            }
            5->{
                binding.imageMenu.setImageResource(R.drawable.ic_e_card)
                binding.description = ""

            }
            6->{
                binding.imageMenu.setImageResource(R.drawable.ic_compliants)
                binding.description = ""

            }
            7->{
                binding.imageMenu.setImageResource(R.drawable.ic_faq)
                binding.description = ""

            }
            8->{
                binding.imageMenu.setImageResource(R.drawable.ic_about)
                binding.description = ""

            }
            9->{
                binding.imageMenu.setImageResource(R.drawable.ic_language)
                binding.description = ""

            }

            10->{
                binding.imageMenu.setImageResource(R.drawable.ic_health_tip)
                binding.description = ""

            }
            11->{
                binding.imageMenu.setImageResource(R.drawable.ic_health_tip)
                binding.description = ""

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