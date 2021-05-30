package com.kotlintest.app.view.fragment.reimbursement

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentReimbursementListBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.responseModel.EcardModel
import com.kotlintest.app.model.responseModel.ReimbursementListModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.ReimbursementAdapter
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderListFragment
import com.kotlintest.app.viewModel.MedicalProviderViewModel
import com.kotlintest.app.viewModel.ReimbursementViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReimbursementListFragment : BaseFragment<FragmentReimbursementListBinding>() {

    private val reimbursementViewModel by viewModel<ReimbursementViewModel>()

    private var listModel = ArrayList<ReimbursementListModel.ReimbursementResponse>()

    private var adapter: ReimbursementAdapter ?=null

    override fun layoutId(): Int = R.layout.fragment_reimbursement_list
    override fun initView(mViewDataBinding: ViewDataBinding?) {

        adapter = ReimbursementAdapter(listModel,object : Commoninterface{
            override fun onCallback(value: Any) {
              //  event.post(NavigateEvent("reimbursement"))
                //  moveTOFragment(ReimbursementDetailsFragment(),R.id.reimbursement_containter)
            }

        })
        binding.adapter = adapter
        reimbursementViewModel.getReimbursementListApi()


        reimbursementViewModel.response().observe(this, Observer {
            processResponse(it)
        })
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessage(event: NavigateEvent) {
        when(event.imagePath){
            "reimbursements" -> {
                moveTOFragment(ReimbursementFragment(),R.id.reimbursement_containter)

            }

        }

    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ReimbursementListModel ->{
                        listModel.addAll(response.data.ReimbursementListResponse)
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
}