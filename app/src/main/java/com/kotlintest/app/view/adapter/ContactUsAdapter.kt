package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.R.layout.ecard_adapter
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.ContactUsAdapterBinding
import com.kotlintest.app.databinding.EcardAdapterBinding
import com.kotlintest.app.model.localModel.EcardLocalModel
import com.kotlintest.app.model.responseModel.ContactUsModel


class ContactUsAdapter(val documentModel: ArrayList<ContactUsModel.MobConatactu>) : BaseAdapter<ContactUsModel.MobConatactu>(documentModel) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).getBinding()
   //     binding.adapter = ImagePreviewAdapter(data)
        binding.data = documentModel[position]
        if(documentModel.isEmpty()){
            return
        }



    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contact_us_adapter, parent, false))
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: ContactUsAdapterBinding? = null

        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as ContactUsAdapterBinding
        }
        fun getBinding(): ContactUsAdapterBinding {
            return databding!!
        }

    }
}