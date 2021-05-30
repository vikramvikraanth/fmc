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

class FileSelectBottomSheet(var isSelection : Boolean = false) : BaseBottomSheetFragment<FragmentFileSelectBottomSheetBinding>() {


    lateinit var adapter: ImagePickerAdapter

    private val filePickerViewModel by viewModel<FilePickerViewModel>()


    val REQUEST_IMAGE_CAPTURE = 1

    var listImage = ArrayList<String>()



    fun RxPermission(permission: Array<String>) {
        disposable.add(
            rxPermission
                .request(*permission)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ granted ->
                    println("enter the rx permission"+granted)
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        filePickerViewModel.getDataFromMedia()
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

    private fun CallCamera() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(activity.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = filePickerViewModel.createImageFile()
            } catch (ex: IOException) {
                println("enter the expection"+ex.message)
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    isFileCompression(File(filePickerViewModel.mCurrentPhotoPath.value!!))
                }

            }


        }


    }


    private fun isFileCompression(Uri : File){
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
                    commonFunction.commonToast("Invalid image format")

                }
        )
    }

    override fun layoutId(): Int = R.layout.fragment_file_select_bottom_sheet

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        adapter = ImagePickerAdapter(filePickerViewModel.medias.value!!, object : Commoninterface {

            override fun onCallback(value: Any) {
                if (value is Int) {
                    if (value == 0) {
                        CallCamera()
                    } else {
                        if(!isSelection)
                            isFileCompression(File(filePickerViewModel.medias.value!!.get(value - 1).path))
                        else{
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

                        }

                    }
                }
            }

        })


        binding.adpater = adapter
        filePickerViewModel.response().observe(viewLifecycleOwner, Observer { processResponse(it) })
        RxPermission(commonFunction.STORAGEPermission)
        binding.selectTxt.setOnClickListener {
            EventBus.getDefault().postSticky(ImagePathEvent(listImage))
            dismiss()

        }

    }


}