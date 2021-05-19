package com.kotlintest.app.view.fragment.medicalProvider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentMedicalProviderListBinding
import com.kotlintest.app.view.adapter.LocationListAdapter


class MedicalProviderListFragment : BaseFragment<FragmentMedicalProviderListBinding>() {





    override fun layoutId(): Int = R.layout.fragment_medical_provider_list

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        val data = ArrayList<String>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        binding.adapter = LocationListAdapter(data)



    }


}