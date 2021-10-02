package com.example.Settings

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.Company.CompanyViewModel
import com.example.Data.CompanyDatabase
import com.example.Data.PrefManage
import com.example.Pojo.Companies
import com.example.phons.R
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.graphics.drawable.Drawable

import android.graphics.Bitmap

import android.os.Environment

import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Settings : AppCompatActivity() {

    var bu_import_data:Button? = null
    var companydatabase:CompanyDatabase? = null
    var CompanyViewModel: CompanyViewModel? = null
    var fileUri: String? = null

    var CODEWRITE = 120
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        bu_import_data = findViewById(R.id.bu_import_data)



        bu_import_data!!.setOnClickListener {


            if (ContextCompat.checkSelfPermission(this ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this ,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ,CODEWRITE)
            }
            else
            {

                companydatabase = CompanyDatabase.getInstance(this)

                CompanyViewModel = CompanyViewModel(this)

                CompanyViewModel!!.getcompany(uiq)

                val array_id = arrayListOf<String>()

                CompanyViewModel!!.MutableLiveDataCompaby.observe(this,
                    object : Observer<ArrayList<Companies>> {

                        override fun onChanged(t: ArrayList<Companies>?) {
                            Thread(Runnable() {
                                Log.d("chang", t.toString())

                                for (i in t!!) {

                                    array_id.add(i.compid!!)
                                     if (i.image_link != "")
                                     {
                                        runOnUiThread {
                                            SaveImage("https://app.mytasks.click${i.image_link}" ,i.compid.toString())
                                        }
                                     }
                                    companydatabase?.CompanyDao()?.insertCompany(i)
                                }

                                for (i in array_id)
                                {
                                    Log.d("compid" ,i)
                                }
                            }).start()


                        }

                    })

            }

    }

  }

    fun SaveImage(url: String? ,company_id: String) {
        Picasso.with(applicationContext).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                try {
                    val mydir =
                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Samco")
                    if (!mydir.exists()) {
                        mydir.mkdirs()
                    }
                    fileUri = mydir.getAbsolutePath() + File.separator + company_id + ".jpg"
                    val outputStream = FileOutputStream(fileUri)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext, "Image Downloaded", Toast.LENGTH_LONG).show()
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

        })
    }


    @SuppressLint("InlinedApi")

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode)
        {
            CODEWRITE ->
            {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this ,"PERMISSION_GRANTED" ,Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    Toast.makeText(this ,"Accept Permission" ,Toast.LENGTH_SHORT).show()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}