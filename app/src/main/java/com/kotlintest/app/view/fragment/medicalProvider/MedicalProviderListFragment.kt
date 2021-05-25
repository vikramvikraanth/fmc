package com.kotlintest.app.view.fragment.medicalProvider

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentMedicalProviderListBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.responseModel.MedicalLocationModel
import com.kotlintest.app.view.adapter.LocationListAdapter
import com.kotlintest.app.view.fragment.reimbursement.ReimbursementFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MedicalProviderListFragment(var data:MedicalLocationModel?) : BaseFragment<FragmentMedicalProviderListBinding>() {





    override fun layoutId(): Int = R.layout.fragment_medical_provider_list

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.count = data!!.MedicalProviderListResponse.size.toString()
        try {
            binding.adapter = LocationListAdapter(data!!.MedicalProviderListResponse)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onStart() {
        super.onStart()
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessage(event: NavigateEvent) {
        when(event.imagePath){
            "medical_data" -> {
                moveTOFragment(MedicalProviderSearchFragment(data!!),R.id.Providers_contain)

            }

        }

    }
}