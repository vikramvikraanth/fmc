package com.kotlintest.app.baseClass.BaseActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlintest.app.utility.GlideApp
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import java.util.*

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var disposable: CompositeDisposable
    lateinit var activity: Activity
    lateinit var fragmentManager: FragmentManager
    protected lateinit var binding: B
    lateinit var event : EventBus
    val commonFunction : CommonFunction by inject()
    val sharedHelper : SharedHelper by inject()
    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initView(mViewDataBinding: ViewDataBinding?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        activity = this
        event = EventBus.getDefault()
        fragmentManager = supportFragmentManager
        bindContentView(layoutId())

    }
    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner=this
        initView(binding)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable.isDisposed) {
            disposable.clear()
        }
        commonFunction.dismissLoader()

    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String, view: View) {
        //or use (activity.window.decorView.rootView) to set a global view for snackBar
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

    fun glideApp(imagePath: String, Images: ImageView) {
        GlideApp.with(this)
            .load(imagePath)
            .into(Images)
    }

    fun setIntent(cObjection: Class<*>, isFrom: Int) {
        startActivity(Intent(this, cObjection))
        when (isFrom) {
            1 -> {
                //just no need to finish
            }
            2 ->
                //just finishing the single activity
                finish()
            3 ->
                //finishing all previous activity
                finishAffinity()
        }
    }
    protected fun moveTOFragment(fragment: Fragment, ids : Int){
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(ids, fragment)
        fragmentTransaction?.addToBackStack(fragment.javaClass.canonicalName)
        fragmentTransaction?.commitAllowingStateLoss()

    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }


}