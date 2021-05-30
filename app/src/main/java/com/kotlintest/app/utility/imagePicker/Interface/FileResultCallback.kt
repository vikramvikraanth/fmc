package com.kotlintest.app.utility.imagePicker.Interface

interface FileResultCallback<T> {
	fun onResultCallback(files: List<T>)
}