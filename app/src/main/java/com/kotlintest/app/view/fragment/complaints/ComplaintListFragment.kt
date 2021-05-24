package com.kotlintest.app.view.fragment.complaints

import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentComplaintListBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.localModel.BenefitiesListModel
import com.kotlintest.app.model.responseModel.BenefitiesModel
import com.kotlintest.app.model.responseModel.ComplaintListModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.ComplaintsListAdapter
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderListFragment
import com.kotlintest.app.viewModel.ComplaintViewModel
import com.kotlintest.app.viewModel.FamilyViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class ComplaintListFragment : BaseFragment<FragmentComplaintListBinding>() {
    private val complaintViewModel by viewModel<ComplaintViewModel>()



    override fun layoutId(): Int = R.layout.fragment_complaint_list

    var adapter : ComplaintsListAdapter ? =null
    var complaintList : ArrayList<ComplaintListModel>  = ArrayList()

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        complaintList.clear()
        adapter = ComplaintsListAdapter(complaintList)
        binding.adapter =  adapter
        complaintViewModel.response().observe(this,{
            processResponse(it)
        })
        complaintViewModel.getComplaintListApi()
    }

    override fun onStart() {
        super.onStart()
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> ->{
                        complaintList.clear()
                        val data : ArrayList<ComplaintListModel> = response.data as ArrayList<ComplaintListModel>
                        complaintList.addAll(data)
                        adapter?.notifyDataSetChanged()

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


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessage(event: NavigateEvent) {
        when(event.imagePath){
            "complaints" -> {
                moveTOFragment(ComplaintsFragment(),R.id.complaints_containt)
                EventBus.getDefault().removeStickyEvent(event)

            }
            "update_complaint" -> {
                complaintViewModel.getComplaintListApi()
                EventBus.getDefault().removeStickyEvent(event)

            }

        }

    }

}