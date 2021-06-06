package com.kotlintest.app.view.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {


    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.arabic_btn->{
                sharedHelper.putInUser("lang","ar")
                setLocale(sharedHelper.getFromUser("lang"))
                sharedHelper.putInUser("lang_type","AR")

            }
            R.id.englist_btn->{
                sharedHelper.putInUser("lang","en")
                setLocale(sharedHelper.getFromUser("lang"))
                sharedHelper.putInUser("lang_type","EN")

            }
        }


        setIntent(LoginActivity::class.java,0)

    }
}
