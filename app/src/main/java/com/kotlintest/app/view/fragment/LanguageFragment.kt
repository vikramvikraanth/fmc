package com.kotlintest.app.view.fragment

import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentLanguageBinding
import com.kotlintest.app.model.localModel.LanguageModel
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.activity.MainActivity
import com.kotlintest.app.view.activity.SplashActivity
import com.kotlintest.app.view.adapter.LanguageAdapter


class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {

    var previouseSelect :Int =0

    override fun layoutId(): Int = R.layout.fragment_language

    override fun initView(mViewDataBinding: ViewDataBinding?) {
     val data =   ArrayList<LanguageModel>()
        data.add(LanguageModel("English",true))
        data.add(LanguageModel("عربى",false))

        if(sharedHelper.getFromUser("lang").isEmpty() || sharedHelper.getFromUser("lang").equals("en")){

            previouseSelect=0
        }
        if(sharedHelper.getFromUser("lang").equals("ar")){
            previouseSelect=1

        }

        binding.adapter = LanguageAdapter(data,object : Commoninterface{
            override fun onCallback(value: Any) {

                when(data[value as Int].title){
                    "English"->{
                        sharedHelper.putInUser("lang","en")
                        sharedHelper.putInUser("lang_type","EN")

                        setIntent(SplashActivity::class.java, 3)

                    }
                    "عربى"->{
                        sharedHelper.putInUser("lang","ar")
                        sharedHelper.putInUser("lang_type","AR")

                        setIntent(SplashActivity::class.java, 3)
                    }
                }

            }

        },previouseSelect)
    }


}