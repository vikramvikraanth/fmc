package com.kotlintest.app.view.fragment.reimbursement

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentReimbursementListBinding
import com.kotlintest.app.view.adapter.ReimbursementAdapter


class ReimbursementListFragment : BaseFragment<FragmentReimbursementListBinding>() {



    override fun layoutId(): Int = R.layout.fragment_reimbursement_list
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        val data = ArrayList<String>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        binding.adapter = ReimbursementAdapter(data)
    }


}