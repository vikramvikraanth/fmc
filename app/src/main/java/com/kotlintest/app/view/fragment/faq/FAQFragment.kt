package com.kotlintest.app.view.fragment.faq

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentFAQBinding
import com.kotlintest.app.model.localModel.FaqModel
import com.kotlintest.app.view.adapter.FaqListAdapter


class FAQFragment : BaseFragment<FragmentFAQBinding>() {



    override fun layoutId(): Int = R.layout.fragment_f_a_q

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        val data = ArrayList<FaqModel>()
        data.add(FaqModel(false,"What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        data.add(FaqModel(false,"What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        data.add(FaqModel(false,"What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        data.add(FaqModel(false,"What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        binding.adapter = FaqListAdapter(data)


    }


}