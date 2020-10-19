package com.example.propertymanagementapp.ui.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.repositories.PropertyRepository
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.ui.home.PropertyActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_property.*
import kotlinx.android.synthetic.main.add_photo_bottom_sheet.view.*
import java.io.ByteArrayOutputStream

class AddPropertyActivity : AppCompatActivity() {
    private var CAMERA_REQUEST_CODE = 100
    private var GALLERY_REQUEST_CODE = 200
    lateinit var img_bmp: Bitmap
    lateinit var img_path: String
    var hasImage = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)
        init()
    }


    private fun init() {
        button_add_new_property_save.setOnClickListener {
            if(hasImage) {
                img_path = getRealPathFromURI(getImageUri(this, img_bmp))!!
                PropertyRepository().uploadImage(img_path)
                var location = TokenManager().getImageLocation()
                PropertyRepository().newPropertyWithImage(
                    text_input_address.text.toString(),
                    text_input_city.text.toString(),
                    text_input_country.text.toString(),
                    text_input_purchase_price.text.toString(),
                    text_input_state.text.toString(),
                    location)
                this.d("add prop w/image", "success?")
            } else {
                PropertyRepository().newProperty(
                    text_input_address.text.toString(),
                    text_input_city.text.toString(),
                    text_input_country.text.toString(),
                    text_input_purchase_price.text.toString(),
                    text_input_state.text.toString()
                )
                this.d("add prop w/o image", "success?")
            }
            hasImage = false
            startActivity(Intent(this, PropertyActivity::class.java))
            finish()
        }

        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.add_photo_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        button_upload_image.setOnClickListener {
            bottomSheetDialog.show()
        }
        view.button_camera.setOnClickListener {
            requestCameraPermission()
        }

        view.button_gallery.setOnClickListener {
            requestGalleryPermission()
        }
    }


    // single permission
    // camera
    private fun requestSinglePermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    // permission is granted
                    Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                    openCamera()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    // permission denied
                    Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
                    if(p0!!.isPermanentlyDenied) {
                        showDialogue()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            }).check()
    }
    private fun requestCameraPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // check if all permission are granter
                    if(report!!.areAllPermissionsGranted()){
                        Toast.makeText(applicationContext, "All permission granted", Toast.LENGTH_SHORT).show()
                        openCamera()
                    }
                    // check for permanent denial of any permission
                    if(report!!.isAnyPermissionPermanentlyDenied){
                        //Toast.makeText(applicationContext, "permission denied permanently", Toast.LENGTH_SHORT).show()
                        showDialogue()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }
    private fun requestGalleryPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // check if all permission are granter
                    if(report!!.areAllPermissionsGranted()){
                        Toast.makeText(applicationContext, "All permission granted", Toast.LENGTH_SHORT).show()
                        openGallery()
                    }
                    // check for permanent denial of any permission
                    if(report!!.isAnyPermissionPermanentlyDenied){
                        //Toast.makeText(applicationContext, "permission denied permanently", Toast.LENGTH_SHORT).show()
                        showDialogue()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }

    // open setting activity
    private fun openAppSettings(){
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    // show dialogue
    private fun showDialogue(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("App needs permissions")
        builder.setMessage("You have turned off permissions permanently. These can be enabled in the settings.")
        builder.setPositiveButton("Go To Settings", object: DialogInterface.OnClickListener{
            override fun onClick(dialoge: DialogInterface?, p1: Int) {
                dialoge?.dismiss()
                openAppSettings()
            }
        })
        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener{
            override fun onClick(dialoge: DialogInterface?, p1: Int) {
                dialoge?.dismiss()
            }

        })
        builder.show()
    }

    private fun openGallery() {
        var intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //crashes when i try to use from gallery however.. only working from image directly from camera
        if(requestCode == 100)  {
            hasImage = true
            var bmp = data?.extras!!.get("data") as Bitmap
            img_bmp = bmp
            image_view.setImageBitmap(bmp)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    // get actual path
    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }
}