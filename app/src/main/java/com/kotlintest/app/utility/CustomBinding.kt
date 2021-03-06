package com.kotlintest.app.utility

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.kotlintest.app.R
import com.kotlintest.app.utility.RecycleView.GridItemSpacingDecoration
import com.kotlintest.app.view.adapter.*
import com.nekoloop.base64image.Base64Image
import com.nekoloop.base64image.RequestDecode.Decode
import java.io.File
import java.util.*


object CustomBinding {
    private val gridSpacing = dp2px(2)
    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    @BindingAdapter("ImageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).apply(RequestOptions().circleCrop()).into(imageView)
    }

    @BindingAdapter("load_allLead")
    @JvmStatic
    fun loadUsers(recyclerView: RecyclerView, adapter: Any?) {
        when (adapter) {

            is BenifitAdapter, is BenifitstepAdapter, is PreAppovalsAdapter,is LocationListAdapter,is SelectionListAdapter,
            is ReimbursementAdapter , is ComplaintsListAdapter, is FaqListAdapter, is EcardAdapter, is ContactUsAdapter, is SelectionDocumentListAdapter-> {
                recyclerView.adapter = adapter as RecyclerView.Adapter<*>?

            }
            is MainMenuAdapter , is FamilyAdapter ,is LanguageAdapter -> {
                (Objects.requireNonNull<RecyclerView.ItemAnimator>(recyclerView.getItemAnimator()) as SimpleItemAnimator).supportsChangeAnimations =
                    false
                recyclerView.addItemDecoration(GridItemSpacingDecoration(2, gridSpacing, false))
                recyclerView.adapter = adapter as RecyclerView.Adapter<*>?
            }

         is ImagePickerAdapter -> {
                (Objects.requireNonNull<RecyclerView.ItemAnimator>(recyclerView.getItemAnimator()) as SimpleItemAnimator).supportsChangeAnimations =
                    false
                recyclerView.addItemDecoration(GridItemSpacingDecoration(3, gridSpacing, false))
                recyclerView.adapter = adapter as RecyclerView.Adapter<*>?
            }


        }
    }

    @BindingAdapter("enable_button")
    @JvmStatic
    fun setButtonState(view: MaterialButton, ischeck: Boolean) {
        view.isEnabled = ischeck

        if(ischeck){
            view.alpha = 1f
        }else{
            view.alpha = 0.5f
        }

    }
    @BindingAdapter("process_type")
    @JvmStatic
    fun setType(view: TextView, title: String?) {
        if(title==null){
            return
        }
        view.setText(title)

        when(title){
            "Pending"->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setTextColor(view.context.resources.getColor(R.color.red,null))
                }
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_pending, 0);

            }
            "Processed" ->
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setTextColor(view.context.resources.getColor(R.color.lightyellow,null))
                }
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_processing, 0);

            }
            else->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setTextColor(view.context.resources.getColor(R.color.greencolor,null))
                }
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_approvals, 0);

            }
        }

    }

    @BindingAdapter("url")
    @JvmStatic
    fun GlideApp(Images: ImageView, imagePath: Any?) {
        try {
            if (imagePath != null) {
                if(imagePath is Drawable){
                    Glide.with(Images.context).load(imagePath)
                        .apply(
                            RequestOptions()
                                .centerCrop()
                        )
                        .into(Images)
                    return
                }
               if(imagePath is String){

                   val file = File(imagePath)
                   if(file.exists()){
                       GlideApp.with(Images.context)
                           .load(imagePath)
                           .diskCacheStrategy(DiskCacheStrategy.NONE)
                           .skipMemoryCache(true)
                           .into(Images)
                   }else{
                       Base64Image.with(Images.context)
                           .decode(imagePath)
                           .into(object : Decode {
                               override fun onSuccess(bitmap: Bitmap) {
                                   GlideApp.with(Images.context)
                                       .load(bitmap)
                                       .into(Images)
                                   println("enter the image"+imagePath)

                               }
                               override fun onFailure() {
                                   println("enter the error image")

                               }
                           })

                   }

               }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @BindingAdapter("setWebViewClient")
    @JvmStatic
    fun setWebViewClient(view: WebView, client: WebViewClient?) {
        view.webViewClient = client!!
    }

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun loadUrl(view: WebView, url: String?) {
        view.loadUrl(url!!)
    }

}