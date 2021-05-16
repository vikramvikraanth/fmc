package com.astrology.app.baseClass

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlintest.app.R
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseBottomSheetFragment<B : ViewDataBinding> : BottomSheetDialogFragment() {

    lateinit var disposable: CompositeDisposable
    lateinit var activity: Activity
    var fragmentManagers: FragmentManager? = null
    lateinit var views: View

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    protected lateinit var binding: B

    val commonFunction : CommonFunction by inject()
    val sharedHelper : SharedHelper by inject()
    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initView(mViewDataBinding: ViewDataBinding?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        disposable = CompositeDisposable()
        activity = getActivity()!!
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
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        if (disposable.isDisposed) {
            disposable.clear()
        }
        //commonFunction.dismissLoader()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun intent(intent: Intent, ins:Int){
        startActivity(intent)
        when(ins){
            1->{
                activity.finish()
            }
            2->{
                activity.finishAffinity()
            }
        }

    }
}
