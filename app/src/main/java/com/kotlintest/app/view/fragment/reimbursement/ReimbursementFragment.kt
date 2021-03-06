package com.kotlintest.app.view.fragment.reimbursement

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseFragment
import com.kotlintest.app.databinding.FragmentReimbursementBinding
import com.kotlintest.app.model.eventBus.ImagePathEvent
import com.kotlintest.app.model.eventBus.NavigateEvent
import com.kotlintest.app.model.localModel.ReimbursementformModel
import com.kotlintest.app.model.responseModel.*
import com.kotlintest.app.network.Response
import com.kotlintest.app.utility.`interface`.Commoninterface
import com.kotlintest.app.view.adapter.SelectionDocumentListAdapter
import com.kotlintest.app.view.bottomsheetFragment.BrowseTypeFragment
import com.kotlintest.app.view.bottomsheetFragment.FileSelectBottomSheet
import com.kotlintest.app.view.bottomsheetFragment.SelectBottomSheetFragment
import com.kotlintest.app.viewModel.ReimbursementViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class ReimbursementFragment : BaseFragment<FragmentReimbursementBinding>(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_FILE_PDF = 2
    var type :String ="img"

    private var listCountry : ArrayList<CountryListModel.CountryResponse> = ArrayList()
    private var listcategory : ArrayList<TreatCategoryListModel.CategoryResponse> = ArrayList()
    private var listcurrency : ArrayList<CurrencyModel.CountryResponse> = ArrayList()
    private var listReimbursementtype : ArrayList<ReimbursementTypeListModel.reimbursementFileType> = ArrayList()

    private lateinit var dataType : ReimbursementTypeListModel.reimbursementFileType

    private var selectionDocumentListAdapter: SelectionDocumentListAdapter? =null

    private val reimbursementViewModel by viewModel<ReimbursementViewModel>()

    var dpd: DatePickerDialog? =null

    var cameraornot =false


    override fun layoutId(): Int = R.layout.fragment_reimbursement

    override fun initView(mViewDataBinding: ViewDataBinding?) {

        binding.click = this
        binding.viewModel =reimbursementViewModel

        selectionDocumentListAdapter = SelectionDocumentListAdapter(reimbursementViewModel.reimbursementformModel.listImage,object :Commoninterface{
            override fun onCallback(value: Any) {
                reimbursementViewModel.reimbursementformModel.listImage.removeAt(value as Int)
                selectionDocumentListAdapter?.notifyDataSetChanged()
                reimbursementViewModel.onUsernameTextChanged(reimbursementViewModel.reimbursementformModel)
            }

        })

        binding.adapter= selectionDocumentListAdapter


        reimbursementViewModel.response().observe(this,{
            processResponse(it)
        })
        reimbursementViewModel.getTreatCategoryListApi()
        reimbursementViewModel.getReimburseMentDocumentFileTypeListApi()
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.category_edt->{
                showSelectionSheet(
                    listcategory as ArrayList<Any>,
                    getString(R.string.select_category)
                )
            }
            R.id.country_edt->{
                if(reimbursementViewModel.reimbursementformModel.category.isEmpty()){
                    commonFunction.commonToast(getString(R.string.select_your_category))
                    return
                }
                showSelectionSheet(
                    listCountry as ArrayList<Any>,
                    getString(R.string.select_country)
                )


            }
            R.id.currency_edt->{

                if(reimbursementViewModel.reimbursementformModel.countryid.isEmpty()){
                    commonFunction.commonToast(getString(R.string.select_your_category))
                    return
                }
                showSelectionSheet(listcurrency as ArrayList<Any>,getString(R.string.select_currency))

            }
            R.id.account_type_edt->{
               var arrayLis= ArrayList<String>()
                arrayLis.add(getString(R.string.savings))
                arrayLis.add(getString(R.string.current))
                showSelectionSheet(arrayLis as ArrayList<Any>,getString(R.string.select_account_type))
            }
            R.id.date_edit->{
                datePicker()
            }
            R.id.camera_btn->{
                cameraornot = true
                showSelectionSheet(listReimbursementtype as ArrayList<Any>,getString(R.string.select_reimbursement_type))
            }
            R.id.browse_btn->{
                cameraornot = false
                showSelectionSheet(listReimbursementtype as ArrayList<Any>,getString(R.string.select_reimbursement_type))
            }
        }
    }
    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is CountryListModel->{
                        listCountry.clear()
                        listCountry.addAll(response.data.CountryListResponse)
                    }
                    is TreatCategoryListModel ->{
                        listcategory.clear()
                        listcategory.addAll(response.data.CategoryListResponse)
                    }
                    is CurrencyModel ->{
                        listcurrency.clear()
                        listcurrency.addAll(response.data.CountryListResponse)
                    }

                    is ReimbursementTypeListModel->{
                        listReimbursementtype.addAll(response.data.ReimbursementFileType)
                    }
                    is ReimbursementsModel ->{
                        commonFunction.commonToast(response.data.ApiResponse.Details)
                        if(response.data.ApiResponse.StatusCode.equals("1")){
                            event.post(NavigateEvent("reimbuer_update"))
                            fragmentManagers?.popBackStackImmediate()
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
    private fun showSelectionSheet(list:ArrayList<Any> , title :String){

        if(bottomSheet!=null && bottomSheet!!.isAdded){
            bottomSheet?.dismiss()
        }


        bottomSheet = SelectBottomSheetFragment(object : Commoninterface {
            override fun onCallback(value: Any) {

                when(value){
                    is CountryListModel.CountryResponse->{
                        reimbursementViewModel.getCurrencyListApi()
                        reimbursementViewModel.reimbursementformModel.country = value.CountryName
                        reimbursementViewModel.reimbursementformModel.countryid = value.CountryId
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is TreatCategoryListModel.CategoryResponse->{
                        reimbursementViewModel.getCountryListApi()
                        reimbursementViewModel.reimbursementformModel.category = value.CategoryName
                        reimbursementViewModel.reimbursementformModel.categoryid = value.CategoryID
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is CurrencyModel.CountryResponse->{
                        reimbursementViewModel.reimbursementformModel.currency = value.CurrencyName
                        reimbursementViewModel.reimbursementformModel.currencyid = value.CurrencyID
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is String->{
                        reimbursementViewModel.reimbursementformModel.accounttype = value
                        if(value.equals(activity.resources.getString(R.string.savings))){
                            commonFunction.commonToast(getString(R.string.salary_pre_pay_a_c))
                            reimbursementViewModel.reimbursementformModel.accounttypeid = "1"
                        }else{
                            reimbursementViewModel.reimbursementformModel.accounttypeid = "2"
                        }
                        binding.viewModel = reimbursementViewModel
                        bottomSheet?.dismiss()

                    }
                    is ReimbursementTypeListModel.reimbursementFileType->{
                        dataType = value
                        bottomSheet?.dismiss()
                        if(cameraornot){
                            RxPermission(commonFunction.STORAGEPermission)
                        }else{
                            if(bottomSheet!=null && bottomSheet!!.isAdded){
                                bottomSheet?.dismiss()
                            }
                            bottomSheet = BrowseTypeFragment(object : Commoninterface{
                                override fun onCallback(value: Any) {
                                    when(value as String){
                                        "image"->{
                                            type ="img"
                                            bottomSheet = FileSelectBottomSheet("image")
                                            bottomSheet?.show(fragmentManagers!!,"enter image")
                                        }
                                        "pdf"->{
                                            type="pdf"
                                            bottomSheet = FileSelectBottomSheet("pdf")
                                            bottomSheet?.show(fragmentManagers!!,"enter image")
                                        }
                                    }
                                }

                            })
                            bottomSheet?.show(fragmentManagers!!,"enter type")
                        }


                    }

                }

            }

        },title,list)

        bottomSheet?.show(fragmentManagers!!,"show_select")
    }

    private fun datePicker(){
        val now: Calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR),  // Initial year selection
            now.get(Calendar.MONTH),  // Initial month selection
            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        )
        if(dpd!=null && dpd!!.isAdded){
            dpd!!.dismiss()
        }
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR]
        cal[Calendar.MONTH]
        cal[Calendar.DAY_OF_MONTH]

        dpd?.maxDate = cal
        dpd?.setOkText(getString(R.string.ok))
        dpd?.setCancelText(getString(R.string.cancel))
        dpd!!.show(fragmentManagers!!, "Datepickerdialog")

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val datemonth= 1+monthOfYear
        val date = "$datemonth/$year"
        reimbursementViewModel.reimbursementformModel.serviceperiod = date
        binding.viewModel =reimbursementViewModel

    }

    private fun CallCamera() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(activity.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = reimbursementViewModel.createImageFile()
            } catch (ex: Exception) {
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
                    isFileCompression(File(reimbursementViewModel.mCurrentPhotoPath.value!!))
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
                        if(reimbursementViewModel.reimbursementformModel.listImage.size==9){
                            commonFunction.commonToast(getString(R.string.morethan))
                            return@subscribe
                        }
                        val file_size = file!!.length() / 1024
                        val fileSizeInMB: Long = file_size / 1024
                        if(fileSizeInMB>5){
                            commonFunction.commonToast(getString(R.string.documentsize))
                            return@subscribe
                        }
                        if(reimbursementViewModel.reimbursementformModel.listImage.isEmpty()){
                            reimbursementViewModel.reimbursementformModel.listImage.add(ReimbursementformModel.ListImage(dataType.FileTypeShortName,dataType.FileTypeID ,commonFunction.base64Convert(file.toString()),file!!.name,commonFunction.getMimeType(file.toString())))
                        reimbursementViewModel.onUsernameTextChanged(reimbursementViewModel.reimbursementformModel)
                            selectionDocumentListAdapter?.notifyDataSetChanged()
                        }
                        else{
                            checkList(ReimbursementformModel.ListImage(dataType.FileTypeShortName,dataType.FileTypeID ,commonFunction.base64Convert(file.toString()),file!!.name,commonFunction.getMimeType(file.toString())))
                        }

                    }
                ) { throwable: Throwable ->
                    throwable.printStackTrace()
                    commonFunction.run { commonToast(getString(R.string.invalid_image_format)) }

                }
        )
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
    fun onMessage(event: ImagePathEvent) {
        if(reimbursementViewModel.reimbursementformModel.listImage.size==9){
            commonFunction.commonToast(getString(R.string.morethan))
            return
        }
        val file = File(event.imagePath as String)
        val file_size = file.length() / 1024
        val fileSizeInMB: Long = file_size / 1024
        if(fileSizeInMB>5){
            commonFunction.commonToast(getString(R.string.documentsize))
            return
        }
        if(type.equals("img")){
            if(reimbursementViewModel.reimbursementformModel.listImage.isEmpty()) {
                reimbursementViewModel.reimbursementformModel.listImage.add(
                    ReimbursementformModel.ListImage(
                        dataType.FileTypeShortName,
                        dataType.FileTypeID,
                        commonFunction.base64Convert(event.imagePath as String),
                        File(event.imagePath as String).name,
                        commonFunction.getMimeType(event.imagePath as String)
                    )
                )
                reimbursementViewModel.onUsernameTextChanged(reimbursementViewModel.reimbursementformModel)
                selectionDocumentListAdapter?.notifyDataSetChanged()

            }  else{
                checkList(ReimbursementformModel.ListImage(dataType.FileTypeShortName,dataType.FileTypeID ,commonFunction.base64Convert(event.imagePath as String),File(event.imagePath as String).name,commonFunction.getMimeType(event.imagePath as String)))
            }

        }else{

            if(reimbursementViewModel.reimbursementformModel.listImage.isEmpty()){
                reimbursementViewModel.reimbursementformModel.listImage.add(ReimbursementformModel.ListImage(dataType.FileTypeShortName,dataType.FileTypeID ,commonFunction.encodeFileToBase64Binary(file,activity),file!!.name,commonFunction.getMimeType(file.toString())))
                reimbursementViewModel.onUsernameTextChanged(reimbursementViewModel.reimbursementformModel)
                selectionDocumentListAdapter?.notifyDataSetChanged()
            }
            else{
                checkList(ReimbursementformModel.ListImage(dataType.FileTypeShortName,dataType.FileTypeID ,commonFunction.encodeFileToBase64Binary(file,activity),file!!.name,commonFunction.getMimeType(file.toString())))
            }

        }
        EventBus.getDefault().removeStickyEvent(event)

    }
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
                        CallCamera()
                    } else {
                        // Oups permission denied
                        Timber.d("Permission error not support")
                    }
                }, { e -> Timber.d("Permission error%s", e.message) })!!
        )

    }
    private fun checkList(data: ReimbursementformModel.ListImage){
        var post=-1;
        for (item in reimbursementViewModel.reimbursementformModel.listImage.indices) {
            if(data.FileTypeID==reimbursementViewModel.reimbursementformModel.listImage[item].FileTypeID){
                post=item
            }
        }
        if(post==-1){
            reimbursementViewModel.reimbursementformModel.listImage.add(data)
        }else{
            reimbursementViewModel.reimbursementformModel.listImage.set(post,data)
        }
        reimbursementViewModel.onUsernameTextChanged(reimbursementViewModel.reimbursementformModel)
        selectionDocumentListAdapter?.notifyDataSetChanged()

    }



}