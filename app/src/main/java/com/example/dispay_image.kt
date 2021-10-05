package com.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import com.example.phons.R
import com.squareup.picasso.Picasso
import java.io.File

class dispay_image : AppCompatActivity() {

    var image_product:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispay_image)

        val bundle = intent.extras

        val lastversion = bundle?.getString("lastversion")

        image_product = findViewById(R.id.product_image)

        Picasso.with(this).load( File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Samco/${lastversion}.jpg")).placeholder(R.drawable.placeholderimg).into(image_product)


    }

}