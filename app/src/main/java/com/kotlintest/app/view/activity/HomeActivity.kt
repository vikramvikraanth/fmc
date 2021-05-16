package com.kotlintest.app.view.activity

import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivityHomeBinding
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.responseModel.LoginResponseModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.MainMenuAdapter
import com.kotlintest.app.view.fragment.BenefitsFragment
import com.kotlintest.app.view.fragment.ECardFragment
import com.kotlintest.app.view.fragment.FamilyFragment
import com.kotlintest.app.view.fragment.PreApprovalsFragment
import com.kotlintest.app.view.fragment.navigation.FragmentDrawer
import com.kotlintest.app.viewModel.LoginViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(), FragmentDrawer.FragmentDrawerListener,
    View.OnClickListener {
    private var titles: Array<String>? = null
    private var datalist: ArrayList<String>? = ArrayList()
    var drawerFragment: FragmentDrawer? = null
    var mDrawerLayout: DrawerLayout? = null
    override fun layoutId(): Int = R.layout.activity_home
    private val loginViewModel by viewModel<LoginViewModel>()
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        // drawer labels
        mDrawerLayout = binding.drawerLayout
        titles = activity.getResources().getStringArray(R.array.nav_drawer_labels)
        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
        drawerFragment!!.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, null)
        drawerFragment!!.setDrawerListener(this)
        binding.click= this

        // preparing navigation drawer items
        for (i in titles!!.indices) {
            datalist!!.add(titles!![i])
        }
        datalist!!.removeAt(datalist!!.size-1)
        binding.mainmenuAdapter= datalist?.let { MainMenuAdapter(it,object :Commoninterface{
            override fun onCallback(value: Any) {
                when(value as Int){
                     0->{
                         moveTOFragment(FamilyFragment(),R.id.home_conter)

                     }
                    1->{
                        moveTOFragment(BenefitsFragment(),R.id.home_conter)
                    }
                    3->{
                        moveTOFragment(PreApprovalsFragment(),R.id.home_conter)
                    }
                    5->{
                        moveTOFragment(ECardFragment(),R.id.home_conter)
                    }


                }
            }

        }) }
        loginViewModel.response().observe(this,{
            processResponse(it)
        })
    }

    override fun onDrawerItemSelected(view: View?, position: Int) {

        when(position){
            0->{
                moveTOFragment(FamilyFragment(),R.id.home_conter)
            }
            1->{
                moveTOFragment(BenefitsFragment(),R.id.home_conter)
            }
            3->{
                moveTOFragment(PreApprovalsFragment(),R.id.home_conter)
            }
            5->{
                moveTOFragment(ECardFragment(),R.id.home_conter)
            }
            11->{
                Logout()
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.menu_img->{
                mDrawerLayout!!.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun Logout() {
        val show = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(activity.resources.getString(R.string.app_name))
            .setContentText(activity.resources.getString(R.string.are_you_sure_wanna_logout))
            .setCancelText(activity.resources.getString(R.string.no))
            .setConfirmText(activity.resources.getString(R.string.yes))
            .showCancelButton(true)
            .setCancelClickListener { obj: SweetAlertDialog -> obj.dismiss() }
            .setConfirmClickListener { sDialog: SweetAlertDialog ->
                loginViewModel.logoutApiCall()
                sDialog.dismiss()
            }
        show.show()
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ArrayList<*> ->{
                        val data : ArrayList<LoginResponseModel> = response.data as ArrayList<LoginResponseModel>
                        data[0].apiResponse!!.message?.let { commonFunction.commonToast(it) }
                        if(data.get(0).apiResponse!!.statusCode.equals("1")){
                            setIntent(MainActivity::class.java,3)
                            sharedHelper.clearUser()
                        }
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
            "benifite"->{
                moveTOFragment(BenefitsFragment(),R.id.home_conter)
            }
            "preappointment"->{
                moveTOFragment(PreApprovalsFragment(),R.id.home_conter)
            }
            "ecard"->{
                moveTOFragment(ECardFragment(),R.id.home_conter)
            }
        }

    }
}