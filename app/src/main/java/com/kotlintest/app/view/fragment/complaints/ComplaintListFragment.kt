package com.kotlintest.app.view.fragment.complaints

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentComplaintListBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.view.adapter.ComplaintsListAdapter
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ComplaintListFragment : BaseFragment<FragmentComplaintListBinding>() {



    override fun layoutId(): Int = R.layout.fragment_complaint_list

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        val data = ArrayList<String>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        binding.adapter = ComplaintsListAdapter(data)
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
            "complaints" -> {
                moveTOFragment(ComplaintsFragment(),R.id.complaints_containt)

            }

        }

    }

}