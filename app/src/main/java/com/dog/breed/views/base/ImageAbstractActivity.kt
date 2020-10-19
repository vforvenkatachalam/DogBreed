package com.dog.breed.views.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dog.breed.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class ImageAbstractActivity : HeaderActivity(), HeaderActivity.OnHeaderClickListener {

    protected val REQUEST_CODE_GALLERY = 2004
    protected val REQUEST_CODE_TAKE_PICTURE = 2005
    private val STORAGE_CAMERA_PERMISSION_CODE = 34
    private var initialPath: String? = null
    protected var mImageCaptureUri: Uri? = null
    protected var cropImageUri: Uri? = null
    protected var imageUri: String? = null
    protected var imagePath: String? = null


    /*private fun getOutputMediaFile(): File {

        // External sdcard location
        val mediaStorageDir = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            DogBreedConstants.IMAGE_DIRECTORY_NAME
        )

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            val isCreated = mediaStorageDir.mkdirs()
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val mediaFile: File

        initialPath = mediaStorageDir.path

        mediaFile = File(
            initialPath + File.separator
                    + "IMG_" + timeStamp + ".jpg"
        )


        return mediaFile
    }

    protected fun selectImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showSelector()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                STORAGE_CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun showSelector() {
        val options = arrayOf<CharSequence>("Camera", "Photo Library", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Document Image Source: ")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Camera") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val timeStamp = SimpleDateFormat(
                    "yyyyMMdd_HHmmss",
                    Locale.getDefault()
                ).format(Date())
                //  String imageLocation = "IMG_" + timeStamp + ".jpg";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mImageCaptureUri = FileProvider.getUriForFile(
                        applicationContext,
                        BuildConfig.APPLICATION_ID + ".provider",
                        getOutputMediaFile()
                    )
                } else {
                    mImageCaptureUri = Uri.fromFile(getOutputMediaFile())
                }
                DogBreedLog.d("captureImage", mImageCaptureUri.toString())
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
                intent.putExtra("return-data", true)
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE)

            } else if (options[item] == "Photo Library") {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_GALLERY)

            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    fun getPath(uri: Uri): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    private fun beginCrop(source: Uri?) {
        cropImageUri = FileProvider.getUriForFile(
            this@ImageAbstractActivity, BuildConfig.APPLICATION_ID + ".provider", getOutputMediaFile())
        //        if (this instanceof CreateContentActivity)
        //            Crop.of(source, cropImageUri).start(this);
        //        else
        Crop.of(source, cropImageUri).withAspect(100,150).start(this)

    }

    private fun handleCrop(resultCode: Int, result: Intent) {
        if (resultCode == RESULT_OK) {
            imageUri = cropImageUri.toString()
            imagePath = initialPath + "/" + cropImageUri!!.getLastPathSegment()
            previewCapturedImage()
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(
                this, Crop.getError(result).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    protected abstract fun previewCapturedImage()

    protected abstract fun showImageError(error: String)


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            STORAGE_CAMERA_PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0) {

                    var granted = true

                    for (result in grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED)
                            granted = false
                    }

                    if (granted) {
                        showSelector()
                    } else {
                        showImageError("External storage permission not granted!")
                    }

                } else {
                    showImageError("External storage permission not granted!")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PICTURE) {

            ImageUtils.normalizeImageForUri(this, mImageCaptureUri!!, initialPath!!)

            beginCrop(mImageCaptureUri)
        } else if (requestCode == REQUEST_CODE_GALLERY && data != null) {
            beginCrop(data.data)

        } else if (requestCode == Crop.REQUEST_CROP && data != null) {
            handleCrop(resultCode, data)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("cameraImageUri"))
                mImageCaptureUri = Uri.parse(savedInstanceState.getString("cameraImageUri"))
            if (savedInstanceState.containsKey("cropImageUri"))
                cropImageUri = Uri.parse(savedInstanceState.getString("cropImageUri"))
            if (savedInstanceState.containsKey("initialPath"))
                initialPath = savedInstanceState.getString("initialPath")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mImageCaptureUri != null)
            outState.putString("cameraImageUri", mImageCaptureUri.toString())
        if (cropImageUri != null)
            outState.putString("cropImageUri", cropImageUri.toString())
        if (initialPath != null)
            outState.putString("initialPath", initialPath.toString())
    }*/

//     override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        if (savedInstanceState.containsKey("cameraImageUri"))
//            mImageCaptureUri = Uri.parse(savedInstanceState.getString("cameraImageUri"))
//        if (savedInstanceState.containsKey("cropImageUri"))
//            cropImageUri = Uri.parse(savedInstanceState.getString("cropImageUri"))
//         if (savedInstanceState.containsKey("initialPath"))
//             initialPath = savedInstanceState.getString("initialPath")
//    }
}