package com.kotlintest.app.view.bottomsheetFragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.astrology.app.baseClass.BaseBottomSheetFragment
import com.kotlintest.app.R
import com.kotlintest.app.databinding.FragmentGenderBinding
import com.kotlintest.app.utility.`interface`.Commoninterface

class GenderFragment(val commonInterface: Commoninterface) : BaseBottomSheetFragment<FragmentGenderBinding>(), View.OnClickListener {



    override fun layoutId(): Int = R.layout.fragment_gender
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.female_txt->{
                commonInterface.onCallback("Female")
                dismiss()
            }
            R.id.male_txt ->{
                commonInterface.onCallback("Male")
                dismiss()
            }
            R.id.others_txt->{
                commonInterface.onCallback("Others")
                dismiss()
            }
        }
    }


}