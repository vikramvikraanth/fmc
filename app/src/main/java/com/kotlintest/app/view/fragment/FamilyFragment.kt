package com.kotlintest.app.view.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentFamilyBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.responseModel.*
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.activity.HomeActivity
import com.kotlintest.app.view.adapter.FamilyAdapter
import com.kotlintest.app.view.bottomsheetFragment.SelectBottomSheetFragment
import com.kotlintest.app.viewModel.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FamilyFragment : BaseFragment<FragmentFamilyBinding>(), View.OnClickListener {

    var dataList : ArrayList<String> = ArrayList()

    var listFamily = ArrayList<FamilyListModel.familyDetailsResponse>()

    override fun layoutId(): Int = R.layout.fragment_family

    private val familyViewModel by viewModel<FamilyViewModel>()

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.click = this
        val data =  commonFunction.gsonToModel(sharedHelper.getFromUser("user_info"),
            UserInfoModel::class.java) as UserInfoModel

        binding.title =data.getName()
        HomeActivity.memberid = data.getMemberID()

        dataList.clear()
        dataList.add(activity.resources.getString(R.string.ecard))
        dataList.add(activity.resources.getString(R.string.benefits))
        dataList.add(activity.resources.getString(R.string.pre_approval))
        binding.adapter = FamilyAdapter(dataList,object :Commoninterface{
            override fun onCallback(value: Any) {
                when(value as Int){
                    0->{
                        event.post(NavigateEvent("ecard"))
                        moveTOFragment(ECardFragment(),R.id.family_container)

                    }
                    1->{
                        event.post(NavigateEvent("benifite"))
                        moveTOFragment(BenefitsFragment(),R.id.family_container)

                    }
                    2->{
                        event.post(NavigateEvent("preappointment"))
                        moveTOFragment(PreApprovalsFragment(),R.id.family_container)

                    }
                }
            }

        })

        familyViewModel.response().observe(this,{
            processResponse(it)
        })
        familyViewModel.getFamilyApi()

    }

    override fun onClick(v: View?) {
        showSelectionSheet(
            listFamily as ArrayList<Any>,
            getString(R.string.select_family_member)
        )
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is FamilyListModel ->{
                        listFamily.clear()
                        listFamily.addAll(response.data.FamilyDetailsResponse)
                    }

                }
            }
            Status.LOADING -> {
                commonFunction.showLoader(activity)

            }
            Status.DISMISS -> {
                commonFunction.dismissLoader()

            }

        }

    }
    private fun showSelectionSheet(list:ArrayList<Any> , title :String){

        if(bottomSheet!=null && bottomSheet!!.isAdded){
            bottomSheet?.dismiss()
        }


        bottomSheet = SelectBottomSheetFragment(object :Commoninterface{
            override fun onCallback(value: Any) {

                when(value){
                    is FamilyListModel.familyDetailsResponse->{
                        binding.title =value.Name
                        HomeActivity.memberid = value.Memberid
                        bottomSheet?.dismiss()

                    }

                }

            }

        },title,list)
        bottomSheet?.show(fragmentManagers!!,"show_select")
    }


}