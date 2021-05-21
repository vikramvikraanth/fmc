package com.kotlintest.app.view.fragment.medicalProvider

import android.view.View
import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentMedicalProviderBinding
import com.kotlintest.app.model.eventBus.NavigateEvent


class MedicalProviderFragment : BaseFragment<FragmentMedicalProviderBinding>(),
    View.OnClickListener {



    override fun layoutId(): Int = R.layout.fragment_medical_provider

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        binding.click = this

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reset_btn->{

            }
            R.id.search_btn->{
                event.post(NavigateEvent("medical"))
                moveTOFragment(MedicalProviderListFragment(),R.id.provider_container)

            }
        }
    }


}