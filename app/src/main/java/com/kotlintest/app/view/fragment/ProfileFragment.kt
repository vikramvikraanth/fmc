package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentProfileBinding
import com.kotlintest.app.model.responseModel.UserInfoModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {




    override fun layoutId(): Int = R.layout.fragment_profile

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        val data =  commonFunction.gsonToModel(sharedHelper.getFromUser("user_info"),
            UserInfoModel::class.java)
        binding.datas = data as UserInfoModel?
        binding.toolbar.setOnClickListener {
            fragmentManagers?.popBackStackImmediate()
        }
    }

}