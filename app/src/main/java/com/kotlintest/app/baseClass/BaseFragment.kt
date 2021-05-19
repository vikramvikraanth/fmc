package com.kotlintest.app.baseClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlintest.app.utility.GlideApp
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject


abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var disposable: CompositeDisposable
    lateinit var activity: Activity
    var fragmentManagers: FragmentManager? = null
    lateinit var views:View
    protected lateinit var binding: B

    val commonFunction : CommonFunction by inject()
    val sharedHelper : SharedHelper by inject()

    lateinit var event : EventBus

    var bottomSheet : BottomSheetDialogFragment? =null
    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initView(mViewDataBinding: ViewDataBinding?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        activity = getActivity()!!
        event = EventBus.getDefault()
        fragmentManagers = fragmentManager!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,layoutId(), container, false)
        binding.lifecycleOwner=this
        initView(binding)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        if (disposable.isDisposed) {
            disposable.clear()
        }
        if(bottomSheet!=null && bottomSheet!!.isAdded){
            bottomSheet!!.dismiss()
        }

        commonFunction.dismissLoader()
    }

    fun glideApp(imagePath: String, Images: ImageView) {
        GlideApp.with(activity)
            .load(imagePath)
            .into(Images)
    }

    fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .show()
    }

    private fun showNetworkSnackBar(message: String, view: View?) {
        val snackBar: Snackbar?
        snackBar = Snackbar.make(view!!, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackBar.setAction("Dismiss", View.OnClickListener {
            snackBar.dismiss()
        })
        snackBar.show()
    }

    protected fun moveTOFragment(fragment: Fragment,ids : Int){
        val fragmentTransaction = fragmentManagers?.beginTransaction()
        fragmentTransaction?.replace(ids, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commitAllowingStateLoss()

    }
    fun setIntent(cObjection: Class<*>, isFrom: Int) {
        startActivity(Intent(activity, cObjection))
        when (isFrom) {
            1 -> {
                //just no need to finish
            }
            2 ->
                //just finishing the single activity
                activity.finish()
            3 ->
                //finishing all previous activity
                activity.finishAffinity()
        }
    }



}