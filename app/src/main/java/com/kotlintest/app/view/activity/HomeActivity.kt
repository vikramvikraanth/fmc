package com.kotlintest.app.view.activity

import android.view.View
import android.widget.TextView
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
import com.kotlintest.app.model.responseModel.UserInfoModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.MainMenuAdapter
import com.kotlintest.app.view.fragment.*
import com.kotlintest.app.view.fragment.aboutUs.AboutUsFragment
import com.kotlintest.app.view.fragment.complaints.ComplaintListFragment
import com.kotlintest.app.view.fragment.complaints.ComplaintsFragment
import com.kotlintest.app.view.fragment.contactUs.ContactUsFragment
import com.kotlintest.app.view.fragment.faq.FAQFragment
import com.kotlintest.app.view.fragment.healthTips.HealthTipsFragment
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderFragment
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderListFragment
import com.kotlintest.app.view.fragment.medicalProvider.MedicalProviderSearchFragment
import com.kotlintest.app.view.fragment.navigation.FragmentDrawer
import com.kotlintest.app.view.fragment.reimbursement.ReimbursementDetailsFragment
import com.kotlintest.app.view.fragment.reimbursement.ReimbursementFragment
import com.kotlintest.app.view.fragment.reimbursement.ReimbursementListFragment
import com.kotlintest.app.viewModel.LoginViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text
import timber.log.Timber
import java.util.concurrent.TimeUnit


