package com.kotlintest.app.view.bottomsheetFragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.astrology.app.baseClass.BaseBottomSheetFragment
import com.kotlintest.app.R
import com.kotlintest.app.databinding.FragmentBrowseTypeBinding
import com.kotlintest.app.databinding.FragmentGenderBinding
import com.kotlintest.app.utility.`interface`.Commoninterface

class BrowseTypeFragment(val commonInterface: Commoninterface) : BaseBottomSheetFragment<FragmentBrowseTypeBinding>(), View.OnClickListener {



    override fun layoutId(): Int = R.layout.fragment_browse_type
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.female_txt->{
                commonInterface.onCallback("image")
                dismiss()
            }
            R.id.male_txt ->{
                commonInterface.onCallback("pdf")
                dismiss()
            }

        }
    }


}