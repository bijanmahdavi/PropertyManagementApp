package com.example.propertymanagementapp.ui.home

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.repositories.UserRepository
import com.example.propertymanagementapp.helpers.TokenManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_home.*

var CAMERA_REQUEST_CODE = 100

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        button_home_logout.setOnClickListener {
            UserRepository().getProperties()
            //dialogLogout()
        }

        button_to_add_property.setOnClickListener {
            requestSinglePermission()
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

    private fun requestMultiplePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
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

    // open setting activity
    private fun openAppSettings(){
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri = Uri.fromParts("package", packageName, null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }

    // show dialogue
    private fun showDialogue(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("App needs permissions")
        builder.setMessage("You have turned off permissions permanently. These can be enabled in the settings.")
        builder.setPositiveButton("Go To Setting", object: DialogInterface.OnClickListener{
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
    private fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun dialogLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                TokenManager().logout()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        })
        var myAlertDialog = builder.create()
        myAlertDialog.show()
    }

}