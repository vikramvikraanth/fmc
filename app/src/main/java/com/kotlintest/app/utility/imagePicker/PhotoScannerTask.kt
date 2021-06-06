package com.kotlintest.app.utility.imagePicker

import android.content.ContentResolver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.astrology.app.utility.imagePicker.Files.FilePickerConst
import com.astrology.app.utility.imagePicker.Files.PickerManager
import com.kotlintest.app.utility.imagePicker.Files.PhotoDirectory
import com.kotlintest.app.utility.imagePicker.Interface.FileResultCallback
import java.util.*


class PhotoScannerTask(val contentResolver: ContentResolver, private val args: Bundle,
					   private val resultCallback: FileResultCallback<PhotoDirectory>?) : AsyncTask<Void, Void, MutableList<PhotoDirectory>>() {

	var type =""
	override fun doInBackground(vararg voids: Void): MutableList<PhotoDirectory> {
		val bucketId = args.getString(FilePickerConst.EXTRA_BUCKET_ID, null)
		val mediaType = args.getInt(FilePickerConst.EXTRA_FILE_TYPE, FilePickerConst.MEDIA_TYPE_IMAGE)
		
		val projection = null
		val uri = MediaStore.Files.getContentUri("external")
		val sortOrder = MediaStore.Images.Media._ID + " DESC"
		
		var selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
				+ MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
		
		if (mediaType == FilePickerConst.FILE_TYPE_DOCUMENT) {
			type="pdf"
			val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")

			selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
					+ MediaStore.Files.FileColumns.MEDIA_TYPE_NONE)
		}
		
		if (bucketId != null)
			selection += " AND " + MediaStore.Images.Media.BUCKET_ID + "='" + bucketId + "'"
		
		val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)
		
		if (cursor != null) {
			val data = getPhotoDirectories(cursor)
			cursor.close()
			return data
		}
		
		return mutableListOf()
	}
	
	override fun onPostExecute(result: MutableList<PhotoDirectory>?) {
		super.onPostExecute(result)
		result?.let {
			resultCallback?.onResultCallback(it.toList())
		}
	}
	
	private fun getPhotoDirectories(data: Cursor): MutableList<PhotoDirectory> {
		val directories = ArrayList<PhotoDirectory>()
		
		while (data.moveToNext()) {
			
			val imageId = data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID))
			val bucketId = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID))
			val name = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
			val path = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
			val fileName = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE))
			val mediaType = data.getInt(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE))
			
			val photoDirectory = PhotoDirectory()
			photoDirectory.bucketId = bucketId
			photoDirectory.name = name
			if(type=="pdf"){
				if (path != null && path.toLowerCase().endsWith("pdf")){
					if (!directories.contains(photoDirectory)){
						photoDirectory.addPhoto(imageId, fileName, path, mediaType)

						photoDirectory.dateAdded = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED))
						directories.add(photoDirectory)
					}else{
						directories[directories.indexOf(photoDirectory)]
							.addPhoto(imageId, fileName, path, mediaType)
					}

				}
			}
			else{
				if (!directories.contains(photoDirectory)) {

					if (path != null && path.toLowerCase().endsWith("gif")) {
						if (PickerManager.isShowGif) {
							photoDirectory.addPhoto(imageId, fileName, path, mediaType)
						}
					} else {
						photoDirectory.addPhoto(imageId, fileName, path, mediaType)
					}
					photoDirectory.dateAdded = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED))
					directories.add(photoDirectory)
				} else {
					directories[directories.indexOf(photoDirectory)]
						.addPhoto(imageId, fileName, path, mediaType)
				}
			}


		}
		
		return directories
	}
}
