package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentLanguageBinding
import com.kotlintest.app.model.localModel.LanguageModel
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.LanguageAdapter


class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {


    override fun layoutId(): Int = R.layout.fragment_language

    override fun initView(mViewDataBinding: ViewDataBinding?) {
     val data =   ArrayList<LanguageModel>()
        data.add(LanguageModel("English",true))
        data.add(LanguageModel("عربى",false))
        binding.adapter = LanguageAdapter(data,object : Commoninterface{
            override fun onCallback(value: Any) {

            }

        })
    }


}