class HomeActivity : BaseActivity<ActivityHomeBinding>(), FragmentDrawer.FragmentDrawerListener,
    View.OnClickListener {
    private var titles: Array<String>? = null
    private var datalist: ArrayList<String>? = ArrayList()
    var drawerFragment: FragmentDrawer? = null
    var mDrawerLayout: DrawerLayout? = null
    override fun layoutId(): Int = R.layout.activity_home
    private val loginViewModel by viewModel<LoginViewModel>()
    var nameTxt : TextView ? =null
    companion object {
        var memberid :String =""
    }
    var datasv =UserInfoModel()
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        // drawer labels
        mDrawerLayout = binding.drawerLayout
        titles = activity.getResources().getStringArray(R.array.nav_drawer_labels)
        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
        drawerFragment!!.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, null)
        nameTxt = mDrawerLayout!!.findViewById(R.id.name_txt)
        drawerFragment!!.setDrawerListener(this)
        binding.click= this
        binding.title ="Home"
        binding.iconstate = true
        binding.isvisible = false
        val data =  commonFunction.gsonToModel(sharedHelper.getFromUser("user_info"),UserInfoModel::class.java)
         datasv = (data as UserInfoModel?)!!
        binding.datas = datasv
        nameTxt?.setText(datasv.getName())
        memberid =datasv!!.getMemberID()
        //isCheckLogout()
        // preparing navigation drawer items
        for (i in titles!!.indices) {
            datalist!!.add(titles!![i])
        }
        datalist!!.removeAt(datalist!!.size - 1)
        binding.mainmenuAdapter= datalist?.let { MainMenuAdapter(it, object : Commoninterface {
            override fun onCallback(value: Any) {

                HomeActivity.memberid = datasv.getMemberID()
                when (value as Int) {
                    0 -> {
                        moveTOFragment(FamilyFragment(), R.id.home_conter)
                        binding.isvisible = false

                        binding.title =getString(R.string.my_Family)
                    }
                    1 -> {
                        moveTOFragment(BenefitsFragment(), R.id.home_conter)
                        binding.isvisible = false

                        binding.title =getString(R.string.benefits)
                    }
                    2 -> {
                        moveTOFragment(MedicalProviderFragment(), R.id.home_conter)
                        binding.title =getString(R.string.medical_provider)
                        binding.isvisible = false
                    }
                    3 -> {
                        moveTOFragment(PreApprovalsFragment(), R.id.home_conter)
                        binding.isvisible = false

                        binding.title =getString(R.string.pre_approval)
                    }
                    4 -> {
                        moveTOFragment(ReimbursementListFragment(), R.id.home_conter)
                        binding.isvisible = true
                        binding.iconstate = true
                        binding.title =getString(R.string.reimbursement)
                    }
                    5 -> {
                        moveTOFragment(ECardFragment(), R.id.home_conter)
                        binding.isvisible = false

                        binding.title =getString(R.string.ecard)
                    }
                    6 -> {
                        moveTOFragment(ComplaintListFragment(), R.id.home_conter)
                        binding.isvisible = true
                        binding.iconstate = true
                        binding.title =getString(R.string.compliant)
                    }
                    7 -> {
                        moveTOFragment(FAQFragment(), R.id.home_conter)
                        binding.isvisible = false

                        binding.title =getString(R.string.faq)
                    }
                    8 -> {
                        moveTOFragment(AboutUsFragment(), R.id.home_conter)
                        binding.title =getString(R.string.about_us)

                        binding.isvisible = false


                    }
                    9 -> {
                       // moveTOFragment(AboutUsFragment(), R.id.home_conter)

                        moveTOFragment(LanguageFragment(), R.id.home_conter)
                        binding.title =getString(R.string.language)

                        binding.isvisible = false
                    }
                    10 -> {
                        moveTOFragment(HealthTipsFragment(), R.id.home_conter)
                        binding.title =getString(R.string.health_tips)
                        binding.isvisible = false
                    }
                    11 -> {
                        moveTOFragment(ContactUsFragment(), R.id.home_conter)
                        binding.title =getString(R.string.contact_us)
                        binding.isvisible = false
                    }


                }
                //setTitle()

            }

        }) }
        loginViewModel.response().observe(this, {
            processResponse(it)
        })
    }

    override fun onDrawerItemSelected(view: View?, position: Int) {
        HomeActivity.memberid = datasv.getMemberID()
        when(position){
            0 -> {
                moveTOFragment(FamilyFragment(), R.id.home_conter)
                binding.isvisible = false

                binding.title =getString(R.string.my_Family)
            }
            1 -> {
                moveTOFragment(BenefitsFragment(), R.id.home_conter)
                binding.isvisible = false

                binding.title =getString(R.string.benefits)
            }
            2 -> {
                moveTOFragment(MedicalProviderFragment(), R.id.home_conter)
                binding.title =getString(R.string.medical_provider)
                binding.isvisible = false
            }
            3 -> {
                moveTOFragment(PreApprovalsFragment(), R.id.home_conter)
                binding.isvisible = false

                binding.title =getString(R.string.pre_approval)
            }
            4 -> {
                moveTOFragment(ReimbursementListFragment(), R.id.home_conter)
                binding.isvisible = true
                binding.iconstate = true
                binding.title =getString(R.string.reimbursement)
            }
            5 -> {
                moveTOFragment(ECardFragment(), R.id.home_conter)
                binding.isvisible = false

                binding.title =getString(R.string.ecard)
            }
            6 -> {
                moveTOFragment(ComplaintListFragment(), R.id.home_conter)
                binding.isvisible = true
                binding.iconstate = true
                binding.title =getString(R.string.compliant)
            }
            7 -> {
                moveTOFragment(FAQFragment(), R.id.home_conter)
                binding.isvisible = false

                binding.title =getString(R.string.faq)
            }
            8 -> {
                moveTOFragment(AboutUsFragment(), R.id.home_conter)
                binding.title =getString(R.string.about_us)

                binding.isvisible = false
            }
            9 -> {
                moveTOFragment(LanguageFragment(), R.id.home_conter)
                binding.title =getString(R.string.language)

                binding.isvisible = false
            }
            10 -> {
                moveTOFragment(HealthTipsFragment(), R.id.home_conter)
                binding.title =getString(R.string.health_tips)
                binding.isvisible = false
            }
            11 -> {
                moveTOFragment(ContactUsFragment(), R.id.home_conter)
                binding.title =getString(R.string.contact_us)
                binding.isvisible = false
            }
            12 -> {
                Logout()
            }
        }
        //setTitle()
        mDrawerLayout?.closeDrawer(GravityCompat.START)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.menu_img -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
            }
            R.id.other_img -> {
                println("enter the title"+binding.titleTxt.text.toString())
                when (binding.titleTxt.text.toString()) {
                    "Medical Provider" -> {
                        binding.isvisible = false
                        binding.title = getString(R.string.medical_provider)
                        event.post(NavigateEvent("medical_data"))

                    }
                    "Reimbursement" -> {
                        event.post(NavigateEvent("reimbursements"))
                        binding.isvisible = false
                        binding.title = getString(R.string.reimbursement)

                    }
                    "Compliant" -> {
                        binding.title = getString(R.string.compliant)
                        binding.isvisible = false
                        event.post(NavigateEvent("complaints"))

                    }
                }

            }
            R.id.profile_relativ_rly->{
                moveTOFragment(ProfileFragment(), R.id.drawer_layout)

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
                    is ArrayList<*> -> {
                        val data: ArrayList<LoginResponseModel> =
                            response.data as ArrayList<LoginResponseModel>
                        data[0].apiResponse!!.message?.let { commonFunction.commonToast(it) }
                        if (data.get(0).apiResponse!!.statusCode.equals("1")) {
                            setIntent(MainActivity::class.java, 3)
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
            "benifite" -> {
                binding.isvisible = false

                binding.title =getString(R.string.benefits)
            }
            "preappointment" -> {
                binding.isvisible = false

                binding.title =getString(R.string.pre_approval)
            }
            "ecard" -> {
                binding.isvisible = false

                binding.title =getString(R.string.ecard)
            }
            "medical" -> {
                binding.title =getString(R.string.medical_provider)
                binding.isvisible = true
                binding.iconstate = false
            }
            "reimbursement" -> {
                binding.isvisible = false

                binding.title =getString(R.string.reimbursement)
            }
        }

    }

    override fun onBackPressed() {
      //  super.onBackPressed()
        val fragment = fragmentManager.fragments
        println("enter fragment manger"+fragment.get(fragment.size-1).javaClass.canonicalName)
        when(fragment.get(fragment.size-1).javaClass.canonicalName){
            BenefitsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            FamilyFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ECardFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            PreApprovalsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ReimbursementDetailsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ReimbursementFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ReimbursementListFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            MedicalProviderFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            MedicalProviderListFragment(null).javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            HealthTipsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            FAQFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ContactUsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            LanguageFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ComplaintListFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            ComplaintsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            AboutUsFragment().javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()

            }
            MedicalProviderSearchFragment(null).javaClass.canonicalName ->{
                fragmentManager.popBackStackImmediate()
            }else ->{
               super.onBackPressed()
            }
        }
        setTitle()

    }

    private fun setTitle() {
        val fragment = fragmentManager.fragments
        when(fragment.get(fragment.size-1).javaClass.canonicalName){
            BenefitsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.benefits)
                binding.isvisible = false


            }
            FamilyFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.my_Family)

                binding.isvisible = false

            }
            ECardFragment().javaClass.canonicalName ->{

                binding.title =getString(R.string.ecard)
                binding.isvisible = false

            }
            LanguageFragment().javaClass.canonicalName ->{

                binding.title =getString(R.string.language)
                binding.isvisible = false

            }
            PreApprovalsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.pre_approval)

                binding.isvisible = false

            }
            ReimbursementDetailsFragment().javaClass.canonicalName ->{
                binding.isvisible = false

                binding.title =getString(R.string.reimbursement)

            }
            ReimbursementFragment().javaClass.canonicalName ->{
                binding.isvisible = false

                binding.title =getString(R.string.reimbursement)

            }
            ReimbursementListFragment().javaClass.canonicalName ->{
                binding.isvisible = true
                binding.iconstate = true
                binding.title =getString(R.string.reimbursement)

            }
            MedicalProviderFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.medical_provider)
                binding.isvisible = false


            }
            MedicalProviderListFragment(null).javaClass.canonicalName ->{
                binding.title =getString(R.string.medical_provider)
                binding.isvisible = true
                binding.iconstate = false

            }
            HealthTipsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.health_tips)
                binding.isvisible = false


            }
            MedicalProviderSearchFragment(null).javaClass.canonicalName ->{
                binding.title =getString(R.string.medical_provider)
                binding.isvisible = false


            }
            FAQFragment().javaClass.canonicalName ->{
                binding.isvisible = false

                binding.title =getString(R.string.faq)

            }
            ContactUsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.contact_us)
                binding.isvisible = false


            }
            ComplaintListFragment().javaClass.canonicalName ->{
                binding.isvisible = true
                binding.iconstate = true
                binding.title =getString(R.string.compliant)

            }
            ComplaintsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.compliant)
                binding.isvisible = false


            }
            AboutUsFragment().javaClass.canonicalName ->{
                binding.title =getString(R.string.about_us)

                binding.isvisible = false

            }else ->{
            binding.title =getString(R.string.home)
            binding.isvisible = false


        }
        }
        binding.executePendingBindings()

    }
    private fun isCheckLogout(){
        disposable.add(
            Observable.timer(15, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
                .repeat()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {  t->
                run {
                    Timber.d(t)
                }
            }
            .subscribe { aLong ->
                val previous : Long = sharedHelper.getFromUser("milsec").toLong()
                val amountTime: Long = System.currentTimeMillis() - previous
                val min = ((amountTime/1000) / 60) % 60
                println("enter the current time"+min)
                if(min>2){
                    loginViewModel.logoutApiCall()
                }

            })

    }
}