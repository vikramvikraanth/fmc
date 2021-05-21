package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentFamilyBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.FamilyAdapter


class FamilyFragment : BaseFragment<FragmentFamilyBinding>() {

    var dataList : ArrayList<String> = ArrayList()

    override fun layoutId(): Int = R.layout.fragment_family


    override fun initView(mViewDataBinding: ViewDataBinding?) {
        dataList.clear()
        dataList.add(activity.resources.getString(R.string.ecard))
        dataList.add(activity.resources.getString(R.string.benefits))
        dataList.add(activity.resources.getString(R.string.pre_approval))
        binding.adapter = FamilyAdapter(dataList,object :Commoninterface{
            override fun onCallback(value: Any) {
                when(value as Int){
                    0->{
                        event.post(NavigateEvent("ecard"))
                        moveTOFragment(ECardFragment(),R.id.family_container)

                    }
                    1->{
                        event.post(NavigateEvent("benifite"))
                        moveTOFragment(BenefitsFragment(),R.id.family_container)

                    }
                    2->{
                        event.post(NavigateEvent("preappointment"))
                        moveTOFragment(PreApprovalsFragment(),R.id.family_container)

                    }
                }
            }

        })

    }

}