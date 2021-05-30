package com.kotlintest.app.utility.imagePicker

import android.content.ContentResolver
import android.os.Bundle
import com.kotlintest.app.utility.imagePicker.Files.PhotoDirectory
import com.kotlintest.app.utility.imagePicker.Interface.FileResultCallback

object MediaStoreHelper {
	
	fun getDirs(contentResolver: ContentResolver, args: Bundle, resultCallback: FileResultCallback<PhotoDirectory>) {
		PhotoScannerTask(contentResolver,args,resultCallback).execute()
	}
	
}