package com.shivam.imageupload

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.google.android.material.button.MaterialButton
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File

class MainActivity : AppCompatActivity() {
    var previewImage: ImageView? = null
    var progressBar: ProgressBar? = null
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previewImage = findViewById(R.id.previewImage)
        progressBar = findViewById(R.id.progressBar)

        val pickImageButton: TextView = findViewById(R.id.pickImageButton)

        val uploadButton: Button = findViewById(R.id.uploadImgBtn)
        previewImage!!.setImageResource(R.drawable.thumb)



        pickImageButton.setOnClickListener {


            Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {


                        ImagePicker.with(this@MainActivity)
                            .galleryOnly().compress(1024)
                            .start()


                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    }

                }).check()


        }




        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.mainEvent.observe(this@MainActivity, Observer {


                when (it) {

                    is MainActivityViewModel.MainEvent.Success -> {
                        progressBar!!.visibility = View.GONE

                       Toast.makeText(applicationContext, "Image uploaded successfully",Toast.LENGTH_LONG).show()


                    }

                    is MainActivityViewModel.MainEvent.Failure -> {

                        progressBar!!.visibility = View.GONE

                        Log.d("XXXXXXXX", "" + it.m!!)
                    }

                    is MainActivityViewModel.MainEvent.Loading -> {

                        progressBar!!.visibility = View.VISIBLE
                    }

                }

            })

            mainActivityViewModel.imageFile.observe(this@MainActivity, Observer {


                uploadButton.setOnClickListener { v ->


                    mainActivityViewModel.uploadImage(it)

                }


            })

        }





    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            previewImage!!.setImageURI(fileUri)
            //You can get File object from intent


            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            mainActivityViewModel.sendImageFile(file)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(applicationContext, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }


}