package com.kotlintest.app.view.bottomsheetFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.astrology.app.baseClass.BaseBottomSheetFragment
import com.kotlintest.app.R
import com.kotlintest.app.databinding.FragmentSelectBottomSheetBinding
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.SelectionListAdapter


class SelectBottomSheetFragment(var commoninterface: Commoninterface,var title:String,var dataList :ArrayList<Any>) : BaseBottomSheetFragment<FragmentSelectBottomSheetBinding>() {

    override fun layoutId(): Int = R.layout.fragment_select_bottom_sheet

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.title = title
        binding.adapter = SelectionListAdapter(dataList,commoninterface)

    }


}