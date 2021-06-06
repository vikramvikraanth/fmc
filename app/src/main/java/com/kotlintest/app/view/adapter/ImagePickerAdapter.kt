package com.kotlintest.app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseAdapter
import com.kotlintest.app.databinding.ImagePickerAdapterBinding
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.utility.imagePicker.Files.Media

class ImagePickerAdapter constructor(val arraydata : ArrayList<Media>, val commonInterface: Commoninterface,var type :String) : BaseAdapter<Media>(arraydata) {


    override fun onBindViewHolderBase(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ImagePickerAdapterViewHolder).getBinding()
        if(arraydata.isEmpty()){
            return
        }
        if(type=="image"){
            binding.image = arraydata[position].path
        }else{
            binding.image = null
        }
        binding.isSelection = arraydata[position].isSelection
        holder.itemView.setOnClickListener {
            /*arraydata[position-1].isSelection( !arraydata[position-1].isSelection)
            binding.isSelection = arraydata[position-1].isSelection*/
            commonInterface.onCallback(holder.adapterPosition)

        }

    }
    override fun onCreateViewHolderBase(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImagePickerAdapterViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.image_picker_adapter, parent, false))
    }
    class ImagePickerAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var databding: ImagePickerAdapterBinding
        init {
            databding = DataBindingUtil.bind<ViewDataBinding>(itemView) as ImagePickerAdapterBinding

        }
        fun getBinding(): ImagePickerAdapterBinding {
            return databding
        }

    }
}