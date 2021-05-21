package com.kotlintest.app.view.fragment.reimbursement

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentReimbursementListBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.ReimbursementAdapter
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ReimbursementListFragment : BaseFragment<FragmentReimbursementListBinding>() {



    override fun layoutId(): Int = R.layout.fragment_reimbursement_list
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        val data = ArrayList<String>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        binding.adapter = ReimbursementAdapter(data,object : Commoninterface{
            override fun onCallback(value: Any) {
                event.post(NavigateEvent("reimbursement"))
                moveTOFragment(ReimbursementDetailsFragment(),R.id.reimbursement_containter)
            }

        })


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
            "reimbursements" -> {
                moveTOFragment(ReimbursementFragment(),R.id.reimbursement_containter)

            }

        }

    }


}