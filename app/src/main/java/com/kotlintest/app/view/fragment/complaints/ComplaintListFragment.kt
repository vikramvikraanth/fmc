package com.kotlintest.app.view.fragment.complaints

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentComplaintListBinding
import com.kotlintest.app.view.adapter.ComplaintsListAdapter


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


}