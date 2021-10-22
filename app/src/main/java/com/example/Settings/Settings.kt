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
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.Data.ProductDatabase
import com.example.Pojo.Login
import com.example.Pojo.Product
import com.example.Products.ProductsViewModel
import com.example.phons.LoginViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import com.example.Company.Company_activity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Settings : AppCompatActivity() {

    var bu_import_data:Button? = null
    var companydatabase:CompanyDatabase? = null
    var CompanyViewModel: CompanyViewModel? = null


    var productdatabase:ProductDatabase? = null
    var ProductViewModel: ProductsViewModel? = null

    var loginviewmodel: LoginViewModel? = null
    var bu_procces_database:Button? = null

    var fileUri: String? = null
    var bu_back:FloatingActionButton? = null

    var CODEWRITE = 120
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        bu_back?.setOnClickListener {
            onBackPressed()
        }


        val dialogdatabase = AlertDialog.Builder(this)
        val view:View = View.inflate(this ,R.layout.alert_initialization_databe ,null)
        val d = dialogdatabase.setView(view).create()
        d.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        val dialog_import = AlertDialog.Builder(this)
        val view_import:View = View.inflate(this ,R.layout.alert_data_daownload ,null)
        val d_import = dialog_import.setView(view_import).create()
        d_import.setCancelable(false)
        dialog_import.setCancelable(false)
        d_import.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        val myExecutor1 = Executors.newSingleThreadExecutor()
        val myHandler1 = Handler(Looper.getMainLooper())

        myExecutor1.execute {
            myHandler1.post {
                d.show()
            }
            companydatabase = CompanyDatabase.getInstance(this@Settings)

            productdatabase = ProductDatabase.getInstance(this@Settings)

            myExecutor1.shutdown()
            if (myExecutor1.isShutdown)
            {
                myHandler1.post {
                    d.setCancelable(true)
                    d.dismiss()
                }
            }
        }


        val prf = PrefManage()
        prf.prefcreate(this)

        bu_import_data = findViewById(R.id.bu_import_data)

        bu_procces_database = findViewById(R.id.bu_process_database)

        bu_procces_database!!.setOnClickListener {

            d.show()
            d.setCancelable(false)
            val myExecutor = Executors.newSingleThreadExecutor()
            val myHandler = Handler(Looper.getMainLooper())

            myExecutor.execute {
                val arrayid_product = productdatabase?.ProductDao()?.getallid()
                arrayid_product?.forEach {
                    productdatabase?.ProductDao()?.deleteByUserId(it.toLong())
                }

                val arrayid_company = companydatabase?.CompanyDao()?.getallid()
                arrayid_company?.forEach {
                    companydatabase?.CompanyDao()?.deleteByUserId(it.toLong())

                }
                myExecutor.shutdown()
                if (myExecutor.isShutdown)
                {
                    myHandler.post {
                        d.setCancelable(true)
                        d.dismiss()
                        Toast.makeText(this ,"تم تهيئة قاعدة البيانات بنجاح" ,Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }



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

                loginviewmodel!!.MutableLiveDataLogin.observe(this ,object :Observer<Login>{ override fun onChanged(t: Login?) {
                    if (t!!.status == "1")
                        {
                            prf.prefcreate(this@Settings)
                            prf.insertuiq(t.uiq)

                            val nuiq = prf.getuiq()
                            prf.insert_one_page(true)

                            CompanyViewModel = CompanyViewModel(this@Settings)

                            ProductViewModel = ProductsViewModel()

                            CompanyViewModel!!.getcompany(nuiq)


                            d_import.setCancelable(false)
                            d_import.show()

                            val timer = Timer()
                            timer.schedule(object : TimerTask() {
                                override fun run() {
                                    d_import.dismiss()
                                    timer.cancel()
                                    startActivity(Intent(this@Settings ,Company_activity::class.java))
                                }
                            }, 15000)

                            CompanyViewModel!!.MutableLiveDataCompaby.observe(this@Settings, object : Observer<ArrayList<Companies>> { override fun onChanged(t: ArrayList<Companies>?) { Thread(Runnable() {

                                try {
                                    for (i in t!!) {
                                        ProductViewModel!!.getProducts(
                                            nuiq,
                                            i.compid.toString(),
                                            null,
                                            null
                                        )

                                        if (i.image_link != "")
                                        {
                                            runOnUiThread {
                                                SaveImage("https://app.mytasks.click${i.image_link}" ,i.lastversion.toString())

                                            }
                                        }
                                        companydatabase?.CompanyDao()?.insertCompany(i)
                                    }
                                }catch (ex:Exception){

                                    runOnUiThread { Toast.makeText(this@Settings ,ex.toString() ,Toast.LENGTH_LONG).show() }
                                }


                  }).start()
                    }

               })

                            var t1:Thread? = null
                ProductViewModel!!.MutableLiveDataProducts.observe(this@Settings ,object :Observer<ArrayList<Product>>
                    {
                        override fun onChanged(t: ArrayList<Product>?) {
                          t1 =  Thread(Runnable {

                                try {
                                    for (i in t!!)
                                    {
                                        if (i.image != "")
                                        {
                                            runOnUiThread {
                                                SaveImage("https://app.mytasks.click${i.image}" ,i.lastversion)
                                            }
                                        }
                                        productdatabase?.ProductDao()?.insertProduct(i)

                                    }


                                }catch (ex:Exception)
                                {
                                    runOnUiThread { Toast.makeText(this@Settings ,ex.toString() ,Toast.LENGTH_LONG).show() }
                                }

                            })
                            t1!!.start()

                        }

                    })
                        }
                    }

                })


            }

    }

  }

    fun SaveImage(url: String? ,lastversion: String) {
        Picasso.with(applicationContext).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                try {
                    val mydir =
                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Samco")
                    Log.d("path",mydir.toString())
                    if (!mydir.exists()) {
                        mydir.mkdirs()
                    }
                    fileUri = mydir.getAbsolutePath() + File.separator + lastversion + ".jpg"

                    Log.d("path",fileUri.toString())
                    val outputStream = FileOutputStream(fileUri)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
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