package com.kotlintest.app.view.bottomsheetFragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.app.washeruser.repository.Status
import com.astrology.app.baseClass.BaseBottomSheetFragment
import com.astrology.app.utility.imagePicker.Files.FilePickerConst
import com.kotlintest.app.R
import com.kotlintest.app.databinding.FragmentFileSelectBottomSheetBinding
import com.kotlintest.app.model.eventBus.ImagePathEvent
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.ImagePickerAdapter
import com.kotlintest.app.viewModel.FilePickerViewModel
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException

class FileSelectBottomSheet(var isimageType: String = "") :
    BaseBottomSheetFragment<FragmentFileSelectBottomSheetBinding>() {


    lateinit var adapter: ImagePickerAdapter

    private val filePickerViewModel by viewModel<FilePickerViewModel>()


    var listImage = ArrayList<String>()


    fun RxPermission(permission: Array<String>) {
        disposable.add(
            rxPermission
                .request(*permission)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ granted ->
                    println("enter the rx permission" + granted)
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        if (isimageType.equals("image")) {
                            filePickerViewModel.getDataFromMedia(FilePickerConst.MEDIA_TYPE_IMAGE)
                        } else {
                            filePickerViewModel.getDataFromMedia(FilePickerConst.FILE_TYPE_DOCUMENT)
                        }
                    } else {
                        // Oups permission denied
                        Timber.d("Permission error not support")
                    }
                }, { e -> Timber.d("Permission error%s", e.message) })!!
        )

    }

    private fun processResponse(it: Response) {
        if (it.status.equals(Status.SUCCESS)) {
            adapter.notifyDataSetChanged()
        }
    }


    private fun isFileCompression(Uri: File) {
        //

        disposable.add(
            Compressor(activity)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(100)
                .compressToFileAsFlowable(File(Uri.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { file: File? ->
                        EventBus.getDefault().postSticky(ImagePathEvent(file.toString()))
                        dismiss()

                    }
                ) { throwable: Throwable ->
                    throwable.printStackTrace()
                    commonFunction.run { commonToast(getString(R.string.invalid_image_format)) }

                }
        )
    }

    override fun layoutId(): Int = R.layout.fragment_file_select_bottom_sheet

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        adapter = ImagePickerAdapter(filePickerViewModel.medias.value!!, object : Commoninterface {

            override fun onCallback(value: Any) {
                if (value is Int) {

                    /*if(!isSelection)*/
                    if (isimageType.equals("image")) {
                        isFileCompression(File(filePickerViewModel.medias.value!!.get(value).path))
                    }else {
                        EventBus.getDefault()
                            .postSticky(ImagePathEvent(filePickerViewModel.medias.value!!.get(value).path))
                        dismiss()
                    }
                    /* else{
                         if(filePickerViewModel.medias.value!!.get(value - 1).isSelection){
                             listImage.add(filePickerViewModel.medias.value!!.get(value - 1).path)
                         }else{
                             listImage.remove(filePickerViewModel.medias.value!!.get(value - 1).path)
                         }
                         if(listImage.size==0){
                             binding.selectTxt.visibility = View.GONE
                             return
                         }
                         binding.selectTxt.visibility = View.VISIBLE
                         binding.selectTxt.setText("Selected Image "+listImage.size)

                     }*/


                }
            }

        }, isimageType)


        binding.adpater = adapter
        filePickerViewModel.response().observe(viewLifecycleOwner, Observer { processResponse(it) })
        RxPermission(commonFunction.STORAGEPermission)
        binding.selectTxt.setOnClickListener {
            EventBus.getDefault().postSticky(ImagePathEvent(listImage))
            dismiss()

        }

    }


}