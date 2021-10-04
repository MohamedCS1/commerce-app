package com.example.Settings

import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import com.example.Data.ProductDatabase
import com.example.Pojo.Login
import com.example.Pojo.Product
import com.example.Products.ProductsViewModel
import com.example.phons.LoginViewModel

import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitCancellation
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class Settings : AppCompatActivity() {

    var bu_import_data:Button? = null
    var companydatabase:CompanyDatabase? = null
    var CompanyViewModel: CompanyViewModel? = null


    var productdatabase:ProductDatabase? = null
    var ProductViewModel: ProductsViewModel? = null

    var loginviewmodel: LoginViewModel? = null


    var fileUri: String? = null

    var CODEWRITE = 120
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        bu_import_data = findViewById(R.id.bu_import_data)

        if (ContextCompat.checkSelfPermission(this ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this ,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ,CODEWRITE)
        }

        bu_import_data!!.setOnClickListener {


            if (ContextCompat.checkSelfPermission(this ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this ,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ,CODEWRITE)
            }
            else
            {
                loginviewmodel = LoginViewModel()

                loginviewmodel!!.login(prf.getemail() ,prf.getpassword() ,prf.getcode())

                loginviewmodel!!.MutableLiveDataLogin.observe(this ,object :Observer<Login>{
                    override fun onChanged(t: Login?) {
                        if (t!!.status == "1")
                        {
                            prf.prefcreate(this@Settings)
                            prf.insertuiq(t.uiq)

                            val nuiq = prf.getuiq()
                            companydatabase = CompanyDatabase.getInstance(this@Settings)

                            productdatabase = ProductDatabase.getInstance(this@Settings)

                            CompanyViewModel = CompanyViewModel(this@Settings)

                            ProductViewModel = ProductsViewModel()

                            CompanyViewModel!!.getcompany(nuiq)

                            val array_id = arrayListOf<String>()


                CompanyViewModel!!.MutableLiveDataCompaby.observe(this@Settings,
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


                            }).start()

                        }

                    })
                            for (i in array_id)
                            {

                                ProductViewModel!!.getProducts(uiq ,i ,null ,null)
                                Thread.sleep(300)
                                Log.d("compid" ,i)

                            }
                ProductViewModel!!.MutableLiveDataProducts.observe(this@Settings ,
                    object :Observer<ArrayList<Product>>
                    {
                        override fun onChanged(t: ArrayList<Product>?) {
                            Thread(Runnable {
                                try {

                                    val it: MutableIterator<Product> = t!!.iterator()
                                    while (it.hasNext()) {
                                        val value = it.next()
                                        if (value.image != "")
                                        {
                                            runOnUiThread {
                                                SaveImage("https://app.mytasks.click${value.image}" ,value.id)
                                            }
                                        }
                                        productdatabase?.ProductDao()?.insertProduct(value)
                                    }
                                }catch (ex:Exception)
                                {
                                    print(ex)
                                }

                            }).start()
                        }

                    })


                        }
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